package com.example.elandweb.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "channel_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelInfoEntity extends BasicEntity{
    @Id
    @Column(name = "source_area_id")
    private String sourceAreaId;

    @Column(name = "is_used")
    private boolean isUsed;


    @Column(name="p_type_2")
    private String PType2;

    @Column(name="source_id")
    private String source_id;
}