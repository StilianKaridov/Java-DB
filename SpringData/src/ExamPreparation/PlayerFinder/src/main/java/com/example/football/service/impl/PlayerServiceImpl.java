package com.example.football.service.impl;

import com.example.football.models.dto.BestPlayersExportDTO;
import com.example.football.models.dto.PlayerImportDTO;
import com.example.football.models.dto.PlayerImportWrapperDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.example.football.util.FilePaths.PLAYERS_IMPORT;
import static com.example.football.util.Messages.INVALID_FORMAT;
import static com.example.football.util.Messages.SUCCESSFULLY_IMPORTED_PLAYER;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final ModelMapper mapper;

    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository, ModelMapper mapper) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_IMPORT));
    }

    @Override
    public String importPlayers() throws JAXBException, IOException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(PlayerImportWrapperDTO.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        FileReader fileReader = new FileReader(PLAYERS_IMPORT);

        PlayerImportWrapperDTO wrapperDTO = (PlayerImportWrapperDTO) unmarshaller.unmarshal(fileReader);

        for (PlayerImportDTO player : wrapperDTO.getPlayers()) {
            boolean isPlayerPresent = this.playerRepository.findFirstByEmail(player.getEmail()).isPresent();

            if (!player.validate() || isPlayerPresent) {
                sb.append(String.format(INVALID_FORMAT, "Player"));
                continue;
            }

            Player toInsert = this.mapper.map(player, Player.class);

            Optional<Team> team = this.teamRepository.findFirstByName(player.getTeam().getName());
            Optional<Town> town = this.townRepository.findFirstByName(player.getTown().getName());
            Optional<Stat> stat = this.statRepository.findById(player.getStat().getId());

            toInsert.setTeam(team.get());
            toInsert.setTown(town.get());
            toInsert.setStat(stat.get());

            this.playerRepository.saveAndFlush(toInsert);

            sb.append(String.format(SUCCESSFULLY_IMPORTED_PLAYER,
                    toInsert.getFirstName(),
                    toInsert.getLastName(),
                    toInsert.getPosition()));
        }

        fileReader.close();

        return sb.toString();
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();

        List<BestPlayersExportDTO> theBestPlayers = this.playerRepository.getTheBestPlayers();

        for (BestPlayersExportDTO p : theBestPlayers) {
            sb.append(p.toString());
        }

        return sb.toString();
    }
}
