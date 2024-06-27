package com.example.backend.repository.Product;

import com.example.backend.dto.product.PriceResponseDTO;
import com.example.backend.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.example.backend.entity.Bid.BidKind.BUY;
import static com.example.backend.entity.Bid.BidKind.SELL;

@Repository
@AllArgsConstructor
@Log4j2

public class ProductSearchImpl implements ProductSearch {

    private final JPAQueryFactory queryFactory;

    private static final QProducts products = QProducts.products;
    private static final QCategory category = QCategory.category;
    private static final QSize size = QSize.size;
    private static final QSizePrice sizePrice = QSizePrice.sizePrice;
    private static final QBid bid = QBid.bid;

    // Sub Query : 구매/판매 희망가 조회, SubBid 는 별명
    private static final QSizePrice subSizePrice = new QSizePrice("subSizePrice");
    private static final QBid subBid = new QBid("subBid");

    @Override
    @Transactional
    // categoryName 에 대한 소분류 전체 보기
    public List<Products> allProductInfo(String categoryName) {

        JPAQuery<BigDecimal> lowPrice = queryFactory.select(subSizePrice.sellPrice.min())
                .from(subSizePrice)
                .leftJoin(subSizePrice.size, size)
                .leftJoin(size.product, products)
                .leftJoin(subBid).on(subBid.size.eq(size))
                .where(sizePrice.size.product.modelNum.eq(products.modelNum)
                        .and(subBid.bidKind.eq(BUY)));

        BooleanBuilder whereSplit = new BooleanBuilder();
        whereSplit.and(category.categoryName.eq(categoryName))
                .and(sizePrice.sellPrice.eq(lowPrice));

        // Main 부분
        return queryFactory.selectFrom(products)
                // products 와 연결된 category 엔티티 값
                .leftJoin(products.category, category).fetchJoin()
                // 해당 size 와 product 클래스와 동일한 값
                .leftJoin(size).on(size.product.eq(products)).fetchJoin()
                // SizePrice 와 size 클래스와 동일한 값
                .leftJoin(sizePrice).on(sizePrice.size.eq(size)).fetchJoin()
                // bid 엔티티와 size 엔티티가 연결된 값
                .leftJoin(bid).on(bid.size.eq(size)).fetchJoin()
                .where(whereSplit)
                .orderBy(bid.bidStartDate.asc())
                .fetch();
    }

    // modelNum = "사용자가 접속한 상품 번호",  bidKind = BUY,
    @Override
    public PriceResponseDTO searchProductPrice(String modelNum) {
        log.info("Query Execution Started for modelNum : {}", modelNum);

        // 최저가 서브쿼리
        JPAQuery<BigDecimal> lowPrice = queryFactory.select(subSizePrice.sellPrice.min())
                .from(subSizePrice)
                .leftJoin(subSizePrice.size, size)
                .leftJoin(size.product, products)
                .leftJoin(subBid).on(subBid.size.eq(size))
                .where(products.modelNum.eq(modelNum)
                        .and(subBid.bidKind.eq(BUY)));

        // 최고가 서브쿼리
        JPAQuery<BigDecimal> topPrice = queryFactory.select(subSizePrice.sellPrice.max())
                .from(subSizePrice)
                .leftJoin(subSizePrice.size, size)
                .leftJoin(size.product, products)
                .leftJoin(subBid).on(subBid.size.eq(size))
                .where(products.modelNum.eq(modelNum)
                        .and(subBid.bidKind.eq(SELL)));

        BigDecimal highestPrice = topPrice.fetchOne();
        BigDecimal lowestPrice = lowPrice.fetchOne();
        log.info("Highest Price: {}", highestPrice);

        PriceResponseDTO responseDTO = PriceResponseDTO.builder()
                .expectBuyPrice(lowestPrice)
                .expectSellPrice(highestPrice)
                .build();

        return responseDTO;
    }
}
