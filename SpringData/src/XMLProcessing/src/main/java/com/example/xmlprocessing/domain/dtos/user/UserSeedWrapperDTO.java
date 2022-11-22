package com.example.xmlprocessing.domain.dtos.user;

import com.example.xmlprocessing.domain.dtos.user.UserSeedDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSeedWrapperDTO {

    @XmlElement(name = "user")
    private List<UserSeedDTO> users;
}
