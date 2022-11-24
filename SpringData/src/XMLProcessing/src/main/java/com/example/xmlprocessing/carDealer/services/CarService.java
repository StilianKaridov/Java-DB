package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.car.CarDTO;
import com.example.xmlprocessing.carDealer.domain.dtos.car.CarWithPartsDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Car;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CarService {

    void seedCars() throws IOException, JAXBException;

    Car getRandomCar();

    List<CarDTO> getAllCarsFromToyota(String make) throws IOException, JAXBException;

    List<CarWithPartsDTO> getAllCarsNecessaryInfo() throws IOException, JAXBException;
}
