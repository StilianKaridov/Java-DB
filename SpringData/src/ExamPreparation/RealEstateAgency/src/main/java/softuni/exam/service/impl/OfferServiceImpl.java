package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDTO;
import softuni.exam.models.dto.OfferImportWrapperDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.enums.ApartmentType;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.constants.FilePaths.OFFERS_IMPORT;
import static softuni.exam.constants.PrintFormats.INVALID_FORMAT;
import static softuni.exam.constants.PrintFormats.SUCCESSFUL_OFFER_FORMAT;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper mapper;

    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository, ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_IMPORT));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(OfferImportWrapperDTO.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        FileReader fileReader = new FileReader(OFFERS_IMPORT);

        OfferImportWrapperDTO wrapperDTO = (OfferImportWrapperDTO) unmarshaller.unmarshal(fileReader);

        for (OfferImportDTO o : wrapperDTO.getOffers()) {
            Optional<Agent> agent = this.agentRepository.findFirstByFirstName(o.getAgent().getFirstName());

            if (agent.isEmpty() || !o.validate()) {
                sb.append(String.format(INVALID_FORMAT, "offer"));
                continue;
            }

            Optional<Apartment> apartment = this.apartmentRepository.findById(o.getApartment().getId());

            Offer toInsert = this.mapper.map(o, Offer.class);

            toInsert.setAgent(agent.get());
            toInsert.setApartment(apartment.get());

            this.offerRepository.saveAndFlush(toInsert);

            sb.append(String.format(SUCCESSFUL_OFFER_FORMAT, o.getPrice()));
        }


        fileReader.close();

        return sb.toString();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();

        this.offerRepository
                .getTheBestOffers(ApartmentType.three_rooms)
                .forEach(o -> sb.append(o.toString()));

        return sb.toString();
    }
}
