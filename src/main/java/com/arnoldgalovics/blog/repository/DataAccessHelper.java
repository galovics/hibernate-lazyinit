package com.arnoldgalovics.blog.repository;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

public class DataAccessHelper {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void doInTransaction(final Consumer<EntityManager> c) {
        c.accept(entityManager);
    }
}
