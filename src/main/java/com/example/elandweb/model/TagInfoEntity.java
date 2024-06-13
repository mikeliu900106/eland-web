package com.example.elandweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tag_info")
public class TagInfoEntity {

    @Id
    @Column(name = "tag_id")
    private int tagId;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name =  "type")
    private String type;
}
