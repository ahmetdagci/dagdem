package com.tr.cay.dagdem.enums;

/**
 * Created by EXT0175855 on 8/16/2015.
 */
public enum ProductType {

    CAY(1),
    ORALET(2),
    SU(3),
    DIGER(4);

    private int productCode;

    private ProductType(int productCode)
    {
        this.productCode = productCode;
    }

    public int getProductCode()
    {
        return productCode;
    }
}
