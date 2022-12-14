package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import com.luv2code.springdemo.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject our customer service
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomer(Model theModel,/*, Model theModel2*/
			@RequestParam(required=false) String sort) {
		
		//get customers from the service
//		List<Customer> theCustomers = customerService.getCustomers();
		List<Customer> theCustomers = null;
		System.out.println("sort -> " + sort );
		//check for sort field
		if( sort != null) {
			int theSortField = Integer.parseInt(sort);
			theCustomers = customerService.getCustomers(theSortField);
			System.out.println("Customers: " + theCustomers);
		}else {
			// no sort field provided. Default to sorting by last name
			theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
		}
//		Customer customer = new Customer();
		
		// add the customers to the model
		theModel.addAttribute("customers",theCustomers);
		
//		theModel2.addAttribute("customer", customer );
		
		return "list-customers";
	}
	
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		// save the customer using our service
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId,
									Model theModel) {
		// get the customer (from the database) via our service
		Customer theCustomer = customerService.getCustomer(theId); // customer retrieved from DB
		
		// add/set the customer as a model attribute to pre-populate the form
		// with the data from DB
		theModel.addAttribute("customer", theCustomer);
		
		// send over to our form
		
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		// delete the customer
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
			Model theModel) {
		
		// search customers from the service
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
		
		theModel.addAttribute("customers", theCustomers);
		System.out.println(theCustomers);
		
		return "list-customers";
	}
	
	
}
