package ExamPreparation.CarService.src.main.java.softuni.exam.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface TaskService {

    boolean areImported();

    String readTasksFileContent() throws IOException;

    String importTasks() throws IOException, JAXBException;

    String getCoupeCarTasksOrderByPrice();
}
