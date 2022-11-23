package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.sale.SaleSeedDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Car;
import com.example.xmlprocessing.carDealer.domain.entities.Customer;
import com.example.xmlprocessing.carDealer.domain.entities.Sale;
import com.example.xmlprocessing.carDealer.repositories.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    private double getRandomDiscountPercentage(double[] percentages) {
        Random random = new Random();

        int index = random.nextInt(7);

        return percentages[index];
    }
}
