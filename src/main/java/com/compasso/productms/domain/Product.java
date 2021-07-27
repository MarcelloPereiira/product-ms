package com.compasso.productms.domain;

import com.sun.istack.NotNull;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    @NotEmpty
    public String name;

    @Lob
    @Column(name = "description", nullable = false)
    @NotNull
    @NotEmpty
    public String description;

    @Column(name = "price", nullable = false)
    @NotNull
    public Double price;

}
