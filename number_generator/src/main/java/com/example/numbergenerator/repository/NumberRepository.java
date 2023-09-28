package com.example.numbergenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.numbergenerator.entity.NumberEntity;


@Repository
public interface NumberRepository extends JpaRepository<NumberEntity, Long> {
}
