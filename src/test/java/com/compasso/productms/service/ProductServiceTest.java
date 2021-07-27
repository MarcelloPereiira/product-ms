package com.compasso.productms.service;

import com.compasso.productms.domain.Product;
import com.compasso.productms.repository.ProductCriteria;
import com.compasso.productms.repository.ProductRepository;
import com.compasso.productms.service.dto.ProductDTO;
import com.compasso.productms.service.mapper.ProductMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @Mock
    private ProductCriteria criteria;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private Product buildProduct() {
        Product entity = new Product();
        entity.setName("Teste");
        entity.setDescription("Teste");
        entity.setPrice(11.00);
        return entity;
    }

    private Product buildProductWithID() {
        Product entity = buildProduct();
        entity.setId(1L);
        return entity;
    }

    private List<Product> buildProductListWithID() {
        List<Product> entities = new ArrayList<>();
        entities.add(buildProductWithID());

        return entities;
    }

    private ProductDTO buildProductDTO() {
        ProductDTO dto = new ProductDTO();
        dto.setName("Teste");
        dto.setDescription("Teste");
        dto.setPrice(11.00);
        return dto;
    }

    private ProductDTO buildProductDTOWithID() {
        ProductDTO dto = buildProductDTO();
        dto.setId(1L);
        return dto;
    }

    private List<ProductDTO> buildProductListDTOWithID() {
        List<ProductDTO> dtos = new ArrayList<>();
        dtos.add(buildProductDTOWithID());

        return dtos;
    }

    @Test
    void save() {
        Mockito.when(repository.save(Mockito.any(Product.class))).thenReturn(buildProductWithID());
        Mockito.when(mapper.toEntity(Mockito.any(ProductDTO.class))).thenReturn(buildProductWithID());
        Mockito.when(mapper.toDto(Mockito.any(Product.class))).thenReturn(buildProductDTOWithID());

        ProductDTO productDTO = productService.save(buildProductDTO());

        Assertions.assertNotNull(productDTO);
        Assertions.assertEquals(1L, productDTO.getId());
        Assertions.assertEquals("Teste", productDTO.getName());
        Assertions.assertEquals("Teste", productDTO.getDescription());
        Assertions.assertEquals(11.00, productDTO.getPrice());
    }

    @Test
    void update() {
        Mockito.when(repository.save(Mockito.any(Product.class))).thenReturn(buildProductWithID());
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(buildProductWithID()));
        Mockito.when(mapper.toEntity(Mockito.any(ProductDTO.class))).thenReturn(buildProductWithID());
        Mockito.when(mapper.toDto(Mockito.any(Product.class))).thenReturn(buildProductDTOWithID());

        ProductDTO productDTO = productService.update(1L, buildProductDTO());

        Assertions.assertNotNull(productDTO);
        Assertions.assertEquals(1L, productDTO.getId());
        Assertions.assertEquals("Teste", productDTO.getName());
        Assertions.assertEquals("Teste", productDTO.getDescription());
        Assertions.assertEquals(11.00, productDTO.getPrice());
    }

    @Test
    void findAll() {
        Mockito.when(repository.findAll()).thenReturn(buildProductListWithID());
        Mockito.when(mapper.toDto(Mockito.any(Product.class))).thenReturn(buildProductDTOWithID());

        List<ProductDTO> productDTOs = productService.findAll();

        Assertions.assertEquals(1, productDTOs.size());
        Assertions.assertIterableEquals(buildProductListDTOWithID(), productDTOs);
    }

    @Test
    void search() {
        Mockito.when(
                criteria.findProductsWithFilters(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString())
        ).thenReturn(buildProductListWithID());
        Mockito.when(mapper.toDto(Mockito.any(Product.class))).thenReturn(buildProductDTOWithID());

        List<ProductDTO> productDTOs = productService.search(1.0, 1.0, "test");

        Assertions.assertEquals(1, productDTOs.size());
        Assertions.assertIterableEquals(buildProductListDTOWithID(), productDTOs);
    }

    @Test
    void findOne() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(buildProductWithID()));
        Mockito.when(mapper.toDto(Mockito.any(Product.class))).thenReturn(buildProductDTOWithID());

        ProductDTO productDTO = productService.findOne(Mockito.anyLong());

        Assertions.assertNotNull(productDTO);
        Assertions.assertEquals(1L, productDTO.getId());
        Assertions.assertEquals("Teste", productDTO.getName());
        Assertions.assertEquals("Teste", productDTO.getDescription());
        Assertions.assertEquals(11.00, productDTO.getPrice());
    }

    @Test
    @SneakyThrows
    void delete() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(buildProductWithID()));

        productService.delete(Mockito.anyLong());
    }
}