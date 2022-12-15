package com.jf.invintoryservice.web;

import com.jf.invintoryservice.dto.ProductRequestDTO;
import com.jf.invintoryservice.entities.Category;
import com.jf.invintoryservice.entities.Product;
import com.jf.invintoryservice.repository.CategoryRepository;
import com.jf.invintoryservice.repository.ProductRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductGraphQLController {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductGraphQLController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @QueryMapping
    public List<Product> productList(){
        return productRepository.findAll();
    }

    @QueryMapping
    public Product productById(@Argument String id){
        return productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(String.format("Product %s is not found",id)));
    }

    @QueryMapping
    public List<Category> categories(){
        return categoryRepository.findAll();
    }

    @QueryMapping
    public Category categoryById(@Argument Long id){
        return categoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Category %d is not found",id)));
    }
    @MutationMapping
    public Product saveProduct(@Argument ProductRequestDTO product){
        Category category = categoryRepository.findById(product.categoryId()).orElse(null);
        Product productToSave = new Product();
        productToSave.setId(UUID.randomUUID().toString());
        productToSave.setName(product.name());
        productToSave.setPrice(product.price());
        productToSave.setQuantity(product.quantity());
        productToSave.setCategory(category);
        return productRepository.save(productToSave);
    }
    @MutationMapping
    public Product updateProduct(@Argument String id,@Argument ProductRequestDTO product){
        Category category = categoryRepository.findById(product.categoryId()).orElse(null);
        Product productToUpdate = new Product();
        productToUpdate.setId(id);
        productToUpdate.setName(product.name());
        productToUpdate.setPrice(product.price());
        productToUpdate.setQuantity(product.quantity());
        productToUpdate.setCategory(category);
        return productRepository.save(productToUpdate);
    }

    @MutationMapping
    public void deleteProduct(@Argument String id){
        productRepository.deleteById(id);
    }
}
