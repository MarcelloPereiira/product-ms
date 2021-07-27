package com.compasso.productms.service.mapper;

import com.compasso.productms.domain.Product;
import com.compasso.productms.service.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductMapper extends EntityMapper<ProductDTO, Product>{
    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO product);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }

}
