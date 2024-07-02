package com.example.backend.repository.Product;

import com.example.backend.dto.admin.AdminProductDto;
import com.example.backend.entity.Product;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminProduct {

    List<AdminProductDto> getProductsByDepartment(@Param("mainDepartment") String mainDepartment,@Param("subDepartment") String subDepartment);
}
