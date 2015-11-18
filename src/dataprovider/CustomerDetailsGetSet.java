/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataprovider;

/**
 *
 * @author partha
 */
public class CustomerDetailsGetSet {
    private String cust_name;
    private String cust_address;
    private String cust_phone;
    private String cust_point;

    public CustomerDetailsGetSet() {
    }
    
    public CustomerDetailsGetSet(String cust_name, String cust_address, String cust_phone, String cust_point) {
        this.cust_name = cust_name;
        this.cust_address = cust_address;
        this.cust_phone = cust_phone;
        this.cust_point = cust_point;
    }
    
    
    
    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_address() {
        return cust_address;
    }

    public void setCust_address(String cust_address) {
        this.cust_address = cust_address;
    }

    public String getCust_phone() {
        return cust_phone;
    }

    public void setCust_phone(String cust_phone) {
        this.cust_phone = cust_phone;
    }

    public String getCust_point() {
        return cust_point;
    }

    public void setCust_point(String cust_point) {
        this.cust_point = cust_point;
    }
    
    
}
