package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.FilePaths.AGENTS_IMPORT;
import static softuni.exam.constants.PrintFormats.INVALID_FORMAT;
import static softuni.exam.constants.PrintFormats.SUCCESSFUL_AGENT_FORMAT;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, Gson gson, ModelMapper mapper) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENTS_IMPORT));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();

        FileReader fileReader = new FileReader(AGENTS_IMPORT);

        Arrays.stream(this.gson.fromJson(fileReader, AgentImportDTO[].class))
                .forEach(agent -> {
                    boolean doesExists = this.agentRepository.findFirstByFirstName(agent.getFirstName()).isPresent();

                    if (doesExists || !agent.validate()) {
                        sb.append(String.format(INVALID_FORMAT, "agent"));
                        return;
                    }

                    Optional<Town> town = this.townRepository.findFirstByTownName(agent.getTown());

                    if (town.isPresent()) {
                        Agent toInsert = this.mapper.map(agent, Agent.class);

                        toInsert.setTown(town.get());

                        this.agentRepository.saveAndFlush(toInsert);

                        sb.append(String.format(SUCCESSFUL_AGENT_FORMAT, toInsert.getFirstName(), toInsert.getLastName()));
                    }
                });

        fileReader.close();

        return sb.toString();
    }
}
