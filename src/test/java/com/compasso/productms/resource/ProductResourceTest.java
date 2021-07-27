package com.compasso.productms.resource;

import com.compasso.productms.service.ProductService;
import com.compasso.productms.service.dto.ProductDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = ProductResourceTest.class)
class ProductResourceTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductResource productResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
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
    @SneakyThrows
    void createProduct() {
        Mockito.when(productService.save(Mockito.any(ProductDTO.class))).thenReturn(buildProductDTOWithID());

        ProductDTO productDTO = productResource.createProduct(buildProductDTO()).getBody();

        Assertions.assertNotNull(productDTO);
        Assertions.assertEquals(1L, productDTO.getId());
        Assertions.assertEquals("Teste", productDTO.getName());
        Assertions.assertEquals("Teste", productDTO.getDescription());
        Assertions.assertEquals(11.00, productDTO.getPrice());
    }

    @Test
    void updateProduct() {
        Mockito.when(productService.update(Mockito.anyLong(), Mockito.any(ProductDTO.class))).thenReturn(buildProductDTOWithID());

        ProductDTO productDTO = productResource.updateProduct(1L, buildProductDTO()).getBody();

        Assertions.assertNotNull(productDTO);
        Assertions.assertEquals(1L, productDTO.getId());
        Assertions.assertEquals("Teste", productDTO.getName());
        Assertions.assertEquals("Teste", productDTO.getDescription());
        Assertions.assertEquals(11.00, productDTO.getPrice());
    }

    @Test
    void getProducts() {
        Mockito.when(productService.findAll()).thenReturn(buildProductListDTOWithID());

        List<ProductDTO> productDTOs = productResource.getProducts().getBody();

        Assertions.assertEquals(1, productDTOs.size());
        Assertions.assertIterableEquals(buildProductListDTOWithID(), productDTOs);
    }

    @Test
    void searchProducts() {
        Mockito.when(productService.search(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString())).thenReturn(buildProductListDTOWithID());

        List<ProductDTO> productDTOs = productResource.searchProducts(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString()).getBody();

        Assertions.assertEquals(1, productDTOs.size());
        Assertions.assertIterableEquals(buildProductListDTOWithID(), productDTOs);
    }

    @Test
    void getProduct() {
        Mockito.when(productService.findOne(Mockito.anyLong())).thenReturn(buildProductDTOWithID());

        ProductDTO productDTO = productResource.getProduct(Mockito.anyLong()).getBody();

        Assertions.assertNotNull(productDTO);
        Assertions.assertEquals(1L, productDTO.getId());
        Assertions.assertEquals("Teste", productDTO.getName());
        Assertions.assertEquals("Teste", productDTO.getDescription());
        Assertions.assertEquals(11.00, productDTO.getPrice());
    }

}