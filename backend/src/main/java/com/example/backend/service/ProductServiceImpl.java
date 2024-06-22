package com.example.backend.service;

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;
    private final SizeRepository sizeRepository;
    private final SizePriceRepository sizePriceRepository;
    private final BidRepository bidRepository;

    @Override
    public List<Products> selectCategotyType(String productType) {

        List<Products> products = productsRepository.selectProductInfo("의류");
        for (Products product : products) {
            log.info("Category '의류' Product Info: {}", product);
            List<Size> sizes = sizeRepository.findByProduct(product);
            for (Size size : sizes) {
                List<SizePrice> sizePrices = sizePriceRepository.findBySize(size);
                List<Bid> bids = bidRepository.findBySize(size);

                log.info("Products Info: {}, || Size: {}, SizePrice: {}, Bid: {}", product, size, sizePrices, bids);
            }
        }
        return products;
    }
}
