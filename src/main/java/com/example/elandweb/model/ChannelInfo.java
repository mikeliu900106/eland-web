package com.example.elandweb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "channel_info")
public class ChannelInfo {
    @Id
    @Column(name = "source_area_id")
    private String sourceAreaId;

    @Column(name = "is_used")
    private boolean isUsed;

    @OneToOne
    @JoinColumn(name="p_type_2")
    private PType2List pType2List;

    @OneToMany(mappedBy = "channelInfo")
    private List<ChannelTagMapping> channelTagMappingList;
}