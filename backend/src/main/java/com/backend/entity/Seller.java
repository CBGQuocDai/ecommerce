package com.backend.entity;

import jakarta.persistence.Entity;


@Entity
public class Seller extends User{
    @Override
    public String getRole() {
        return "seller";
    }
}
