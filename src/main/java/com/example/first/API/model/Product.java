package com.example.first.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;


@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;

    @Length(min=3, max=20,message = "Nom trop long ou trop court. Et oui messages sont plus stylés que ceux de Spring")
    private String nom;

    @Min(value = 1)
    private int price;

    @JsonIgnore
    private int purchasingPrice;

    public Product(){}

    public Product(String nom, int price, int purchasingPrice) {
        this.nom = nom;
        this.price = price;
        this.purchasingPrice=purchasingPrice;
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrice() {
        return price;
    }

    public int getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPurchasingPrice(int purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", price=" + price +
                '}';
    }
}
