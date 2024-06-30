package com.example.backend.repository.LuckyDraw;

import com.example.backend.entity.LuckyDraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LuckyDrawRepository extends JpaRepository<LuckyDraw,Long> {

}
