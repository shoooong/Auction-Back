package com.example.backend.repository.Product;

import com.example.backend.entity.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor

public class ProductSearchImpl implements ProductSearch {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public List<Products> selectProductInfo(String categoryName) {
        QProducts products = QProducts.products;
        QCategory category = QCategory.category;
        QSize size = QSize.size;
        QSizePrice sizePrice = QSizePrice.sizePrice;
        QBid bid = QBid.bid;

        // Sub Query : 구매/판매 가격 검색
        QBid subBid = new QBid("subBid");
        JPAQuery<Integer> minBidPrice = queryFactory.select(subBid.bidPrice.min())
                .from(subBid)
                .where((bid.bidKind.eq(Bid.BidKind.BUY)));

        return queryFactory.selectFrom(products)
                // products와 연결된 category엔티티
                .leftJoin(products.category, category).fetchJoin()
                // 해당 size와 product클래스와 동일한 값
                .leftJoin(size).on(size.product.eq(products)).fetchJoin()
                // SizePrice와 size클래스와 동일한 값
                .leftJoin(sizePrice).on(sizePrice.size.eq(size)).fetchJoin()
                // bid엔티티와 size엔티티가 연결된 값
                .leftJoin(bid).on(bid.size.eq(size)).fetchJoin()
                .where(category.categoryName.eq(categoryName)
                                .and(bid.bidKind.eq(Bid.BidKind.BUY)))
                .fetch();
    }

//    private BooleanExpression eqBidKindAndBidPrice() {
//
//    }

    @Override
    @Transactional
    public void updateProducts(Long productId, String categoryName) {


    }


}
