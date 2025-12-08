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
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + getRole().toUpperCase());
    }
    
    @Override
    public String getUsername() {
        return this.email;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    protected String fullName;
    protected String email;
    protected String password;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    protected List<Address> address;
    protected boolean isActive;
    protected Date createdAt=new Date();
    protected Date updatedAt=new Date();

    public String getRole(){
        return "user";
    }
}