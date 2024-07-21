package com.example.backend.dto.orders;


import com.example.backend.dto.product.AllProductDto;
import com.example.backend.dto.product.ProductResponseDto;
import com.example.backend.dto.user.UserDTO;
import com.example.backend.entity.BuyingBidding;
import com.example.backend.entity.Product;
import com.example.backend.entity.Users;
import com.example.backend.entity.enumData.BiddingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BuyingBiddingDto {
    private Long buyingBiddingId;

    private OrderProductDto product;

    private BigDecimal buyingBiddingPrice;

    private int buyingQuantity;

    private LocalDateTime buyingBiddingTime;

    private BiddingStatus biddingStatus;

//    public BuyingBidding toEntity() {
//        return BuyingBidding.builder()
//            .buyingBiddingId(this.buyingBiddingId)
//            .product(this.prordcuㅅ)
//            .buyingBiddingPrice(this.buyingBiddingPrice)
//            .buyingQuantity(this.buyingQuantity)
//            .buyingBiddingTime(this.buyingBiddingTime)
//            .biddingStatus(this.biddingStatus)
//            .build();
//    }
//}

}
