package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.enums.DayOfWeek;
import softuni.exam.models.dto.ForecastExportDTO;
import softuni.exam.models.entity.Forecast;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    Optional<Forecast> findFirstByCity_CityNameAndDayOfWeek(String cityName, DayOfWeek dayOfWeek);

    @Query(value = "select new softuni.exam.models.dto.ForecastExportDTO" +
            "(f.city.cityName, f.minTemperature, f.maxTemperature, f.sunrise, f.sunset) " +
            "from Forecast f " +
            "where f.dayOfWeek = :dayOfWeek and f.city.population < :population " +
            "order by f.maxTemperature desc, f.id")
    List<ForecastExportDTO> findForecastsByDayOfWeekAndCity_PopulationOrderByMaxTemperatureDescIdAsc(DayOfWeek dayOfWeek, int population);
}
