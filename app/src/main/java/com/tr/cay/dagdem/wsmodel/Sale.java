package com.tr.cay.dagdem.wsmodel;

import java.io.Serializable;
import java.util.List;

public class Sale implements Serializable{
	
	private String userId;
	private String customerId;
	private List<Product> productList;

    public Sale()
    {

    }

    public Sale(String userId,String customerId,List<Product> products)
    {
        this.userId = userId;
        this.customerId = customerId;
        this.productList = products;
    }

    public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public List<Product> getProductList() {
		return productList;
	}
	
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
