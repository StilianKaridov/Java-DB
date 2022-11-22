package com.example.xmlprocessing.domain.dtos.user;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class AllUsersWithCountWrapperDTO {

    @XmlAttribute
    private int count;

    @XmlElement(name = "user")
    private List<UserWithAttributesDTO> users;

    public AllUsersWithCountWrapperDTO() {
        this.users = new ArrayList<>();
    }

    public AllUsersWithCountWrapperDTO(List<UserWithAttributesDTO> users) {
        this.users = users;
        this.count = this.users.size();
    }
}
