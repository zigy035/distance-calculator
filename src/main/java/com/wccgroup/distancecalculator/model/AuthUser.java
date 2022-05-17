package com.wccgroup.distancecalculator.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "auth_user")
public class AuthUser {

    @Id
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    private String username;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    @NotNull
    @Size(min = 5, max = 100)
    private String name;

    @NotNull
    @CreatedDate
    private LocalDateTime createDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthUserRole role;
}
