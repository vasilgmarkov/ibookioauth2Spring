package com.mycompany.myapp.web.rest.dto;

import java.time.ZonedDateTime;

import com.mycompany.myapp.domain.User;

/**
 * A DTO extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserDTO extends UserDTO {

    private Long id;

    private ZonedDateTime createdDate;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    private Double saldo;

    public ManagedUserDTO() {
    }

    public ManagedUserDTO(User user) {
        super(user);
        this.id = user.getId();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.saldo = user.getSaldo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }


    @Override
    public String toString() {
        return "ManagedUserDTO{" +
            "id=" + id +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", saldo=" + saldo +
            '}';
    }
}
