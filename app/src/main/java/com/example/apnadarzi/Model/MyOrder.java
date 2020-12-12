package com.example.apnadarzi.Model;

public class MyOrder {
    private String order_no;
    private String state;
    private String totalAmount;
    private String image;
    public MyOrder()
    {

    }
    public MyOrder(String order_no, String state, String totalAmount, String image) {
        this.order_no = order_no;
        this.state = state;
        this.totalAmount = totalAmount;
        this.image = image;

    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
