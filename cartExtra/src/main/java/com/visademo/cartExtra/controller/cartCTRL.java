package com.visademo.cartExtra.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.visademo.cartExtra.model.Cart;

@Controller
public class cartCTRL {

    @GetMapping("/shopcart")
    public String showCartList(Model model){
        Cart carT = new Cart(null, null, null);
        model.addAttribute("cart",carT);
        return"shopcart";
    }

    //Adding the ADD function//
    int before = currCart.getContents().size();{
        for(int i=1; i < terms.length; i++)
            currCart.add(terms[i]);
        int addedCount = currCart.getContents().size() - before;
        System.out.printf("Added %d item(s) to %s's cart\n",
            addedCount, currCart.getUserName());
    }


    //Input a counter to display list of items//
    //public void printList(List<String> list){
        //if(list.size() <= 0){
            //System.out.println("No record found!");
            //return;
        //}
        //for(int i=0; i < list.size(); i++){
            //System.out.printf("%d. %s\n", (i+1), list.get(i));
        //}
    //}

    //reroute submit button to shopcart page//
    //@PostMapping("/shopcartsaved")
    //public String saveCartList(@ModelAttribute Cart cart,Model model){
        //model.addAttribute("cart", cart.findAll());
        //return "shopcart";
    //}

}

