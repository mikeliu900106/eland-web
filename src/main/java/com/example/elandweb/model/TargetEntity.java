package com.example.elandweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "target")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TargetEntity implements Cloneable {
    @Id
    @Column(name = "tag_name")
    private String tagName;
    @Column(name = "news_count")
    private int newsCount;
    @Column(name = "blog_count")
    private int blogCount;
    @Column(name = "forum_count")
    private int forumCount;
    @Column(name = "social_count")
    private int socialCount;
    @Column(name = "comment_count")
    private int commentCount;
    @Column(name = "qa_count")
    private int qaCount;
    @Column(name = "video_count")
    private int videoCount;
    @Column(name = "type")
    private short type;

    @Override
    public TargetEntity clone() {
        try {
            TargetEntity clone = (TargetEntity) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
