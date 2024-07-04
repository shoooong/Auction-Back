package com.example.backend.repository.LuckyDraw;

import com.example.backend.dto.mypage.drawHistory.DrawDetailsDto;
import com.example.backend.entity.Draw;
import com.example.backend.entity.LuckyDraw;
import com.example.backend.entity.enumData.LuckyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {

    // 전체 럭키드로우 응모 건수
    @Query("SELECT COUNT(d) FROM Draw d WHERE d.user.userId = :userId")
    Long countAllByUserId(Long userId);

    // 진행 중 건수
    @Query("SELECT COUNT(d) FROM Draw d WHERE d.user.userId = :userId AND d.luckyStatus ='PROCESS'")
    Long countProcessByUserId(Long userId);

    // 당첨 건수
    @Query("SELECT COUNT(d) FROM Draw d WHERE d.user.userId = :userId AND d.luckyStatus ='LUCKY'")
    Long countLuckyByUserId(Long userId);

    // TODO: QueryDSL로 변경할 것
    // 럭키드로우 응모 상세 정보
    @Query("SELECT new com.example.backend.dto.mypage.drawHistory.DrawDetailsDto(l.luckyImage, l.luckyName, l.luckySize, l.luckyDate, d.luckyStatus) " +
            "FROM Draw d JOIN d.luckyDraw l JOIN d.user u " +
            "WHERE u.userId = :userId " +
            "ORDER BY l.luckyDate DESC")
    List<DrawDetailsDto> findDrawDetailsByUserId(Long userId);

    Draw findByLuckyDrawAndLuckyStatus(LuckyDraw luckyDraw, LuckyStatus luckyStatus);
}
