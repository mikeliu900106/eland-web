package com.example.elandweb.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "p_type_2")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PType2Entity extends  BasicEntity{

    @Id
    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

}