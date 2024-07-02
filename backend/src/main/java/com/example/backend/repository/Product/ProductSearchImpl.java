package com.example.backend.repository.Product;

import com.example.backend.entity.*;
import com.example.backend.entity.enumData.BinddingStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
@Log4j2

public class ProductSearchImpl implements ProductSearch {

    private final JPAQueryFactory queryFactory;

    private final QProduct product = QProduct.product;
    private final QBuyingBidding mainBuying = QBuyingBidding.buyingBidding;

    private final QBuyingBidding subBuying = new QBuyingBidding("subBuying");

    @Override
    @Transactional
    public List<Product> subProductInfo(String subDepartment) {

        // 각 상품별 입찰 구매희망가가 가장 낮은 가격
        JPAQuery<Long> lowPrice = queryFactory.select(subBuying.buyingPrice.min())
                .from(subBuying)
                .where(subBuying.binddingStatus.eq(BinddingStatus.PROCESS)
                        .and(subBuying.product.subDepartment.eq(subDepartment)))
                .groupBy(subBuying.product.modelNum);

        log.info("minBuyingPrice: " + lowPrice);

        BooleanBuilder whereSplit = new BooleanBuilder();
        whereSplit.and(product.subDepartment.eq(subDepartment))
                .and(mainBuying.buyingPrice.in(lowPrice));

        log.info("서브쿼리 실행 결과 : {}", whereSplit.toString());

        return queryFactory.selectFrom(product)
                .leftJoin(mainBuying).on(mainBuying.product.eq(product)).fetchJoin()
                .where(whereSplit)
                .orderBy(product.createDate.desc())
                .fetch();
    }

//    @Override
//    @Transactional
//    // categoryName 에 대한 소분류 전체 보기
//    public List<Product> allProductInfo(String categoryName) {
//
//        JPAQuery<BigDecimal> lowPrice = queryFactory.select(subSizePrice.sellPrice.min())
//                .from(subSizePrice)
//                .leftJoin(subSizePrice.size, size)
//                .leftJoin(size.product, products)
//                .leftJoin(subBid).on(subBid.size.eq(size))
//                .where(sizePrice.size.product.modelNum.eq(products.modelNum)
//                        .and(subBid.bidKind.eq(BUY)));
//
//        BooleanBuilder whereSplit = new BooleanBuilder();
//        whereSplit.and(category.categoryName.eq(categoryName))
//                .and(sizePrice.sellPrice.eq(lowPrice));
//
//        // Main 부분
//        return queryFactory.selectFrom(products)
//                // products 와 연결된 category 엔티티 값
//                .leftJoin(products.category, category).fetchJoin()
//                // 해당 size 와 product 클래스와 동일한 값
//                .leftJoin(size).on(size.product.eq(products)).fetchJoin()
//                // SizePrice 와 size 클래스와 동일한 값
//                .leftJoin(sizePrice).on(sizePrice.size.eq(size)).fetchJoin()
//                // bid 엔티티와 size 엔티티가 연결된 값
//                .leftJoin(bid).on(bid.size.eq(size)).fetchJoin()
//                .where(whereSplit)
//                .orderBy(bid.bidStartDate.asc())
//                .fetch();
//    }
//
//    // modelNum = "사용자가 접속한 상품 번호",  bidKind = BUY,
//    @Override
//    public PriceResponseDTO searchProductPrice(String modelNum) {
//        log.info("Query Execution Started for modelNum : {}", modelNum);
//
//        // 최저가 서브쿼리
//        JPAQuery<BigDecimal> lowPrice = queryFactory.select(subSizePrice.sellPrice.min())
//                .from(subSizePrice)
//                .leftJoin(subSizePrice.size, size)
//                .leftJoin(size.product, products)
//                .leftJoin(subBid).on(subBid.size.eq(size))
//                .where(products.modelNum.eq(modelNum)
//                        .and(subBid.bidKind.eq(BUY)));
//
//        // 최고가 서브쿼리
//        JPAQuery<BigDecimal> topPrice = queryFactory.select(subSizePrice.sellPrice.max())
//                .from(subSizePrice)
//                .leftJoin(subSizePrice.size, size)
//                .leftJoin(size.product, products)
//                .leftJoin(subBid).on(subBid.size.eq(size))
//                .where(products.modelNum.eq(modelNum)
//                        .and(subBid.bidKind.eq(SELL)));
//
//        BigDecimal highestPrice = topPrice.fetchOne();
//        BigDecimal lowestPrice = lowPrice.fetchOne();
//        log.info("Highest Price: {}", highestPrice);
//
//        PriceResponseDTO responseDTO = PriceResponseDTO.builder()
//                .expectBuyPrice(lowestPrice)
//                .expectSellPrice(highestPrice)
//                .build();
//
//        return responseDTO;
//    }
}
