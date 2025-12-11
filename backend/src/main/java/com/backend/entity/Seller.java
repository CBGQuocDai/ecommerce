package com.backend.entity;

import jakarta.persistence.Entity;


@Entity
public class Seller extends User{

    public Seller(User user) {
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.password= user.getPassword();
        this.createdAt= user.getCreatedAt();
        this.updatedAt= user.getUpdatedAt();
        this.isActive= user.isActive();
        this.id=user.getId();
        this.phoneNumber=user.getPhoneNumber();
        this.avatarPath=user.getAvatarPath();
    }

    public Seller() {

    }

    @Override
    public String getRole() {
        return "seller";
    }
}
