package org.ciencialabart.journeyplanner.datasource.h2;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class H2JpaTransactionManager extends JpaTransactionManager {
    
    private static final long serialVersionUID = 6328294836727565118L;

    public H2JpaTransactionManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
    }
    
}
