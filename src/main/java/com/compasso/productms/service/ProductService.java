package com.compasso.productms.service;

import com.compasso.productms.domain.Product;
import com.compasso.productms.repository.ProductCriteria;
import com.compasso.productms.repository.ProductRepository;
import com.compasso.productms.resource.errors.NotFoundException;
import com.compasso.productms.service.dto.ProductDTO;
import com.compasso.productms.service.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductCriteria criteria;

    @Transactional(rollbackOn = Exception.class)
    public ProductDTO save(ProductDTO productDTO) {
        Product product = productRepository.save(productMapper.toEntity(productDTO));

        return productMapper.toDto(product);
    }

    @Transactional(rollbackOn = Exception.class)
    public ProductDTO update(Long id, ProductDTO productDTO) throws NotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);

        productOptional
                .orElseThrow(() -> new NotFoundException("Product not found"));

        productDTO.setId(id);
        return save(productDTO);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> search(Double minPrice, Double maxPrice, String keyword) {
        return criteria.findProductsWithFilters(minPrice, maxPrice, keyword)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO findOne(Long id) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);

        return productMapper.toDto(
                product.orElseThrow(() -> new NotFoundException("Product not found"))
        );
    }

    public void delete(Long id) throws NotFoundException  {
        Optional<Product> productOptional = productRepository.findById(id);

        Product product = productOptional
                .orElseThrow(() -> new NotFoundException("Product not found"));

        productRepository.delete(product);
    }

}
