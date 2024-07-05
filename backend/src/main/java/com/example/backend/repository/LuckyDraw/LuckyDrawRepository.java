package com.example.backend.repository.LuckyDraw;

import com.example.backend.entity.LuckyDraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LuckyDrawRepository extends JpaRepository<LuckyDraw,Long> {

    // 현재를 지난 당첨발표일과 럭키드로우id 조회
    @Query("SELECT ld FROM LuckyDraw ld WHERE ld.luckyDate <= :currentDate")
    List<LuckyDraw> findTodayLucky(LocalDateTime currentDate);

}
