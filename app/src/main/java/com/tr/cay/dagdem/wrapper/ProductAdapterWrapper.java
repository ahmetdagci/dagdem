package com.tr.cay.dagdem.wrapper;

import com.tr.cay.dagdem.model.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EXT0175855 on 8/16/2015.
 */
public class ProductAdapterWrapper implements Serializable {

    private List<Product> productList;

    public ProductAdapterWrapper(List<Product> productList)
    {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
