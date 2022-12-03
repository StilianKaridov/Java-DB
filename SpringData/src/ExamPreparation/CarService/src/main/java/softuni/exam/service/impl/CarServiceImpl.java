package ExamPreparation.CarService.src.main.java.softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarImportWrapperDTO;
import softuni.exam.models.entity.Car;
import ExamPreparation.CarService.src.main.java.softuni.exam.repository.CarRepository;
import ExamPreparation.CarService.src.main.java.softuni.exam.service.CarService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import static softuni.exam.util.FilePaths.CARS_PATH;
import static softuni.exam.util.Messages.INVALID_IMPORT_MESSAGE;
import static softuni.exam.util.Messages.SUCCESSFULLY_IMPORTED_CAR_MESSAGE;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper mapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(CARS_PATH);
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(CarImportWrapperDTO.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        FileReader reader = new FileReader(CARS_PATH.toFile());

        CarImportWrapperDTO wrapperDTO = (CarImportWrapperDTO) unmarshaller.unmarshal(reader);

        wrapperDTO.getCars()
                .forEach(c -> {
                    boolean isCarPresent = this.carRepository.findFirstByPlateNumber(c.getPlateNumber()).isPresent();

                    if (isCarPresent || !c.validate()) {
                        sb.append(String.format(INVALID_IMPORT_MESSAGE, "car"));
                        return;
                    }

                    Car toInsert = this.mapper.map(c, Car.class);

                    this.carRepository.saveAndFlush(toInsert);

                    sb.append(String.format(SUCCESSFULLY_IMPORTED_CAR_MESSAGE,
                            toInsert.getCarMake(),
                            toInsert.getCarModel()));
                });

        reader.close();

        return sb.toString();
    }
}
