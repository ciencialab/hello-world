package org.ciencialabart.journeyplanner.datasource.h2;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

@Component
public class H2LocalContainerEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {
    
    @Autowired
    public H2LocalContainerEntityManagerFactoryBean(DataSource dataSource) {
        setPersistenceProviderClass(HibernatePersistenceProvider.class);
        setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        setDataSource(dataSource);
        setPackagesToScan("org.ciencialabart.journeyplanner");
        
        Properties jpaProperties = new Properties();
        jpaProperties.put(org.hibernate.cfg.AvailableSettings.HBM2DDL_DATABASE_ACTION, "drop-and-create");
        jpaProperties.put(org.hibernate.cfg.AvailableSettings.DIALECT, "org.hibernate.dialect.HSQLDialect");
        jpaProperties.put(AvailableSettings.ENHANCER_ENABLE_ASSOCIATION_MANAGEMENT, "false");
        jpaProperties.put(AvailableSettings.ENHANCER_ENABLE_DIRTY_TRACKING, "false");
        jpaProperties.put(AvailableSettings.ENHANCER_ENABLE_LAZY_INITIALIZATION, "false");
        setJpaProperties(jpaProperties);
    }
    
}
