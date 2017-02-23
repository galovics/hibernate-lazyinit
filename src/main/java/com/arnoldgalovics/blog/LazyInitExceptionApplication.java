package com.arnoldgalovics.blog;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.arnoldgalovics.blog.config.JpaConfig;
import com.arnoldgalovics.blog.repository.Company;
import com.arnoldgalovics.blog.repository.DataAccess;

public class LazyInitExceptionApplication {
    private static final Logger logger = LoggerFactory.getLogger(LazyInitExceptionApplication.class);

    public static void main(final String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        DataAccess dataAccess = context.getBean(DataAccess.class);
        UUID companyId = dataAccess.createDefaultCompany();
        Company company = dataAccess.getCompany(companyId);
        int productsCount = company.getProducts().size();
        logger.info("Company has {} products", productsCount);
    }
}
