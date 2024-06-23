package com.example.backend.service;

import com.example.backend.dto.product.*;
import com.example.backend.entity.Bid;
import com.example.backend.entity.Products;
import com.example.backend.entity.Size;
import com.example.backend.entity.SizePrice;
import com.example.backend.repository.BidRepository;
import com.example.backend.repository.ProductsRepository;
import com.example.backend.repository.SizePriceRepository;
import com.example.backend.repository.SizeRepository;
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

    // Test 용, DTO 변환 안했을 경우
    @Override
    public List<Products> selectProductById(String categoryType) {

        List<Products> products = productsRepository.selectProductInfo(categoryType);
        log.info("Service 영역에서 Repository 로부터 받아온 값 확인 : {}", products);

        for (Products product : products) {
            log.info("Category {}, Product Info: {}", categoryType ,product);
            List<Size> sizes = sizeRepository.findByProduct(product);
            for (Size size : sizes) {
                List<SizePrice> sizePrices = sizePriceRepository.findBySize(size);
                List<Bid> bids = bidRepository.findBySize(size);

                log.info("Products Info: {}, || Size: {}, SizePrice: {}, Bid: {}", product, size, sizePrices, bids);
            }
        }
        return products;
    }

    @Override
    public List<ProductResponseDTO> selectCategoryName(String categoryName) {

        List<Products> products = productsRepository.selectProductInfo(categoryName);

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
