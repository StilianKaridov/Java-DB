package com.example.jsonprocessing.domain.dtos.user;

import java.util.List;

public class UserWithProductsWrapperDTO {

    private Integer count;

    private List<UserWithProductsDTO> users;

    public UserWithProductsWrapperDTO() {
    }

    public UserWithProductsWrapperDTO(List<UserWithProductsDTO> users) {
        this.users = users;
        this.count = users.size();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserWithProductsDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductsDTO> users) {
        this.users = users;
    }
}
