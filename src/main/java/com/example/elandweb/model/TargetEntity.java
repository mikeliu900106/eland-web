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
public class TargetEntity {
    @Id
    @Column(name = "tag_name")
    private String tagName;
    @Column(name = "news_count")
    private int news;
    @Column(name = "blog_count")
    private int blog;
    @Column(name = "forum_count")
    private int forum;
    @Column(name = "social_count")
    private int social;
    @Column(name = "comment_count")
    private int comment;
    @Column(name = "qa_count")
    private int qa;
    @Column(name = "video_count")
    private int video;
}
