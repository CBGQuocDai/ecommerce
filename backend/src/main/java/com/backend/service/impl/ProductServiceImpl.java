package com.backend.service.impl;

import com.backend.dto.request.product.CreateProductRequest;
import com.backend.dto.response.product.ProductResponse;
import com.backend.dto.response.product.SellerProductResponse;
import com.backend.dto.response.product.SkuResponse;
import com.backend.entity.*;
import com.backend.mapper.ProductMapper;
import com.backend.repository.*;
import com.backend.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final SkuRepository skuRepository;
    private final ProductImagesRepository productImagesRepository;
    private final SellerRepository sellerRepository;
    private final ProductMapper productMapper;
    @Value("${uploads}")
    private String PATH;
    private final HashMap<String, String> imageMapping = new HashMap<>();
    @Transactional
    @Override
    public void createProduct(CreateProductRequest request){
        log.info("create product");
        Authentication auth=  SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getEmail();
        log.info("user email: {}", email);
        Seller s = sellerRepository.findSellerByEmail(email);
        List<Sku> skus;
        try {
            skus = objectMapper.readValue(request.skus(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setDescription(request.description());
        product.setOwner(s);
        Category category = new Category();
        category.setId(request.categoryId());
        product.setCategory(category);
        Product p = productRepository.save(product);
        List<ProductImages> images = new ArrayList<>();
        for(MultipartFile image:request.images()){
            ProductImages img = getProductImages(image, p);
            img = productImagesRepository.save(img);
            images.add(img);
        }
        product.setImages(images);
        skus.forEach(sku->{
            sku.setProduct(product);
            String key = sku.getImage();
            System.out.println(key);
            sku.setImage(imageMapping.get(sku.getImage()));
            imageMapping.remove(key);
            skuRepository.save(sku);
        });
    }
    @Transactional
    @Override
    public void deleteProduct(Long id){
        Authentication auth=  SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getEmail();
        Product p = productRepository.findProductById(id);
        if(p==null) return;
        if(p.getOwner().getEmail().equals(email)) {
            productRepository.deleteById(id);
        }
    }
    @Transactional
    @Override
    public Resource getImage(Long id){
        ProductImages img = productImagesRepository.findProductImagesById(id);
        if(img==null) throw new RuntimeException("image not found");
        File file = new File(img.getPath());
        return new FileSystemResource(file);
    }
    @Transactional
    @Override
    public List<ProductResponse> buyerGetProducts(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = productRepository.findAll(pageable).getContent();
        return products.stream().map(productMapper::fromProductToProductResponse).toList();
    }
    @Override
    public SkuResponse buyerGetSku(int id) {
        return null;
    }

    @Override
    public List<SellerProductResponse> sellerGetProducts(int page, int size) {
        Authentication auth=  SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) auth.getPrincipal()).getEmail();
        Seller s = sellerRepository.findSellerByEmail(email);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Product> products = productRepository.findAllProductByOwner(s,pageable);
        return products.stream().map(productMapper::fromProductToSellerProductResponse).toList();
    }
    @Override
    public List<ProductResponse> searchProducts(String nameProduct, int page, int size) {
        return List.of();
    }

    private ProductImages getProductImages(MultipartFile image, Product p) {
        String nameFile = p.getId()+"_"+new Date().getTime()+"_"+ image.getOriginalFilename();
        Path path = Paths.get(PATH, nameFile);
        try {
            Files.createDirectories(path.getParent());
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            imageMapping.put(image.getOriginalFilename(),path.toString());
            log.info("save file {} success", path.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ProductImages.builder().path(path.toString()).product(p).build();
    }
}
