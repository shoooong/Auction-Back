package com.example.backend.repository.LuckyDraw;

import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.enumData.LuckyProcessStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LuckyDrawRepository extends JpaRepository<LuckyDraw,Long> {

    // 메인페이지에서 응모 진행 중인 상품만 조회
    @Query("SELECT ld FROM LuckyDraw ld WHERE ld.luckyProcessStatus = :luckyProcessStatus")
    List<LuckyDraw> findByProcess(LuckyProcessStatus luckyProcessStatus);

    // 현재를 지난 당첨발표일과 럭키드로우id 조회
    @Query("SELECT ld FROM LuckyDraw ld WHERE ld.luckyDate <= :currentDate")
    List<LuckyDraw> findTodayLucky(LocalDateTime currentDate);

    // endStatus 상태 변경
    @Modifying
    @Query("UPDATE LuckyDraw ld SET ld.luckyProcessStatus = :luckyProcessStatus WHERE ld.luckyId = :luckyId")
    void updateEndStatus(Long luckyId, LuckyProcessStatus luckyProcessStatus);

}
