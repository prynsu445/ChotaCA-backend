package com.chotaca.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Client Name is required")
    private String clientName;

    @NotBlank(message = "Mobile is required")
    private String mobile;

    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "PAN Number is required")
    private String panNumber;

    @NotBlank(message = "GST Number is required")
    private String gstNumber;

    @NotBlank(message = "Address is required")
    private String address;

    private String status;

    @JsonManagedReference
    @OneToMany(mappedBy = "client")
    private List<Invoice> invoices;



}