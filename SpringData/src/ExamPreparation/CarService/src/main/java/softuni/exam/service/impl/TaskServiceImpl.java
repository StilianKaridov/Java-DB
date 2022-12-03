package ExamPreparation.CarService.src.main.java.softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskImportWrapperDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.models.enums.CarType;
import ExamPreparation.CarService.src.main.java.softuni.exam.repository.CarRepository;
import ExamPreparation.CarService.src.main.java.softuni.exam.repository.MechanicRepository;
import ExamPreparation.CarService.src.main.java.softuni.exam.repository.PartRepository;
import ExamPreparation.CarService.src.main.java.softuni.exam.repository.TaskRepository;
import ExamPreparation.CarService.src.main.java.softuni.exam.service.TaskService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static softuni.exam.util.FilePaths.TASKS_PATH;
import static softuni.exam.util.Messages.INVALID_IMPORT_MESSAGE;
import static softuni.exam.util.Messages.SUCCESSFULLY_IMPORTED_TASK_MESSAGE;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MechanicRepository mechanicRepository;
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper mapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, MechanicRepository mechanicRepository, CarRepository carRepository, PartRepository partRepository, ModelMapper mapper) {
        this.taskRepository = taskRepository;
        this.mechanicRepository = mechanicRepository;
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(TASKS_PATH);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(TaskImportWrapperDTO.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        FileReader reader = new FileReader(TASKS_PATH.toFile());

        TaskImportWrapperDTO wrapperDTO = (TaskImportWrapperDTO) unmarshaller.unmarshal(reader);

        wrapperDTO.getTasks()
                .forEach(t -> {
                    Optional<Mechanic> mechanic = this.mechanicRepository
                            .findFirstByFirstName(t.getMechanic().getFirstName());

                    Optional<Car> car = this.carRepository.findById(t.getCar().getId());

                    Optional<Part> part = this.partRepository.findById(t.getPart().getId());

                    if (car.isEmpty() || mechanic.isEmpty() || !t.validate()) {
                        sb.append(String.format(INVALID_IMPORT_MESSAGE, "task"));
                        return;
                    }

                    Task toInsert = this.mapper.map(t, Task.class);

                    toInsert.setMechanic(mechanic.get());
                    toInsert.setCar(car.get());
                    toInsert.setPart(part.get());

                    this.taskRepository.saveAndFlush(toInsert);

                    sb.append(String.format(SUCCESSFULLY_IMPORTED_TASK_MESSAGE,
                            toInsert.getPrice().doubleValue()));
                });

        reader.close();

        return sb.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        StringBuilder sb = new StringBuilder();

        this.taskRepository
                .getHighestPricedTasks(CarType.coupe)
                .forEach(t -> sb.append(t.toString()));

        return sb.toString();
    }
}
