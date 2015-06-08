package com.tr.cay.dagdem.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EXT0175855 on 5/18/2015.
 */
public class Customer implements Serializable {

    private String customerId;
    private String  name;
    private String  address;
    private boolean checked = false;

    public Customer(String customerId,String name) {
        super();
        this.customerId = customerId;
        this.name = name;
    }

    public Customer(String customerId,String name,String address)
    {
        super();
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }


    @Override
    public String toString() {
        return name + address;
    }

    public static Customer findSelectedCustomer(List<Customer> customerList)
    {
      for(Customer customer:customerList)
      {
          if(customer.isChecked())
              return customer;
      }
        return null;
    }
}
