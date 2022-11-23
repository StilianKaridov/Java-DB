package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.entities.Car;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CarService {

    void seedCars() throws IOException, JAXBException;

    Car getRandomCar();
}
