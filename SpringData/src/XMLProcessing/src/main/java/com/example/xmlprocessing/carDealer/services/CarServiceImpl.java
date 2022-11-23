package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.car.CarSeedWrapperDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Car;
import com.example.xmlprocessing.carDealer.domain.entities.Part;
import com.example.xmlprocessing.carDealer.repositories.CarRepository;
import com.example.xmlprocessing.carDealer.repositories.PartRepository;
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

import static com.example.xmlprocessing.carDealer.constants.FilePaths.CARS_XML_PATH;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper mapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedCars() throws IOException, JAXBException {
        if (this.carRepository.count() == 0) {
            FileReader fileReader = new FileReader(CARS_XML_PATH);

            JAXBContext jaxbContext = JAXBContext.newInstance(CarSeedWrapperDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            CarSeedWrapperDTO wrapperDTO = (CarSeedWrapperDTO) unmarshaller.unmarshal(fileReader);

            List<Car> cars = wrapperDTO.getCars()
                    .stream()
                    .map(c -> mapper.map(c, Car.class))
                    .collect(Collectors.toList());

            Random random = new Random();

            for (Car c : cars) {
                for (int i = 0; i < 15; i++) {
                    long partId = random.nextInt(130);

                    Optional<Part> part = this.partRepository.findById(partId);

                    part.ifPresent(value -> c.getParts().add(value));
                }
            }

            this.carRepository.saveAllAndFlush(cars);

            fileReader.close();
        }
    }

    @Override
    public Car getRandomCar() {
        Random random = new Random();

        long carId = random.nextInt(357);

        return this.carRepository.findById(carId + 1).get();
    }


}
