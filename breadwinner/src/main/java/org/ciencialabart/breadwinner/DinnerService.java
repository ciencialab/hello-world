package org.ciencialabart.breadwinner;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DinnerService {

    private static DinnerService instance;

    public static DinnerService createService() {
        if (instance == null) {
            instance = new DinnerService();
        }
        return instance;
    }

    private final ServiceRegistry serviceRegistry;
    private final SessionFactory sessionFactory;

    private DinnerService() {
        Configuration conf = new Configuration();
        conf.configure();
        serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(conf.getProperties())
                .buildServiceRegistry();
        try {
            sessionFactory = conf.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            Logger.getLogger(DinnerService.class.getName()).log(Level.SEVERE, "SessionFactory creation failed: %s", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public synchronized List<Dinner> getDinners() {
        List<Dinner> dinners;
        
        Session session = sessionFactory.openSession();

        dinners = session.createQuery("from Dinner").list();
        
        session.close();
        
        Collections.sort(dinners, (Dinner o1, Dinner o2) -> o2.getDinnerDate()
                .compareTo(o1.getDinnerDate()));
        
        return dinners;
    }
    
    public synchronized List<DinnerComponent> getDinnersComponents(String kind) {
        List<DinnerComponent> dinnersComponents;
        
        Session session = sessionFactory.openSession();

        dinnersComponents = session
            .createQuery("from DinnerComponent dc "
                       + "where dc.kind "
                       + "= '" + kind + "'")
            .list();
        
        session.close();
        
        Collections.sort(dinnersComponents, (DinnerComponent o1, DinnerComponent o2) -> o1.getName()
                .compareTo(o2.getName()));
        
        return dinnersComponents;
    }

    public synchronized void save(Dinner dinner) {
        try {
            dinner = (Dinner) BeanUtils.cloneBean(dinner);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.saveOrUpdate(dinner);
        session.getTransaction().commit();
        
        session.close();
    }

    public synchronized void save(DinnerComponent dinnerComponent) {
        try {
            dinnerComponent = (DinnerComponent) BeanUtils.cloneBean(dinnerComponent);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.saveOrUpdate(dinnerComponent);
        session.getTransaction().commit();
        
        session.close();
    }

    public synchronized void remove(Dinner dinner) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(dinner);
        session.getTransaction().commit();
        
        session.close();
    }

    public synchronized void remove(DinnerComponent dinnerComponent) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(dinnerComponent);
        session.getTransaction().commit();
        
        session.close();
    }

}
