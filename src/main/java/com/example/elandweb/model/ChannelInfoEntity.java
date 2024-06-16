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
public class ChannelInfoEntity {
    @Id
    @Column(name = "source_area_id")
    private String sourceAreaId;

    @Column(name = "is_used")
    private boolean isUsed;

    @OneToOne
    @JoinColumn(name="p_type_2")
    private PType2Entity pType2List;

    @OneToMany(mappedBy = "channelInfo")
    private List<ChannelTagEntity> channelTagMappingList;
}