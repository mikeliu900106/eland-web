package com.example.elandweb.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "tag_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagInfoEntity extends BasicEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private int tagId;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name =  "type")
    private int type;
}
