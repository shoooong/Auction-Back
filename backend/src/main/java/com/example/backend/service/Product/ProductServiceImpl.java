package com.example.backend.service.Product;

import com.example.backend.dto.product.*;
import com.example.backend.dto.product.Detail.BasicInformationDto;
import com.example.backend.entity.Product;
import com.example.backend.repository.Bidding.BuyingBiddingRepository;
import com.example.backend.repository.Bidding.SalesBiddingRepository;
import com.example.backend.repository.Product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final BuyingBiddingRepository buyingBiddingRepository;
    @Autowired
    private final SalesBiddingRepository salesBiddingRepository;

    @Autowired
    private final ModelMapper modelMapper;

    // 상품 소분류 조회
    @Override
    public List<ProductResponseDto> selectCategoryValue(String subDepartment) {
        log.info("subDepartment : {}", subDepartment);

        List<Product> subProduct = productRepository.subProductInfo(subDepartment);
        log.info("subProduct : {}", subProduct);

        return subProduct.stream()
                .map(this::convertProductDto)
                .collect(Collectors.toList());
    }

    private ProductResponseDto convertProductDto(Product product) {
        // 해당 product 값들을 DTO 로 변환
        ProductResponseDto productDto = modelMapper.map(product, ProductResponseDto.class);

        // Product 와 연관된 BuyingBidding 엔티티들을 BuyingDto로 변환
        List<BuyingDto> buyingDto = buyingBiddingRepository.findByProduct(product).stream()
                .map(buyingBidding -> modelMapper.map(buyingBidding, BuyingDto.class))
                .collect(Collectors.toList());

        // 변환된 BuyingDto 리스트를 ProductResponseDto 에 설정
        productDto.setBuyingDto(buyingDto);

        return productDto;
    }

    // 상품의 기본정보 조회
    @Override
    public BasicInformationDto basicInformation(String modelNum) {
        log.info("modelNum : {}", modelNum);

        Optional<Product> productOptional = productRepository.findFirstByModelNum(modelNum);
        // 망할 ModelMapper 이슈로
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            BasicInformationDto basicInformationDto = new BasicInformationDto();
            basicInformationDto.setProductId(product.getProductId());
            basicInformationDto.setProductImg(product.getProductImg());
            basicInformationDto.setProductBrand(product.getProductBrand());
            basicInformationDto.setModelNum(product.getModelNum());
            basicInformationDto.setProductName(product.getProductName());
            basicInformationDto.setOriginalPrice(product.getOriginalPrice());
            basicInformationDto.setProductLike(product.getProductLike());

            log.info("basicInformationDto DTO 변환 : {}", basicInformationDto);

            BasicInformationDto priceValue = productRepository.searchProductPrice(modelNum);
            basicInformationDto.setBuyingBiddingPrice(priceValue.getBuyingBiddingPrice());
            basicInformationDto.setSalesBiddingPrice(priceValue.getSalesBiddingPrice());
            return basicInformationDto;
        }
        return null;
    }

    // 상세 상품에 대한 입찰희망 구매/판매 가격 조회
    @Override
    public BasicInformationDto selectProductDetailPrice(String modelNum) {

        log.info("서비스 수행 전 모델번호 확인 : {}", modelNum);
        return productRepository.searchProductPrice(modelNum);
    }

    // 해당 상품의 모델번호를 통해 거래 체결된 것이 있는지 확인


    // JPA 사용 버전
    // 상품 상세 조회(기본 정보)
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

    // categoryName 에 따른 소분류 조회
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
}