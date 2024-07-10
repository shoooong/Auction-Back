package com.example.backend.repository.Product;

import com.example.backend.dto.product.Detail.BasicInformationDto;
import com.example.backend.dto.product.Detail.BuyingHopeDto;
import com.example.backend.dto.product.Detail.SalesBiddingDto;
import com.example.backend.dto.product.Detail.SalesHopeDto;
import com.example.backend.entity.*;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.SalesStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
@AllArgsConstructor
@Log4j2

public class ProductSearchImpl implements ProductSearch {

    private final JPAQueryFactory queryFactory;

    private final QProduct product = QProduct.product;

    private final QBuyingBidding buying = QBuyingBidding.buyingBidding;
    private final QSalesBidding sales = QSalesBidding.salesBidding;

    // 소분류 상품 조회
    @Override
    public List<Product> subProductInfo(String subDepartment) {

        // 서브쿼리 : 각 상품별 입찰 구매희망가가 가장 낮은 가격을 조회
        JPQLQuery<Tuple> subQuery = JPAExpressions
                .select(buying.product.modelNum, buying.buyingBiddingPrice.min())
                .from(buying)
                .where(buying.biddingStatus.eq(BiddingStatus.PROCESS))
                .groupBy(buying.product.modelNum);

        BooleanBuilder whereSplit = new BooleanBuilder();
        whereSplit.and(product.subDepartment.eq(subDepartment));

        log.info("서브쿼리 실행 결과 : {}", whereSplit.toString());

        // 메인 쿼리: 서브쿼리 결과를 사용하여 최소 입찰가를 가진 상품을 조회
        List<Product> products = queryFactory.selectFrom(product)
                .leftJoin(buying).on(buying.product.eq(product).and(buying.biddingStatus.eq(BiddingStatus.PROCESS)))
                .where(whereSplit.and(
                        Expressions.list(product.modelNum, buying.buyingBiddingPrice).in(subQuery)
                ))
                .distinct()
                .orderBy(product.createDate.desc())
                .fetch();

        log.info("최종 실행 결과 : {}", products);

        return products;
    }

    // 해당 상품의 사이즈 상관없이 구매(최저), 판매(최고)가 조회
    @Override
    public BasicInformationDto searchProductPrice(String modelNum) {

        log.info("ModelNum : {}", modelNum);
        JPAQuery<Long> lowPrice = queryFactory.select(buying.buyingBiddingPrice.min())
                .from(buying)
                .where(buying.biddingStatus.eq(BiddingStatus.PROCESS)
                        .and(buying.product.modelNum.eq(modelNum)));
        log.info("구매 입찰 최저가 뽑기: " + lowPrice);

        JPAQuery<Long> topPrice = queryFactory.select(sales.salesBiddingPrice.max())
                .from(sales)
                .where(sales.salesStatus.eq(SalesStatus.PROCESS)
                        .and(sales.product.modelNum.eq(modelNum)));
        log.info("판매 입찰 최고가 뽑기: " + lowPrice);

        Long lowestPrice = lowPrice.fetchOne();
        Long highestPrice = topPrice.fetchOne();

        log.info("Lowest Price: {}", lowestPrice);
        log.info("Highest Price: {}", highestPrice);

        // PriceResponseDto 생성 및 설정
        BasicInformationDto priceValue = new BasicInformationDto();
        priceValue.setBuyingBiddingPrice(lowestPrice);
        priceValue.setSalesBiddingPrice(highestPrice);

        log.info("PriceResponseDto: {}", priceValue);

        return priceValue;
    }

    @Override
    public List<SalesBiddingDto> recentlyTransaction(String modelNum) {

         List<SalesBiddingDto> salesBiddingDtoList = queryFactory.select(Projections.bean(SalesBiddingDto.class,
                product.productId,
                product.modelNum,
                product.productSize,
                product.latestPrice,
                product.previousPrice,
                product.previousPercentage,
                sales.salesBiddingTime.as("salesBiddingTime"),
                sales.salesBiddingPrice.as("salesBiddingPrice")
                ))
                .from(product)
                .leftJoin(sales).on(sales.product.eq(product))
                .leftJoin(buying).on(buying.product.eq(product))
                .where(product.modelNum.eq(modelNum)
                        .and(sales.salesStatus.eq(SalesStatus.COMPLETE))
                        .and(buying.biddingStatus.eq(BiddingStatus.COMPLETE)))
                .orderBy(sales.salesBiddingTime.desc())
                .fetch();

        return salesBiddingDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesHopeDto> SalesHopeInfo(String modelNum) {

        List<SalesHopeDto> salesHopeDtoList =  queryFactory.select(Projections.bean(SalesHopeDto.class,
                        sales.salesBiddingPrice,
                        product.productSize,
                        sales.salesQuantity))
                .from(product)
                .leftJoin(sales).on(sales.product.eq(product))
                .where(product.modelNum.eq(modelNum)
                        .and(sales.salesStatus.eq(SalesStatus.PROCESS)))
                .orderBy(sales.salesBiddingPrice.asc())
                .fetch();

        return salesHopeDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<BuyingHopeDto> BuyingHopeInfo(String modelNum) {
        List<BuyingHopeDto> buyingHopeDtoList =  queryFactory.select(Projections.bean(BuyingHopeDto.class,
                        buying.buyingBiddingPrice,
                        product.productSize,
                        buying.buyingQuantity))
                .from(product)
                .leftJoin(buying).on(buying.product.eq(product))
                .where(product.modelNum.eq(modelNum)
                        .and(buying.biddingStatus.eq(BiddingStatus.PROCESS)))
                .orderBy(buying.buyingBiddingPrice.desc())
                .fetch();

        return buyingHopeDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}