package com.example.backend.repository.Product;

import com.example.backend.dto.product.AllProductDto;
import com.example.backend.entity.QBuyingBidding;
import com.example.backend.entity.QProduct;
import com.example.backend.entity.QSalesBidding;
import com.example.backend.entity.enumData.ProductStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@PersistenceContext
public class ShopSearchImpl implements ShopSearch {
    private final JPQLQueryFactory queryFactory;

    QProduct product = QProduct.product;
    QBuyingBidding buyingBidding = QBuyingBidding.buyingBidding;
    QSalesBidding salesBidding = QSalesBidding.salesBidding;

    @Override
    public Slice<AllProductDto> getKeywordSearch(String keyword, Pageable pageable) {
        int pageSize = pageable.getPageSize();

        List<AllProductDto> searchResult = queryFactory
                .select(Projections.constructor(AllProductDto.class,
                        product.modelNum,
                        product.productId,
                        product.productBrand,
                        product.productName,
                        product.mainDepartment,
                        product.subDepartment,
                        product.productImg,
                        buyingBidding.buyingBiddingPrice.min()
                ))
                .from(product)
                .leftJoin(buyingBidding).on(product.productId.eq(buyingBidding.product.productId))
                .leftJoin(salesBidding).on(product.productId.eq(salesBidding.product.productId))
                .where(product.productStatus.eq(ProductStatus.valueOf("REGISTERED"))
                        .and(
                                product.productName.contains(keyword)
                                        .or(product.productBrand.contains(keyword))
                        )
                )
                .groupBy(product.modelNum)
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();

        // 다음 페이지 유무
        boolean hasNext = false;
        if(searchResult.size() > pageSize){
            searchResult.remove(pageSize);
            hasNext = true;
        }

        // Slice 객체 변환
        return new SliceImpl<>(searchResult, pageable, hasNext);
    }
}
