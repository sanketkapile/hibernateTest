package com.sanket;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
	
	private static SessionFactory sessionFactory;

	private HibernateUtils() {
		
	}
	
	public static SessionFactory getSessionFactory() {
		
		if(sessionFactory == null) {
			sessionFactory = createSessionFactory();
		}
		return sessionFactory;
	}
	
	private static SessionFactory createSessionFactory() {
		StandardServiceRegistry serviceRegistry = null;
		try {
			StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
			serviceRegistryBuilder.loadProperties("hibernate.properties");
			serviceRegistry = serviceRegistryBuilder.build();
			
			MetadataSources metadataSources = new MetadataSources(serviceRegistry);
			metadataSources.addAnnotatedClass(Student.class);
			
			Metadata metaData = metadataSources.getMetadataBuilder().build();
			return metaData.getSessionFactoryBuilder().build();
		}
		catch (Exception e) {
			e.printStackTrace();
			if(serviceRegistry != null) {
				StandardServiceRegistryBuilder.destroy(serviceRegistry);
			}
			
			throw new RuntimeException("\nError in building SessionFactory\n" + e);
		}
	}
}
