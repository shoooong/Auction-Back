package com.example.backend.repository.Product;

import com.example.backend.dto.product.Detail.BasicInformationDto;
import com.example.backend.dto.product.Detail.SalesBiddingDto;
import com.example.backend.entity.*;
import com.example.backend.entity.enumData.BiddingStatus;
import com.example.backend.entity.enumData.SalesStatus;
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

    private final QBuyingBidding buying = QBuyingBidding.buyingBidding;
    private final QSalesBidding sales = QSalesBidding.salesBidding;

    private final QBuyingBidding subBuying = new QBuyingBidding("subBuying");

    // 소분류 상품 조회
    @Override
    @Transactional
    public List<Product> subProductInfo(String subDepartment) {

        // 각 상품별 입찰 구매희망가가 가장 낮은 가격
        JPAQuery<Long> lowPrice = queryFactory.select(subBuying.buyingBiddingPrice.min())
                .from(subBuying)
                .where(subBuying.biddingStatus.eq(BiddingStatus.PROCESS))
                .groupBy(subBuying.product.modelNum);

        log.info("minBuyingPrice: " + lowPrice);

        BooleanBuilder whereSplit = new BooleanBuilder();
        whereSplit.and(product.subDepartment.eq(subDepartment));

        log.info("서브쿼리 실행 결과 : {}", whereSplit.toString());

        return queryFactory.selectFrom(product)
                .leftJoin(buying).on(buying.product.eq(product)).fetchJoin()
                .where(whereSplit)
                .orderBy(product.createDate.desc())
                .fetch();
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
                .where(sales.product.modelNum.eq(modelNum)
                        .and(sales.salesStatus.eq(SalesStatus.PROCESS)));
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
    // 조건 1) 판매입찰과 구매입찰의 status = 종료
    // 2) 해당 종료가 될 경우 Sales 테이블의 판매단가를 상품의 최근 체결 가격에 저장
    // 3) 해당 시점의 시간을 최근 체결 날짜에 저장
    // 4) 이후 새로운 체결이 들어오면 이전에 저장된 값과 비교하여 변동가격에 저장
    // 5) 해당 변동가격을 %로 변환하여 저장
    public SalesBiddingDto RecentlyTransaction(Long productId) {

        log.info("productId 값 확인 : {}", productId);
        // SalesBidding 테이블에서 가장 최근의 거래를 가져오는 쿼리
        SalesBidding salesBidding = queryFactory.selectFrom(sales)
                .leftJoin(sales.product, product).fetchJoin() // SalesBidding과 Product를 조인
                .where(sales.product.productId.eq(productId) // Product ID가 일치하는 조건
                        .and(sales.salesStatus.eq(SalesStatus.COMPLETE))) // 판매 상태가 'COMPLETE'인 조건
                .orderBy(sales.salesBiddingTime.desc()) // 판매 입찰 시간 기준 내림차순 정렬
                .fetchFirst(); // 가장 최근의 한 개 레코드를 가져옴

        log.info("salesBidding 반환 : {}", salesBidding);
        assert salesBidding != null;
        return SalesBiddingDto.builder()
                .salesBiddingPrice(salesBidding.getSalesBiddingPrice())
                .salesBiddingTime(salesBidding.getSalesBiddingTime())
                .build();

        //        if(modelNum!=null){
//            log.info("Query Execution Started for modelNum ! : {}", modelNum);
//
//            Orders order = queryFactory.selectFrom(orders)
//                    .leftJoin(orders.bid, bid).fetchJoin()
//                    .leftJoin(bid.size, size).fetchJoin()
//                    .leftJoin(size.product, products).fetchJoin()
//                    .where(products.modelNum.eq(modelNum)
//                            .and(bid.bidStatus.eq(Bid.BidStatus.COMPLETE)))
//                    .orderBy(orders.orderDate.desc())
//                    .fetchFirst();
//
//            log.info("Orders : {}", order);
//
//            assert order != null;
//            return RecentlyPriceDTO.builder()
//                    .recentlyPrice(order.getOrderMoney())
//                    .recentlyDate(order.getOrderDate())
//                    .build();
//        }else {
//            log.info("해당 {} 모델번호를 가진 상품을 찾을 수 없습니다.", modelNum);
//            return null;
//        }
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
