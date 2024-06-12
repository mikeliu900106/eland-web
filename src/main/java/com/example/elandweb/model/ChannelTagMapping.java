package com.example.elandweb.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "channel_tag_mapping")
public class ChannelTagMapping {
    @ManyToOne
    @JoinColumn(name = "s_area_id")
    private ChannelInfo channelInfo;

    @Id
    @OneToOne
    @JoinColumn(name = "tag_id")
    private TagInfo tagInfo;
}
