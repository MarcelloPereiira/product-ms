package com.compasso.productms.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price = 0.00;
}
