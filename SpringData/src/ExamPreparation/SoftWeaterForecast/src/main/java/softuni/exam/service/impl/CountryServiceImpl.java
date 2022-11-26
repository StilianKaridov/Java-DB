package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.enums.ExceptionMessages.INVALID_FORMAT;
import static softuni.exam.enums.ExceptionMessages.SUCCESSFUL_FORMAT;
import static softuni.exam.enums.FilePath.COUNTRIES_IMPORT;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper mapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_IMPORT));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readCountriesFromFile(), CountryImportDTO[].class))
                .forEach(country -> {

                    if (this.countryRepository.findFirstByCountryName(country.getCountryName()).isPresent()
                            || !country.validate()) {
                        sb.append(String.format(INVALID_FORMAT, "country"));
                        return;
                    }

                    Country toInsert = mapper.map(country, Country.class);
                    this.countryRepository.saveAndFlush(toInsert);

                    sb.append(String.format(SUCCESSFUL_FORMAT, country.getCountryName(), country.getCurrency()));
                });

        return sb.toString();
    }
}
