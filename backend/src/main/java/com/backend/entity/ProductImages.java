package com.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String path;
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
}
