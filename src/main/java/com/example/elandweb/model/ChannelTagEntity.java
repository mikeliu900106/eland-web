package com.example.elandweb.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "channel_tag_mapping")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelTagEntity {
    @ManyToOne
    @JoinColumn(name = "s_area_id")
    private ChannelInfoEntity channelInfo;

    @Id
    @OneToOne
    @JoinColumn(name = "tag_id")
    private TagInfoEntity tagInfo;
}
