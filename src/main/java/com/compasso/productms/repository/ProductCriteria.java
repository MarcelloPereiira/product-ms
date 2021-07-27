package com.compasso.productms.repository;

import com.compasso.productms.domain.Product;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCriteria {

    @PersistenceContext
    private EntityManager manager;

    public List<Product> findProductsWithFilters(Double minPrice, Double maxPrice, String keyword) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (minPrice != null && !minPrice.isNaN())
            predicates.add(builder.lessThanOrEqualTo(root.get("price"), minPrice));

        if (maxPrice != null && !maxPrice.isNaN())
            predicates.add(builder.greaterThanOrEqualTo(root.get("price"), maxPrice));

        if (keyword != null && !keyword.isEmpty()){
            predicates.add(
                    builder.or(
                            builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("description")), "%" + keyword.toLowerCase() + "%")
                    )
            );
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        return manager.createQuery(criteria).getResultList();
    }
}
