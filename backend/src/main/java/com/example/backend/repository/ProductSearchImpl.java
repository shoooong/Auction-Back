package com.example.backend.repository;

import com.example.backend.entity.*;
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

        return queryFactory.selectFrom(products)
                .join(products.category, category).fetchJoin()
                .where(category.categoryName.eq(categoryName))
                .fetch();
    }

    @Override
    @Transactional
    public void updateProducts(Long productId, String categoryName) {


    }


}
