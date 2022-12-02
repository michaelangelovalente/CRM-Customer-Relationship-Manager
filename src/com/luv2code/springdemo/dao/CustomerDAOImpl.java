package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.util.SortUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers(int theSortField) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//determine the sort field
		String theFieldName = null;
		switch(theSortField) {
			case SortUtils.FIRST_NAME:
					theFieldName ="firstName";
					break;
			case SortUtils.LAST_NAME:
				theFieldName ="lastName";
				break;
			case SortUtils.EMAIL:
				theFieldName ="email";
				break;
			default:
				theFieldName = "lastName";
				
		
		}
		
		
		String queryString = "FROM Customer ORDER BY "+ theFieldName;
		
		// create a query and sort by chosen field
		Query<Customer> theQuery = 
				currentSession.createQuery(queryString, 
						Customer.class);
		
		// execute query and get result list
		List<Customer> customers =  theQuery.getResultList();
		
		// return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// save the customer
//		currentSession.save(theCustomer);
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieve/read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//delete object with primary key
		Query theQuery = currentSession.createQuery("DELETE FROM Customer where id=:customerId", Customer.class);
		theQuery.setParameter("customerId", theId); // sets theId as a the query's parameter
		
		//execute the query
		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		//get the hibernateSession
		System.out.println("inside CustomerDAO searchCustomers");
		Session currentSession = sessionFactory.getCurrentSession();
		System.out.println("inside CustomerDAO searchCustomers part2");
		Query theQuery = null;
		// only search by name if theSearchName is not null
		if( theSearchName != null ) {
			// search for firstName or LastName
			theQuery = currentSession.createQuery("FROM Customer WHERE LOWER(firstName) LIKE :theName or LOWER(lastName) like :theName", Customer.class);
			theQuery.setParameter("theName", "%"+theSearchName.toLowerCase()+"%");
			
		}else {
			//search is empty --> get all customers
			theQuery = currentSession.createQuery("FROM Customer", Customer.class);
			
		}
		
		List<Customer> customers = theQuery.getResultList();
		System.out.println("Inside searchCustomers: " + customers);
				
		return customers;
	}
	

}
