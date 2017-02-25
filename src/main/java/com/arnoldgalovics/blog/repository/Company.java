package com.arnoldgalovics.blog.repository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company {
    @Id
    private UUID id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.PERSIST)
    private Set<Product> products = new HashSet<>();

    public Company() {
        super();
        this.id = UUID.randomUUID();
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(final Set<Product> products) {
        this.products = products;
    }

    public UUID getId() {
        return id;
    }
}
