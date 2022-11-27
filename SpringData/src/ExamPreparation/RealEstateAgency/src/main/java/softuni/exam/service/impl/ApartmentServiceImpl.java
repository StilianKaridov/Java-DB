package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDTO;
import softuni.exam.models.dto.ApartmentImportWrapperDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.constants.FilePaths.APARTMENTS_IMPORT;
import static softuni.exam.constants.PrintFormats.INVALID_FORMAT;
import static softuni.exam.constants.PrintFormats.SUCCESSFUL_APARTMENT_FORMAT;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ModelMapper mapper;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ModelMapper mapper) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENTS_IMPORT));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        FileReader fileReader = new FileReader(APARTMENTS_IMPORT);

        JAXBContext jaxbContext = JAXBContext.newInstance(ApartmentImportWrapperDTO.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        ApartmentImportWrapperDTO wrapperDTO = (ApartmentImportWrapperDTO) unmarshaller.unmarshal(fileReader);

        for (ApartmentImportDTO a : wrapperDTO.getApartments()) {
            Optional<Apartment> existingApartment = this.apartmentRepository.findFirstByTown_TownNameAndArea(a.getTown(), a.getArea());

            if (existingApartment.isPresent() || !a.validate()) {
                sb.append(String.format(INVALID_FORMAT, "apartment"));
                continue;
            }

            Optional<Town> town = this.townRepository.findFirstByTownName(a.getTown());

            if (town.isPresent()) {
                Apartment toInsert = this.mapper.map(a, Apartment.class);

                toInsert.setTown(town.get());

                this.apartmentRepository.saveAndFlush(toInsert);

                sb.append(String.format(SUCCESSFUL_APARTMENT_FORMAT, a.getApartmentType(), a.getArea()));
            }
        }

        fileReader.close();

        return sb.toString();
    }
}
