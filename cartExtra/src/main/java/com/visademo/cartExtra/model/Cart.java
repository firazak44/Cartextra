package com.visademo.cartExtra.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;

public class Cart implements Serializable{
    private static final long serialVersionUID = 1L;
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

    public static List<Contents> getContents() {
        return contents;
    }

    public void load(InputStream is)throws IOException{
        InputStreamReader isr = new InputStreamReader(is); //raw input
        BufferedReader br = new BufferedReader(isr); //line by line
        String itemSt;
        Contents item;
        while((item = br.readLine()) != null){
            Logger.info(itemSt);
            contents.add(item);
        }
        br.close(); //claiming back from last out, sequential
        isr.close();
    }


}
