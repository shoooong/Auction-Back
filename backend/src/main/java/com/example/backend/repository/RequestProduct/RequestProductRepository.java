package com.example.backend.repository.RequestProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestProductRepository extends JpaRepository<RequestProduct, Long> {

    // jpa query method
    // select * from account where user_id = :id
    //판매 요청 상품 전체 조회
    List<RequestProduct> findByApprovalFalse();


    //판매 요청 상품 단건 조회
    //클라이언트에서 보낸 requestId 파라미터 매핑
    Optional<RequestProduct> findById(@Param("requestId") Long requestId);


}
