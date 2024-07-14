package com.example.backend.repository.Product;

import com.example.backend.dto.product.TotalProductDto;
import com.example.backend.entity.QBuyingBidding;
import com.example.backend.entity.QProduct;
import com.example.backend.entity.QSalesBidding;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Condition;

@RequiredArgsConstructor
@Log4j2
@PersistenceContext
public class ShopProductImpl implements ShopProduct {
    private final JPQLQueryFactory queryFactory;

    QProduct product = QProduct.product;
    QBuyingBidding buyingBidding = QBuyingBidding.buyingBidding;

    // 필터링
//    public BooleanBuilder createFilterBuilder(String subDepartment, QProduct product){
//        BooleanBuilder filterBuilder = new BooleanBuilder();
//
//        // 소분류별 필터링
//        addDepartmentFilters(subDepartment, product, filterBuilder);
//
//        return filterBuilder;
//    }
//
//    // 필터링 조건
//    private void addDepartmentFilters(String subDepartment, QProduct product, BooleanBuilder filterBuilder){
//        if (subDepartment != null) {
//            filterBuilder.andAnyOf(
//                    product.subDepartment.eq(subDepartment)
//            );
//        }
//    }

    // 모든 상품 조회
    @Override
    public Slice<TotalProductDto> allProduct(Pageable pageable) {

        int pageSize = pageable.getPageSize();

        List<TotalProductDto> products = queryFactory
                .select(Projections.constructor(TotalProductDto.class,
                        product.modelNum,
                        product.productId,
                        product.productBrand,
                        product.productName,
                        product.subDepartment,
                        product.productImg,
                        buyingBidding.buyingBiddingPrice.min()
                        ))
                .from(product)
                .leftJoin(buyingBidding).on(product.productId.eq(buyingBidding.product.productId))
                .groupBy(product.modelNum)
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();

        // 다음 페이지 유무
        boolean hasNext = false;
        if(products.size() > pageSize){
            products.remove(pageSize);
            hasNext = true;
        }

        // Slice 객체 변환
        return new SliceImpl<>(products, pageable, hasNext);
    }
}
