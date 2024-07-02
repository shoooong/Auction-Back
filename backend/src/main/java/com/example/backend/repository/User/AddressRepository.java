package com.example.backend.repository.User;

import com.example.backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * userId로 Address 목록 전체 조회
     */
    List<Address> findByUser_UserId(Long userId);

    /**
     * addressId와 userId로 Address 단건 조회
     */
//    Optional<Address> findByIdAndUser_UserId(Long addressId, Long userId);
}
