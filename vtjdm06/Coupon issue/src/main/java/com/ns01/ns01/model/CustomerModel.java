package com.ns01.ns01.model;

import javax.persistence.Entity;


public class CustomerModel {

    
    private String invoice_no;
    private String customer_id;
    private String gender;
    private String age;
    private String category;
    private String quantity;
    private double price;
    private String payment_method;
    private String invoice_date;
    private String shopping_mall;
   
    public String getInvoice_no() {
        return invoice_no;
    }
    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }
    public String getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getPayment_method() {
        return payment_method;
    }
    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
    public String getInvoice_date() {
        return invoice_date;
    }
    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }
    public String getShopping_mall() {
        return shopping_mall;
    }
    public void setShopping_mall(String shopping_mall) {
        this.shopping_mall = shopping_mall;
    }
	@Override
	public String toString() {
		return "CustomerModel [invoice_no=" + invoice_no + ", customer_id=" + customer_id + ", gender=" + gender
				+ ", age=" + age + ", category=" + category + ", quantity=" + quantity + ", price=" + price
				+ ", payment_method=" + payment_method + ", invoice_date=" + invoice_date + ", shopping_mall="
				+ shopping_mall + "]";
	}

    













    
}
