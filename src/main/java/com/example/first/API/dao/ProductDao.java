package com.example.first.API.dao;

import com.example.first.API.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    List<Product> findAll();
    Product findById(int id);
    Product save(Product product);
    void deleteById(int id);
    List<Product> findByPriceGreaterThan(int priceLimit);
    List<Product> findByNomLike(String recherche);
    @Query("SELECT id, nom, price FROM Product p WHERE p.price > : priceLimit")
    List<Product> chercherUnProduitCher(@Param("prixLimit") int price);
}

