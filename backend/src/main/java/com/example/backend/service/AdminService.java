package com.example.backend.service;

import com.example.backend.dto.requestProduct.RequestProductRespDto;
import com.example.backend.entity.*;
import com.example.backend.repository.Category.CategoryRepository;
import com.example.backend.repository.Product.ProductsRepository;
import com.example.backend.repository.RequestProduct.RequestProductRepository;
import com.example.backend.repository.Size.SizeRepository;
import com.example.backend.repository.Size.SizePriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminService {

    private final RequestProductRepository requestProductRepository;
    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;
    private final SizePriceRepository sizePriceRepository;

    // Approval = false 요청상품 리스트로 보기
    public RequestProductRespDto.ReqProductListRespDto getUnapprovedProducts() {
        List<RequestProduct> unapprovedProducts = requestProductRepository.findByApprovalFalse();
        return new RequestProductRespDto.ReqProductListRespDto(unapprovedProducts);
    }

    //requestId로 요청상품 단건 조회
    public RequestProductRespDto.ReqProductRespDto getOneUnapprovedProduct(Long requestId) {
//        System.out.println("리퀘스트아이디"+requestId);
        RequestProduct oneUnapprovedProduct = requestProductRepository.findById(requestId).orElseThrow();

        System.out.println("리퀘스트아이디"+requestId);

        return new RequestProductRespDto.ReqProductRespDto(oneUnapprovedProduct);
    }

    //요청 상품 판매 상품으로 등록
    @Transactional
    public RequestProductRespDto.ReqProductSaveRespDto registerReqProduct(Long requestId) {
        RequestProduct approvalProduct = requestProductRepository.findById(requestId).orElseThrow();

        //요청승인 변경 approval = true;
        approvalProduct.changeApproval(true);
        boolean register = approvalProduct.isApproval();

        System.out.println(register);

        requestProductRepository.save(approvalProduct);

        if (register) {

            Category category = categoryRepository.findById(approvalProduct.getCategory().getCategoryId()).orElseThrow();

            Products product = Products.builder()
                    .productPhoto(approvalProduct.getRequestProductImage())
                    .productBrand(approvalProduct.getBrand())
                    .productName(approvalProduct.getRequestProductName())
                    .modelNum(approvalProduct.getModelNum())
                    .originalPrice(approvalProduct.getOpenPrice())
                    .category(category)
                    .build();
            productsRepository.save(product);

            Size size = Size.builder()
                    .productSize(approvalProduct.getRequestProductSize())
                    .product(product)
                    .build();
            sizeRepository.save(size);

        }

        //상품 등록

        return null;
    }

}