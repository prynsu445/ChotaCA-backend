package com.chotaca.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invoice_item")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer quantity;

    private Double price;

    private Double amount;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}