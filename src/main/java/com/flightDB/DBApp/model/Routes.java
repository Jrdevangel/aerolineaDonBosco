package com.flightDB.DBApp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Routes {
    public Routes(Long id, String country, String city) {
        this.id = id;
        this.country = country;
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Country")
    private String country;

    @Column(name = "City")
    private String city;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "destination-reference")
    private Set<Flight> destinationFlights;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "origin-reference")
    private Set<Flight> originFlights;
}
