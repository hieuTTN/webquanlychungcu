package com.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "apartment")
@Getter
@Setter
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private Float acreage;

    private Integer floor;

    private Double price;

    private Boolean isSold = false;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = {"apartment"})
    private List<Resident> residents;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.REMOVE)
    private List<Vehicle> vehicles;

    @Override
    public String toString() {
        return name +
                ", tầng: " + floor;
    }
}
