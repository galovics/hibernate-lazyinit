package com.arnoldgalovics.blog.repository;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Product {
    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Company company;

    public Product() {
        super();
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company company) {
        this.company = company;
    }

}
