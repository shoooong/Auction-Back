//package com.example.backend.service;
//
//import com.example.backend.dto.product.*;
//import com.example.backend.entity.Product;
//import com.example.backend.repository.Bid.BidRepository;
//import com.example.backend.repository.Product.ProductsRepository;
//import com.example.backend.repository.Size.SizePriceRepository;
//import com.example.backend.repository.Size.SizeRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Log4j2
//@Transactional
//@RequiredArgsConstructor
//public class ProductServiceImpl implements ProductService {
//
//    private final ProductsRepository productsRepository;
//    private final SizeRepository sizeRepository;
//    private final SizePriceRepository sizePriceRepository;
//    private final BidRepository bidRepository;
//    private final ModelMapper modelMapper;
//
//    // JPA 사용 버전
//    // 상품 상세 조회(기본 정보)
//    @Override
//    public OnlyProductResponseDTO detailProductSelect(OnlyProductRequestDTO onlyProductRequestDTO) {
//
//        log.info("Query Execution Started for modelNum : {}", onlyProductRequestDTO.getModelNum());
//        Product products = productsRepository.findFirstByModelNum(onlyProductRequestDTO.getModelNum());
//        log.info("Query Execution Completed Info : {}", products);
//
//        return modelMapper.map(products, OnlyProductResponseDTO.class);
//    }
//
//    // 해당 상품에 대한 구매 / 판매 가격 가져오기
//    @Override
//    public PriceResponseDTO selectProductPrice(OnlyProductRequestDTO onlyProductRequestDTO) {
//        log.info("상품 조회하고 가격까지 조회하러 왔습니다~ : {}", onlyProductRequestDTO.getModelNum());
//        PriceResponseDTO products = productsRepository.searchProductPrice(onlyProductRequestDTO.getModelNum());
//        log.info("해당 상품에 대한 가격 반환까지 완료되었습니다~ : {}", products);
//
//        return modelMapper.map(products, PriceResponseDTO.class);
//    }
//
//    // categoryName 에 따른 소분류 조회
//    @Override
//    public List<ProductResponseDTO> selectCategoryValue(CategoryDTO categoryDTO) {
//
//        List<Product> products = productsRepository.allProductInfo(categoryDTO.getCategoryName());
//        return products.stream()
//                .map(this::convertProductToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private ProductResponseDTO convertProductToDTO(Product product) {
//
//        // 해당 product 값들을 DTO로 변환
//        ProductResponseDTO productDTO = modelMapper.map(product, ProductResponseDTO.class);
//
//        // Products 엔티티에 연관되어있는 Size엔티티들을 SizeDTO로 변환
//        List<SizeDTO> sizes = sizeRepository.findByProduct(product).stream()
//                .map(size -> {
//                    // 각 Size 엔티티를 SizeDTO로 변환
//                    SizeDTO sizeDTO = modelMapper.map(size, SizeDTO.class);
//
//                    // Size 엔티티에 연관된 SizePrice -> DTO 변환
//                    List<SizePriceDTO> sizePrices = sizePriceRepository.findBySize(size).stream()
//                            .map(sizePrice -> modelMapper.map(sizePrice, SizePriceDTO.class))
//                            .collect(Collectors.toList());
//
//                    // Size 엔티티에 연관된 Bid -> DTO 변환
//                    List<BidDTO> bids = bidRepository.findBySize(size).stream()
//                            .map(bid -> modelMapper.map(bid, BidDTO.class))
//                            .collect(Collectors.toList());
//
//                    // 변환된 DTO를 SizeDTO에 저장
//                    sizeDTO.setSizePrices(sizePrices);
//                    sizeDTO.setBids(bids);
//                    return sizeDTO;
//                })
//                // Stream -> List 변환
//                .collect(Collectors.toList());
//
//        // SizeDTO -> Products 저장
//        productDTO.setSizes(sizes);
//        return productDTO;
//    }
//}