package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.part.PartSeedWrapperDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Part;
import com.example.xmlprocessing.carDealer.domain.entities.Supplier;
import com.example.xmlprocessing.carDealer.repositories.PartRepository;
import com.example.xmlprocessing.carDealer.repositories.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.xmlprocessing.carDealer.constants.FilePaths.PARTS_XML_PATH;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper mapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper mapper) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedParts() throws IOException, JAXBException {
        if (this.partRepository.count() == 0) {
            FileReader fileReader = new FileReader(PARTS_XML_PATH);

            JAXBContext jaxbContext = JAXBContext.newInstance(PartSeedWrapperDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            PartSeedWrapperDTO wrapperDTO = (PartSeedWrapperDTO) unmarshaller.unmarshal(fileReader);

            List<Part> parts = wrapperDTO.getParts()
                    .stream()
                    .map(p -> mapper.map(p, Part.class))
                    .collect(Collectors.toList());

            Random random = new Random();

            for (Part p : parts) {
                long supplierId = random.nextInt(30);

                Optional<Supplier> supplier = this.supplierRepository.findById(supplierId + 1);

                supplier.ifPresent(p::setSupplier);
            }

            this.partRepository.saveAllAndFlush(parts);

            fileReader.close();
        }
    }
}
