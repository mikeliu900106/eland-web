package com.example.elandweb.dao;

import com.example.elandweb.model.TargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<TargetEntity,String> {
}
