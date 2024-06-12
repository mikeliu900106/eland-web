package com.example.elandweb.dao;

import com.example.elandweb.model.ChannelInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelInfoRepository  extends JpaRepository<ChannelInfo, String> {
//    list<>
}
