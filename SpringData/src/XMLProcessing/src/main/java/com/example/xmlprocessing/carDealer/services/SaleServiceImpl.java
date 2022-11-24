package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.sale.SaleDTO;
import com.example.xmlprocessing.carDealer.domain.dtos.sale.SaleSeedDTO;
import com.example.xmlprocessing.carDealer.domain.dtos.sale.SaleWrapperDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Car;
import com.example.xmlprocessing.carDealer.domain.entities.Customer;
import com.example.xmlprocessing.carDealer.domain.entities.Part;
import com.example.xmlprocessing.carDealer.domain.entities.Sale;
import com.example.xmlprocessing.carDealer.repositories.SaleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.xmlprocessing.carDealer.constants.FilePaths.SALES_DISCOUNTS_PATH;

@Service
public class SaleServiceImpl implements SaleService {

    private final double[] discountPercentages = new double[]{
            0, 5, 10, 15, 20, 30, 40, 50
    };

    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final ModelMapper mapper;


    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, ModelMapper mapper) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @Override
    public void seedSales() {
        if (this.saleRepository.count() == 0) {

            List<SaleSeedDTO> salesDTOs = new ArrayList<>();

            for (int i = 0; i <= 20; i++) {
                Car car = this.carService.getRandomCar();
                Customer customer = this.customerService.getRandomCustomer();
                double discountPercentage = getRandomDiscountPercentage(discountPercentages);

                SaleSeedDTO saleDTO = new SaleSeedDTO(car, customer, discountPercentage);

                salesDTOs.add(saleDTO);
            }

            List<Sale> salesToInsert = salesDTOs
                    .stream()
                    .map(s -> mapper.map(s, Sale.class))
                    .collect(Collectors.toList());

            this.saleRepository.saveAllAndFlush(salesToInsert);
        }
    }

    @Override
    public List<SaleDTO> getAllSalesWithDiscount() throws IOException, JAXBException {
        TypeMap<Sale, SaleDTO> typeMap = mapper.createTypeMap(Sale.class, SaleDTO.class);

        typeMap.addMappings(m -> m.map(src -> src.getCustomer().getName(),
                SaleDTO::setCustomerName));

        List<SaleDTO> sales = this.saleRepository
                .findAll()
                .stream()
                .map(typeMap::map)
                .collect(Collectors.toList());

        for (SaleDTO s : sales) {
            List<Part> parts = s.getCar().getParts();
            double price = parts
                    .stream()
                    .mapToDouble(p -> p.getPrice().doubleValue())
                    .sum();

            s.setPrice(new BigDecimal(price));

            double priceWithDiscount = price - (s.getDiscountPercentage() / 100) * price;
            s.setPriceWithDiscount(new BigDecimal(priceWithDiscount));
        }

        FileWriter fileWriter = new FileWriter(SALES_DISCOUNTS_PATH);

        JAXBContext jaxbContext = JAXBContext.newInstance(SaleWrapperDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        SaleWrapperDTO wrapperDTO = new SaleWrapperDTO(sales);

        marshaller.marshal(wrapperDTO, fileWriter);

        fileWriter.close();

        return sales;
    }

    private double getRandomDiscountPercentage(double[] percentages) {
        Random random = new Random();

        int index = random.nextInt(7);

        return percentages[index];
    }
}
