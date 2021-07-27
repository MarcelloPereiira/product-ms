package com.compasso.productms.service;

import com.compasso.productms.resource.errors.ApiException;
import com.compasso.productms.service.dto.ProductDTO;

import java.util.List;

public interface IProductService {

    ProductDTO save(ProductDTO productDTO);

    ProductDTO update(Long id, ProductDTO productDTO);

    List<ProductDTO> search(Double minPrice, Double maxPrice, String keyword);

    List<ProductDTO> findAll();

    ProductDTO findOne(Long id) throws ApiException;

    void delete(Long id);

}
