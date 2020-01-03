package com.example.first.API.web.controller;

import com.example.first.API.dao.ProductDao;
import com.example.first.API.exceptions.ProduitIntrouvableException;
import com.example.first.API.exceptions.ProduitSansMargeException;
import com.example.first.API.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(value = "API pour es opérations CRUD sur les produits.")
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("purchasingPrice");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("mon filtreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }

    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @RequestMapping(value = "/Produits/{id}", method = RequestMethod.GET)
    public Product afficheUnProduit(@PathVariable int id) throws ProduitIntrouvableException {
        Product produit = productDao.findById(id);
        if (produit == null)
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        return produit;
    }

    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> saveProduct(@Valid @RequestBody Product product) {

        Product productAdded = productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {
        productDao.deleteById(id);
    }

    @GetMapping(value = "/test/produits/{priceLimit}")
    public List<Product> testeDeRequetes(@PathVariable int priceLimit) {
        return productDao.findByPriceGreaterThan(priceLimit);
    }

    @GetMapping(value = "/test/produits/search/{recherche}")
    public List<Product> testeDeSearch(@PathVariable String recherche) {
        return productDao.findByNomLike("%" + recherche + "%");
    }

    @PutMapping(value = "/Produits")
    public void updateProduit(@Valid @RequestBody Product product) {
        productDao.save(product);
    }

    @ApiOperation(value = "Récupère la marge d'un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/Marge/{id}")
    public int calculerMargeProduit(@PathVariable int id) {
        Product produit = productDao.findById(id);
        if (produit == null)
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        if (produit.getPurchasingPrice() <= produit.getPrice())
            return produit.getPrice() - produit.getPurchasingPrice();
        else
            throw new ProduitSansMargeException("Le produit avec l'id" + id + " ne genere pas de marge, au contraire il est deficitaire !");
    }

    @ApiOperation(value = "Récupère uniquement la marge des produits ayant une marge")
    @GetMapping(value = "/AdminProduits")
    public List<String> MargeProduits() {
        List<String> products = new ArrayList<>();
        for (Product product : productDao.findAll()) {
            if (product.getPurchasingPrice() <= product.getPrice()) {
                products.add(product.toString() + ":" + Math.abs(product.getPrice() - product.getPurchasingPrice()));
            }
        }
        return products;
    }
}
