package com.template.spring.demo.adapters.gateways.databases.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
public class UserEntityDatabaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    public int id;

    @Column(name = "username", unique = true)
    public String username;

    @Column(name = "email", unique = true)
    public String email;

    @Column(name = "password")
    public String password;

    public UserEntityDatabaseModel(){}

}

