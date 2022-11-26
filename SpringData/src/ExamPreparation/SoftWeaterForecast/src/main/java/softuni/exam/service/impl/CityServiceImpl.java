package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.enums.ExceptionMessages.INVALID_FORMAT;
import static softuni.exam.enums.ExceptionMessages.SUCCESSFUL_FORMAT;
import static softuni.exam.enums.FilePath.CITIES_IMPORT;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final CountryRepository countryRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, Gson gson, ModelMapper mapper, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_IMPORT));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readCitiesFileContent(), CityImportDTO[].class))
                .forEach(city -> {

                    if (this.cityRepository.findFirstByCityName(city.getCityName()).isPresent()
                            || !city.validate()) {
                        sb.append(String.format(INVALID_FORMAT, "city"));
                        return;
                    }

                    Optional<Country> country = this.countryRepository.findById(city.getCountry());

                    City toInsert = this.mapper.map(city, City.class);

                    country.ifPresent(toInsert::setCountry);

                    this.cityRepository.saveAndFlush(toInsert);

                    sb.append(String.format(SUCCESSFUL_FORMAT, city.getCityName(), city.getPopulation()));
                });

        return sb.toString();
    }
}
