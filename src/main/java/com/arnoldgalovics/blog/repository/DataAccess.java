package com.arnoldgalovics.blog.repository;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

public class DataAccess {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UUID createDefaultCompany() {
        Company company = new Company();
        Product notebook = new Product();
        notebook.setName("Notebook");
        notebook.setCompany(company);
        Product phone = new Product();
        phone.setName("phone");
        phone.setCompany(company);
        company.setProducts(Sets.newHashSet(notebook, phone));
        entityManager.persist(company);
        return company.getId();
    }

    @Transactional
    public Company getCompany(final UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = cb.createQuery(Company.class);
        Root<Company> root = query.from(Company.class);
        query.select(root);
        query.where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Transactional
    public Company getCompanyCriteriaFetch(final UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = cb.createQuery(Company.class);
        Root<Company> root = query.from(Company.class);
        query.select(root);
        root.fetch("products");
        query.where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Transactional
    public Company getCompanyJPQLFetch(final UUID id) {
        return entityManager.createQuery("FROM Company c JOIN FETCH c.products", Company.class).getSingleResult();
    }
}
