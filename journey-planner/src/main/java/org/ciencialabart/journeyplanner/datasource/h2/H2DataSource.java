package org.ciencialabart.journeyplanner.datasource.h2;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;

@Component
public class H2DataSource extends BasicDataSource {
    
    public static final String JDBC_DRIVER_CLASS_NAME = "org.h2.Driver";
    public static final String JDBC_URL = "jdbc:h2:mem:;MODE=PostgreSQL";
    public static final String JDBC_USERNAME = "sa";
    public static final String JDBC_PASSWORD = "sa";
    
    public H2DataSource() {
        setDriverClassName(JDBC_DRIVER_CLASS_NAME);
        setUrl(JDBC_URL);
        setUsername(JDBC_USERNAME);
        setPassword(JDBC_PASSWORD);
    }
    
}
