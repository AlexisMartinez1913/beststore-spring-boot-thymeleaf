package com.jalexisgarcia.beststore.services;

import com.jalexisgarcia.beststore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Integer> {
}
