package com.arnoldgalovics.blog.repository;


import com.arnoldgalovics.blog.config.JpaConfig;
import org.hibernate.LazyInitializationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.hibernate.Hibernate.isInitialized;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
public class DataAccessTest {
    @Autowired
    private DataAccess underTest;

    @Autowired
    private DataAccessHelper helper;

    private UUID companyId;

    @Before
    public void setUp() {
        companyId = underTest.createDefaultCompany();
    }

    @After
    public void tearDown() {
        underTest.deleteEntities();
    }

    @Test(expected = LazyInitializationException.class)
    public void shouldFailWithLazyInitException() {
        Company company = underTest.getCompany(companyId);
        company.getProducts().size();
    }

    @Test
    public void shouldLoadWithinTransaction() {
        helper.doInTransaction(em -> {
            Company company = underTest.getCompany(companyId);
            assertFalse(isInitialized(company.getProducts()));
            int productCount = company.getProducts().size();
            assertEquals(2, productCount);
        });
    }

    @Test
    public void shouldBeLoadedByCriteriaQuery() {
        Company company = underTest.getCompanyCriteriaFetch(companyId);
        assertTrue(isInitialized(company.getProducts()));
        int productCount = company.getProducts().size();
        assertEquals(2, productCount);
    }

    @Test
    public void shouldBeLoadedByJPQLQuery() {
        Company company = underTest.getCompanyJPQLFetch(companyId);
        assertTrue(isInitialized(company.getProducts()));
        int productCount = company.getProducts().size();
        assertEquals(2, productCount);
    }
}