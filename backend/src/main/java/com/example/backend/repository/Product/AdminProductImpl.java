package com.example.backend.repository.Product;

import com.example.backend.dto.admin.AdminProductDto;
import com.example.backend.entity.QProduct;
import com.example.backend.entity.enumData.ProductStatus;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class AdminProductImpl implements AdminProduct {

    private final JPAQueryFactory queryFactory;



    QProduct product = new QProduct("product");
//    QProduct product = QProduct.product;

//    대분류의 값이 들어오면
    private BooleanExpression eqMain(String mainDepartment){
        if (StringUtils.isBlank(mainDepartment)){
            //대분류 값이 들어오지 않으면 Null 리턴
            return null;
        }
        //대분류에 값이 들어오면, selectfrom(product) where p.mainDepartment = :mainDepartment
        return product.mainDepartment.eq(mainDepartment);
    }

    private BooleanExpression eqSub(String subDepartment){

        if (StringUtils.isBlank(subDepartment)){
            //대분류 값이 들어오지 않으면 Null 리턴
            return null;
        }
        //대분류에 값이 들어오면, selectfrom(product) where p.subDepartment = :subDepartment
        return product.subDepartment.eq(subDepartment);
    }


    @Override
    public List<AdminProductDto> getProductsByDepartment(String mainDepartment, String subDepartment) {

        log.info("mainDepartment {} subDepartment from 쿼리디에셀", mainDepartment, subDepartment);

        return queryFactory.select(
                        Projections.fields(AdminProductDto.class, product.productName, product.productBrand,product.modelNum)
                ).from(product)
                .where(
                        eqMain(mainDepartment),
                        eqSub(subDepartment)
                )
                .groupBy(product.productName,
                        product.productBrand,
                        product.modelNum)
                .fetch();

    }
}
