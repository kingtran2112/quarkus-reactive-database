package com.example;


import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ProductRepository implements PanacheRepositoryBase<ProductModel, UUID> {
}
