package com.example.backend.repository.Orders;

import com.example.backend.dto.mypage.accountSettings.SalesSummaryDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrdersCustom {
    Page<SalesSummaryDto> findSalesHistoryByUserId(@Param("userId")Long userId, Pageable pageable);

    BigDecimal findTotalSalesAmountByUserId(@Param("userId") Long userId);


}
