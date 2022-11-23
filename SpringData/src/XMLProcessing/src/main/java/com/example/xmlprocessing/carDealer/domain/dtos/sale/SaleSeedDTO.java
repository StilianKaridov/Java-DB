package com.example.xmlprocessing.carDealer.domain.dtos.sale;

import com.example.xmlprocessing.carDealer.domain.entities.Car;
import com.example.xmlprocessing.carDealer.domain.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleSeedDTO {

    private Car car;

    private Customer customer;

    private double discountPercentage;
}
