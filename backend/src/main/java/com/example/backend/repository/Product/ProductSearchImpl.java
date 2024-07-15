package com.example.backend.repository.Product;

import com.example.backend.dto.product.Detail.*;
import com.example.backend.entity.*;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.ProductStatus;
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

import java.time.LocalDateTime;
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

        // 각 상품별 입찰 구매희망가가 가장 낮은 가격을 조회
        JPQLQuery<Tuple> subQuery = JPAExpressions
                .select(buying.product.modelNum, buying.buyingBiddingPrice.min())
                .from(buying)
                .where(buying.biddingStatus.eq(BiddingStatus.PROCESS)
                        .and(buying.product.productStatus.eq(ProductStatus.REGISTERED)))
                .groupBy(buying.product.modelNum);

        BooleanBuilder whereSplit = new BooleanBuilder();
        whereSplit.and(product.subDepartment.eq(subDepartment))
                .and(product.productStatus.eq(ProductStatus.REGISTERED));

        log.info("서브쿼리 실행 결과 : {}", whereSplit.toString());

        // 서브쿼리 결과를 사용하여 최소 입찰가를 가진 상품을 조회
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
    public ProductDetailDto searchProductPrice(String modelNum) {

        log.info("ModelNum : {}", modelNum);
        JPAQuery<Long> lowPrice = queryFactory.select(buying.buyingBiddingPrice.min())
                .from(buying)
                .where(buying.biddingStatus.eq(BiddingStatus.PROCESS)
                        .and(buying.product.modelNum.eq(modelNum))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED)));

        JPAQuery<Long> topPrice = queryFactory.select(sales.salesBiddingPrice.max())
                .from(sales)
                .where(sales.salesStatus.eq(SalesStatus.PROCESS)
                        .and(sales.product.modelNum.eq(modelNum))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED)));

        Long lowestPrice = lowPrice.fetchOne();
        Long highestPrice = topPrice.fetchOne();

        log.info("Lowest Price: {}", lowestPrice);
        log.info("Highest Price: {}", highestPrice);

        // PriceResponseDto 생성 및 설정
        ProductDetailDto priceValue = new ProductDetailDto();
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
                        .and(buying.biddingStatus.eq(BiddingStatus.COMPLETE))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED)))
                .orderBy(sales.salesBiddingTime.desc())
                .fetch();

        return salesBiddingDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesHopeDto> SalesHopeInfo(String modelNum) {

        return queryFactory.select(Projections.bean(SalesHopeDto.class,
                        sales.salesBiddingPrice,
                        product.productSize,
                        sales.salesQuantity))
                .from(product)
                .leftJoin(sales).on(sales.product.eq(product))
                .where(product.modelNum.eq(modelNum)
                        .and(sales.salesStatus.eq(SalesStatus.PROCESS))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED)))
                .orderBy(product.createDate.desc())
                .fetch();
    }

    @Override
    public List<BuyingHopeDto> BuyingHopeInfo(String modelNum) {
        return queryFactory.select(Projections.bean(BuyingHopeDto.class,
                        buying.buyingBiddingPrice,
                        product.productSize,
                        buying.buyingQuantity))
                .from(product)
                .leftJoin(buying).on(buying.product.eq(product))
                .where(product.modelNum.eq(modelNum)
                        .and(buying.biddingStatus.eq(BiddingStatus.PROCESS))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED)))
                .orderBy(product.createDate.desc())
                .fetch();
    }

    @Override
    public List<GroupByBuyingDto> GroupByBuyingInfo(String modelNum) {
        List<GroupByBuyingDto> groupByBuyingDtoList = queryFactory.select(Projections.bean(GroupByBuyingDto.class,
                        product.productImg,
                        product.productName,
                        product.modelNum,
                        product.productSize,
                        buying.buyingBiddingPrice.min().as("buyingBiddingPrice")))
                .from(product)
                .leftJoin(buying).on(buying.product.eq(product))
                .where(product.modelNum.eq(modelNum)
                        .and(buying.biddingStatus.eq(BiddingStatus.PROCESS))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED)))
                .groupBy(product.productSize)
                .orderBy(buying.buyingBiddingPrice.min().asc())
                .fetch();

        log.info("GroupByBuyingDtoList Success : {}", groupByBuyingDtoList);
        return groupByBuyingDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupBySalesDto> GroupBySalesInfo(String modelNum) {
        List<GroupBySalesDto> groupBySalesDtoList = queryFactory.select(Projections.bean(GroupBySalesDto.class,
                        product.productImg,
                        product.productName,
                        product.modelNum,
                        product.productSize,
                        sales.salesBiddingPrice.max().as("productMaxPrice")))
                .from(product)
                .leftJoin(sales).on(sales.product.eq(product))
                .where(product.modelNum.eq(modelNum)
                        .and(sales.salesStatus.eq(SalesStatus.PROCESS))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED)))
                .groupBy(product.productSize)
                .orderBy(sales.salesBiddingPrice.desc())
                .fetch();
        return groupBySalesDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public BuyingBidResponseDto BuyingBidResponse(BuyingBidRequestDto bidRequestDto) {

        JPAQuery<Long> lowPrice = queryFactory.select(buying.buyingBiddingPrice.min())
                .from(buying)
                .where(buying.biddingStatus.eq(BiddingStatus.PROCESS)
                        .and(buying.product.modelNum.eq(bidRequestDto.getModelNum()))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED))
                        .and(product.productSize.eq(bidRequestDto.getProductSize())));

        JPAQuery<Long> topPrice = queryFactory.select(sales.salesBiddingPrice.max())
                .from(sales)
                .where(sales.salesStatus.eq(SalesStatus.PROCESS)
                        .and(sales.product.modelNum.eq(bidRequestDto.getModelNum()))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED))
                        .and(product.productSize.eq(bidRequestDto.getProductSize())));

        Long lowestPrice = lowPrice.fetchOne();
        Long highestPrice = topPrice.fetchOne();

        // PriceResponseDto 생성 및 설정
        BuyingBidResponseDto priceValue = BuyingBidResponseDto.builder()
                .productBuyPrice(lowestPrice)
                .productSalePrice(highestPrice)
                .build();

        log.info("PriceResponseDto: {}", priceValue);

        return priceValue;
    }

    @Override
    public List<AveragePriceDto> AveragePriceInfo(String modelNum) {

        List<AveragePriceDto> averagePriceDtoList = queryFactory.select(Projections.bean(AveragePriceDto.class,
                sales.salesBiddingTime.as("contractDateTime"),
                sales.salesBiddingPrice.as("averagePrice")))
                .from(product)
                .leftJoin(sales).on(sales.product.eq(product))
                .leftJoin(buying).on(buying.product.eq(product))
                .where(product.modelNum.eq(modelNum)
                        .and(buying.biddingStatus.eq(BiddingStatus.COMPLETE))
                        .and(sales.salesStatus.eq(SalesStatus.COMPLETE))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED)))
                .orderBy(sales.salesBiddingTime.asc())
                .fetch();

        return averagePriceDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<AveragePriceDto> AveragePriceInfo(String modelNum, LocalDateTime startDate, LocalDateTime endDate) {
        List<AveragePriceDto> averagePriceDto = queryFactory.select(Projections.bean(AveragePriceDto.class,
                sales.salesBiddingTime.as("contractDateTime"),
                sales.salesBiddingPrice.as("averagePrice")))
                .from(product)
                .leftJoin(sales).on(sales.product.eq(product))
                .where(product.latestDate.between(startDate, endDate)
                        .and(product.modelNum.eq(modelNum))
                        .and(product.productStatus.eq(ProductStatus.REGISTERED))
                        .and(sales.salesStatus.eq(SalesStatus.COMPLETE)))
                .fetch();

        return averagePriceDto.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}