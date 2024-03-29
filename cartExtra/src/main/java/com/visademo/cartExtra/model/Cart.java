package com.visademo.cartExtra.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cart implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(Cart.class);
    private static final long serialVersionUID = 1L;
    private static final String CART_ITEM_DELIM = ",";
    private static List<Contents> contents = new LinkedList<>();
    private String userName;
    private String item;
    private String editCart;

    public Cart(String userName, String item, String editCart){
        this.userName = userName;
        this.item = item;
        this.editCart = editCart;
    }

    public void add(Contents item){
        for(Contents inCart: contents)
            if(inCart.equals(item))
                return;
        contents.add(item);
    }

    public Contents delete(int index){
        if(index < contents.size())
            return contents.remove(index);
        return new Contents();
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public String getEditCart() {
        return editCart;
    }

    public void setEditCart(String editCart) {
        this.editCart = editCart;
    }

    public List<Contents> getContents() {
        return contents;
    }

    public void load(InputStream is)throws IOException{
        InputStreamReader isr = new InputStreamReader(is); //raw input
        BufferedReader br = new BufferedReader(isr); //line by line
        String itemSt;
        Contents item;
        while((itemSt = br.readLine()) != null){
            logger.info(itemSt);
            String cartItemWifDelim = (String) itemSt;
            if (cartItemWifDelim != null){
                String[] contentsArr = cartItemWifDelim.split(CART_ITEM_DELIM);
                item = new Contents();
                item.setPrice(Double.valueOf(Long.parseLong(contentsArr[0])));
                item.setId(contentsArr[1]);
                contents.add(item);
            }
        }
        br.close(); //claiming back from last out, sequential
        isr.close();
    }

    public void setContents(List<Contents> contentsDel) {
    }

    public Double getPrice() {
        return null;
    }

    public void setPrice(Double price) {
    }

    public void setItem(Contents item2) {
    }


}
