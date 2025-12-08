package com.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Buyer buyer;
    private String status;
    private double totalPrice;
    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderItems> items;
    @OneToOne
    private Payment payment;
}
