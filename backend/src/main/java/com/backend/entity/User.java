package com.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblUser")
public class User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this::getRole);
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fullName;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private boolean isActive;

    private Date createdAt=new Date();
    private Date updatedAt=new Date();

    public String getRole(){
        return "user";
    }
}
