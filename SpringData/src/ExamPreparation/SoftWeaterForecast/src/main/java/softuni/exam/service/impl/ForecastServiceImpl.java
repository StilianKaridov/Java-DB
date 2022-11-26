package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.enums.DayOfWeek;
import softuni.exam.models.dto.ForecastImportDTO;
import softuni.exam.models.dto.ForecastImportWrapperDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.enums.ExceptionMessages.INVALID_FORMAT;
import static softuni.exam.enums.ExceptionMessages.SUCCESSFUL_FORMAT;
import static softuni.exam.enums.FilePath.FORECASTS_IMPORT;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final ModelMapper mapper;

    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, ModelMapper mapper) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_IMPORT));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(ForecastImportWrapperDTO.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        File file = new File(FORECASTS_IMPORT);
        FileReader reader = new FileReader(file);

        ForecastImportWrapperDTO wrapperDTO = (ForecastImportWrapperDTO) unmarshaller.unmarshal(reader);

        for (ForecastImportDTO f : wrapperDTO.getForecasts()) {
            Optional<City> city = this.cityRepository.findById(f.getCity());

            Optional<Forecast> forecast = this.forecastRepository
                    .findFirstByCity_CityNameAndDayOfWeek(city.get().getCityName(), f.getDayOfWeek());

            if (forecast.isPresent() || !f.validate() || f.getDayOfWeek() == null) {
                sb.append(String.format(INVALID_FORMAT, "forecast"));
                continue;
            }

            Forecast toInsert = this.mapper.map(f, Forecast.class);
            toInsert.setCity(city.get());

            this.forecastRepository.saveAndFlush(toInsert);

            sb.append(String.format(SUCCESSFUL_FORMAT, f.getDayOfWeek(), f.getMaxTemperature()));
        }

        reader.close();

        return sb.toString();
    }

    @Override
    public String exportForecasts() {
        StringBuilder sb = new StringBuilder();

        this.forecastRepository
                .findForecastsByDayOfWeekAndCity_PopulationOrderByMaxTemperatureDescIdAsc(DayOfWeek.SUNDAY, 150000)
                .forEach(f -> sb.append(f.toString()));

        return sb.toString();
    }
}
