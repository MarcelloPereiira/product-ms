package com.compasso.productms.resource;

import com.compasso.productms.resource.errors.ApiException;
import com.compasso.productms.resource.errors.NotFoundException;
import com.compasso.productms.service.ProductService;
import com.compasso.productms.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody ProductDTO productDTO
    ) throws ApiException, URISyntaxException {
        if (productDTO.getId() != null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "ID deve ser nulo");
        }

        ProductDTO result = productService.save(productDTO);
        return ResponseEntity
                .created(new URI("/api/products/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO
    ) throws NotFoundException {
        ProductDTO result = productService.update(id, productDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> result = productService.findAll();

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam(name = "min_price", required = false) Double minPrice,
            @RequestParam(name = "max_price", required = false) Double maxPrice,
            @RequestParam(name = "q", required = false) String keyword
    ) {
        List<ProductDTO> result = productService.search(minPrice, maxPrice, keyword);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) throws NotFoundException {
        ProductDTO result = productService.findOne(id);

        return ResponseEntity
                .ok()
                .body(result);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws NotFoundException {
        productService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
