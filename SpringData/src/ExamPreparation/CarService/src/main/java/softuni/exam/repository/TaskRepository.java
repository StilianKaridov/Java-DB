package ExamPreparation.CarService.src.main.java.softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.TaskExportDTO;
import softuni.exam.models.entity.Task;
import softuni.exam.models.enums.CarType;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "select new softuni.exam.models.dto.TaskExportDTO" +
            "(t.id, t.price, t.car.carMake, t.car.carModel, t.car.kilometers, t.car.engine, t.mechanic.firstName, t.mechanic.lastName)" +
            "from Task t " +
            "where t.car.carType = :carType " +
            "order by t.price desc")
    List<TaskExportDTO> getHighestPricedTasks(CarType carType);
}
