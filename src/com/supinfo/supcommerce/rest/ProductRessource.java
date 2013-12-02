package com.supinfo.supcommerce.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.supinfo.supcommerce.dao.DaoFactory;
import com.supinfo.supcommerce.dao.ProductDao;
import com.supinfo.supcommerce.entity.Product;

/**
 * Servlet implementation class ProductRessource
 * <p>
 * Web Service REST to expose the Product class
 * </p>
 * 
 * @author Elka
 * @version 1.0
 * @since SupCommerce 5.1
 */
@Path("/products")
public class ProductRessource {
	
	private final ProductDao	productDao;
	
	/**
	 * Constructor
	 */
	public ProductRessource() {
		this.productDao = DaoFactory.getProductDao();
	}
	
	/**
	 * @return hello
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHelloWorld() {
		return "Hello World, i'm a web service REST !";
	}
	
	/**
	 * @return xmlProducts
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String getAllProductsInXml() {
		
		/*
		 * Format: <products> <product> <id>12</id> <name>Product1</name> <description>Content1</description>
		 * <price>123.00</price> </product> ... </products>
		 */
		
		final List<Product> allProducts = this.productDao.getAllProducts();
		String xmlProducts = "";
		
		xmlProducts += "<products>";
		
		for (Product product : allProducts) {
			xmlProducts += "<product>";
			xmlProducts += "<id>" + product.getId() + "</id>";
			xmlProducts += "<name>" + product.getName() + "</name>";
			xmlProducts += "<description>" + product.getContent() + "</description>";
			xmlProducts += "<price>" + product.getPrice() + "</price>";
			xmlProducts += "</product>";
		}
		
		xmlProducts += "</products>";
		
		return xmlProducts;
	}
	
	/**
	 * @return jsonProducts
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllProductsInJson() {
		/*
		 * Format: {"products":[ { "id":"12", "name":"Product1", "description":"Content1", "price":"123.00" }, {...} ]}
		 */
		
		final List<Product> allProducts = this.productDao.getAllProducts();
		String jsonProducts = "";
		
		jsonProducts += "{\"products\":[";
		
		for (Product product : allProducts) {
			jsonProducts += "{";
			jsonProducts += "\"id\": \"" + product.getId() + "\",";
			jsonProducts += "\"name\": \"" + product.getName() + "\",";
			jsonProducts += "\"description\": \"" + product.getContent() + "\",";
			jsonProducts += "\"price\": \"" + product.getPrice() + "\"";
			jsonProducts += "}, ";
		}
		
		// Delete coma too at the end of the loop
		jsonProducts = jsonProducts.substring(0, jsonProducts.length() - 2);
		
		jsonProducts += "]}";
		
		return jsonProducts;
	}
	
	/**
	 * @param productId
	 * @return xmlProduct
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{productId}")
	public String getProductInXml(@PathParam("productId") Long productId) {
		final Product requestedProduct = this.productDao.findProductById(productId);
		String xmlProduct = "";
		
		xmlProduct += "<product>";
		xmlProduct += "<id>" + requestedProduct.getId() + "</id>";
		xmlProduct += "<name>" + requestedProduct.getName() + "</name>";
		xmlProduct += "<description>" + requestedProduct.getContent() + "</description>";
		xmlProduct += "<price>" + requestedProduct.getPrice() + "</price>";
		xmlProduct += "</product>";
		
		return xmlProduct;
	}
	
	/**
	 * @param productId
	 * @return jsonProduct
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{productId}")
	public String getProductInJson(@PathParam("productId") Long productId) {
		final Product requestedProduct = this.productDao.findProductById(productId);
		String jsonProduct = "";
		
		jsonProduct += "{";
		jsonProduct += "\"id\": \"" + requestedProduct.getId() + "\",";
		jsonProduct += "\"name\": \"" + requestedProduct.getName() + "\",";
		jsonProduct += "\"description\": \"" + requestedProduct.getContent() + "\",";
		jsonProduct += "\"price\": \"" + requestedProduct.getPrice() + "\"";
		jsonProduct += "}";
		
		return jsonProduct;
	}
	
	/**
	 * @param productId
	 */
	@DELETE
	@Path("/{productId}")
	public void removeProduct(@PathParam("productId") Long productId) {
		this.productDao.removeProduct(productId);
	}
	
}
