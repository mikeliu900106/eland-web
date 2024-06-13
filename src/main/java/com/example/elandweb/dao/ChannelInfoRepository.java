package com.example.elandweb.dao;

import com.example.elandweb.model.ChannelInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelInfoRepository  extends JpaRepository<ChannelInfoEntity, String> {
//    list<>
}
