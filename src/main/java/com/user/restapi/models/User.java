package com.user.restapi.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.user.restapi.utilities.RoleType;
import com.user.restapi.utilities.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
@JsonDeserialize(as = User.class)
public class User extends BaseModel{

    private String fullName;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String country;

    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles = new HashSet<>();

    @ManyToMany
    private Set<Permissions> permissions;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp lastLoginAt;

}
