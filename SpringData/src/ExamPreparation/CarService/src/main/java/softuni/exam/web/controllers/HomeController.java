package ExamPreparation.CarService.src.main.java.softuni.exam.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ExamPreparation.CarService.src.main.java.softuni.exam.service.MechanicService;
import ExamPreparation.CarService.src.main.java.softuni.exam.service.PartService;
import ExamPreparation.CarService.src.main.java.softuni.exam.service.TaskService;
import ExamPreparation.CarService.src.main.java.softuni.exam.service.CarService;

@Controller
public class HomeController extends BaseController {

    private final PartService partService;
    private final TaskService taskService;
    private final CarService carService;
    private final MechanicService mechanicService;

    @Autowired
    public HomeController(PartService partService, TaskService taskService, CarService carService, MechanicService mechanicService) {
        this.partService = partService;
        this.taskService = taskService;
        this.carService = carService;
        this.mechanicService = mechanicService;
    }


    @GetMapping("/")
    public ModelAndView index() {
        boolean areImported = this.partService.areImported() &&
                this.carService.areImported() &&
                this.partService.areImported() &&
                this.taskService.areImported();

        return super.view("index", "areImported", areImported);
    }
}
