package com.backend.controller;

import com.backend.dto.Response;
import com.backend.dto.request.product.CreateProductRequest;
import com.backend.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<?>> createProduct(@ModelAttribute CreateProductRequest req){
        productService.createProduct(req);
        return ResponseEntity.ok().body(new Response<>(1000, "success", null));
    }
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<?>> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().body(new Response<>(1000, "success", null));
    }
    @GetMapping(value = "/image/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getImage(@PathVariable Long id){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(productService.getImage(id));
    }
}
