package com.example.elandweb.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "channel_tag_mapping")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelTagEntity extends BasicEntity{

    @JoinColumn(name = "s_area_id")
    private String sAreaId;

    @Id
    @Column(name = "tag_id")
    private String tagId;
}
