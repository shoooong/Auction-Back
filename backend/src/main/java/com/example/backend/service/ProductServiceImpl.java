package com.example.backend.service;

import com.example.backend.dto.product.*;
import com.example.backend.entity.Bid;
import com.example.backend.entity.Products;
import com.example.backend.entity.Size;
import com.example.backend.entity.SizePrice;
import com.example.backend.repository.Bid.BidRepository;
import com.example.backend.repository.Product.ProductsRepository;
import com.example.backend.repository.Size.SizePriceRepository;
import com.example.backend.repository.Size.SizeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;
    private final SizeRepository sizeRepository;
    private final SizePriceRepository sizePriceRepository;
    private final BidRepository bidRepository;
    private final ModelMapper modelMapper;

    // JPA 사용 버전
    @Override
    public List<ProductResponseDTO> detailProductSelect(String modelNum) {
        log.info("Query Execution Started for modelNum : {}", modelNum);
        List<Products> result = productsRepository.findByModelNum(modelNum);
        log.info("Query Execution Completed: modelNum Size: {}", result.size());

        return result.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // QueryDSL 사용 버전
    @Override
    public ProductResponseDTO detailProductInfo(String modelNum) {
        Products product = productsRepository.detailProductInfo(modelNum);
        log.info("Product found: {}", product);
        return convertToDTO(product);
    }

    @Override
    public List<ProductResponseDTO> selectCategoryValue(String categoryValue) {

        List<Products> products = productsRepository.allProductInfo(categoryValue);

        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductResponseDTO convertToDTO(Products product) {

        // 해당 product 값들을 DTO로 변환
        ProductResponseDTO productDTO = modelMapper.map(product, ProductResponseDTO.class);

        // Products 엔티티에 연관되어있는 Size엔티티들을 SizeDTO로 변환
        List<SizeDTO> sizes = sizeRepository.findByProduct(product).stream()
                .map(size -> {
                    // 각 Size 엔티티를 SizeDTO로 변환
                    SizeDTO sizeDTO = modelMapper.map(size, SizeDTO.class);

                    // Size 엔티티에 연관된 SizePrice -> DTO 변환
                    List<SizePriceDTO> sizePrices = sizePriceRepository.findBySize(size).stream()
                            .map(sizePrice -> modelMapper.map(sizePrice, SizePriceDTO.class))
                            .collect(Collectors.toList());

                    // Size 엔티티에 연관된 Bid -> DTO 변환
                    List<BidDTO> bids = bidRepository.findBySize(size).stream()
                            .map(bid -> modelMapper.map(bid, BidDTO.class))
                            .collect(Collectors.toList());

                    // 변환된 DTO를 SizeDTO에 저장
                    sizeDTO.setSizePrices(sizePrices);
                    sizeDTO.setBids(bids);
                    return sizeDTO;
                })
                // Stream -> List 변환
                .collect(Collectors.toList());

        // SizeDTO -> Products 저장
        productDTO.setSizes(sizes);
        return productDTO;
    }
}
