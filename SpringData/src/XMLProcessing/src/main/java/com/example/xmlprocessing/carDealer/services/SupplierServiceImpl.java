package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.supplier.SupplierDTO;
import com.example.xmlprocessing.carDealer.domain.dtos.supplier.SupplierSeedWrapperDTO;
import com.example.xmlprocessing.carDealer.domain.dtos.supplier.SupplierWrapperDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Supplier;
import com.example.xmlprocessing.carDealer.repositories.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.xmlprocessing.carDealer.constants.FilePaths.LOCAL_SUPPLIERS_PATH;
import static com.example.xmlprocessing.carDealer.constants.FilePaths.SUPPLIER_XML_PATH;

@Service
public class SupplierServiceImpl implements SupplierService{

    private final SupplierRepository supplierRepository;
    private final ModelMapper mapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper mapper) {
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedSuppliers() throws JAXBException, IOException {
        if(this.supplierRepository.count() == 0){
            FileReader fileReader = new FileReader(SUPPLIER_XML_PATH);

            JAXBContext jaxbContext = JAXBContext.newInstance(SupplierSeedWrapperDTO.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            SupplierSeedWrapperDTO suppliers = (SupplierSeedWrapperDTO) unmarshaller.unmarshal(fileReader);

            List<Supplier> suppliersToInsert = suppliers.getSuppliers()
                    .stream()
                    .map(s->mapper.map(s, Supplier.class))
                    .collect(Collectors.toList());

            this.supplierRepository.saveAllAndFlush(suppliersToInsert);

            fileReader.close();
        }
    }

    @Override
    public List<SupplierDTO> getLocalSuppliers() throws IOException, JAXBException {
        List<SupplierDTO> suppliers = this.supplierRepository.getLocalSuppliers();

        FileWriter fileWriter = new FileWriter(LOCAL_SUPPLIERS_PATH);

        JAXBContext jaxbContext = JAXBContext.newInstance(SupplierWrapperDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        SupplierWrapperDTO wrapperDTO = new SupplierWrapperDTO(suppliers);

        marshaller.marshal(wrapperDTO, fileWriter);

        fileWriter.close();

        return suppliers;
    }
}
