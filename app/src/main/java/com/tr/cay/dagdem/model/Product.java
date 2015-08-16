package com.tr.cay.dagdem.model;

import com.tr.cay.dagdem.enums.ProductType;

import java.io.Serializable;

/**
 * Created by EXT0175855 on 6/8/2015.
 */
public class Product implements Serializable {

    private String id;
    private String productName;
    private double price;
    private int quantity;
    private ProductType productType;
    private double salePrice;
    private boolean checked = false;

    public Product(){

    }

    public Product(String id,String productName,double d,int quantity)
    {
        this.id = id;
        this.productName = productName;
        this.price = d;
        this.quantity = quantity;
    }


    public Product(String id,String productName,double d,int quantity,ProductType productType,double salePrice)
    {
        this.id = id;
        this.productName = productName;
        this.price = d;
        this.quantity = quantity;
        this.productType = productType;
        this.salePrice = salePrice;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public boolean isChecked()
    {
        return checked;
    }
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice)
    {
        this.salePrice = salePrice;
    }

	@Override
    public String toString()
    {
        return id+","+productName+","+price+","+quantity;
    }


}
