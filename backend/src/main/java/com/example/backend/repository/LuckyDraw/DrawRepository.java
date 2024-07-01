package com.example.backend.repository.LuckyDraw;

import com.example.backend.entity.Draw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {

}
