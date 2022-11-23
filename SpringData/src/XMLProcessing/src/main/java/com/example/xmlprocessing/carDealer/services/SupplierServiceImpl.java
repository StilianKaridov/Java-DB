package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.supplier.SupplierSeedWrapperDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Supplier;
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
import java.util.stream.Collectors;

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
}
