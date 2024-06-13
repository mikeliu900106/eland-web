package com.example.elandweb.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "p_type_2_list")
public class PType2ListEntity {

    @Id
    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

}