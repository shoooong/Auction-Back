package com.example.backend.repository.Bid;

import com.example.backend.entity.Bid;
import com.example.backend.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findBySize(Size size);
}
