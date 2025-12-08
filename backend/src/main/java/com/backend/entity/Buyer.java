package com.backend.entity;

import jakarta.persistence.Entity;
@Entity
public class Buyer extends User {
    @Override
    public String getRole() {
        return "buyer";
    }
}
