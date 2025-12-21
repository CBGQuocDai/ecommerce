package com.backend.repository;

import com.backend.entity.Province;
import com.backend.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardRepository extends JpaRepository<Ward, Long> {
    Ward findByProvinceAndName(Province p, String ward);
}
