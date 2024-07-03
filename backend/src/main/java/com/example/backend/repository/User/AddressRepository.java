package com.example.backend.repository.User;

import com.example.backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * userId로 Address 목록 전체 조회
     * default_address = true 인 데이터를 가장 상위에 정렬
     */
    @Query("SELECT a FROM Address a WHERE a.user.userId = :userId ORDER BY a.defaultAddress DESC")
    List<Address> findAllByUserId(Long userId);

    /**
     * addressId와 userId로 Address 단건 조회
     */
//    Optional<Address> findByIdAndUser_UserId(Long addressId, Long userId);
}
