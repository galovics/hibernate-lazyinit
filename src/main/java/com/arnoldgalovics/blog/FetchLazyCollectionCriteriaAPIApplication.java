package com.arnoldgalovics.blog;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.arnoldgalovics.blog.config.JpaConfig;
import com.arnoldgalovics.blog.repository.Company;
import com.arnoldgalovics.blog.repository.DataAccess;
import com.arnoldgalovics.blog.repository.DataAccessHelper;

public class FetchLazyCollectionCriteriaAPIApplication {
    private static final Logger logger = LoggerFactory.getLogger(FetchLazyCollectionCriteriaAPIApplication.class);

    public static void main(final String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        DataAccessHelper helper = context.getBean(DataAccessHelper.class);
        DataAccess dataAccess = context.getBean(DataAccess.class);
        UUID companyId = dataAccess.createDefaultCompany();
        helper.doInTransaction(entityManager -> {
            Company company = dataAccess.getCompanyCriteriaFetch(companyId);
            logger.info("Got the Company object. The products collection is loaded yet.");
            int productsCount = company.getProducts().size();
            logger.info("Company has {} products", productsCount);
        });
    }
}
