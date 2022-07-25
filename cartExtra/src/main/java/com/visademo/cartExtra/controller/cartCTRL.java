package com.visademo.cartExtra.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.visademo.cartExtra.model.Cart;
import com.visademo.cartExtra.model.Contents;
import com.visademo.cartExtra.repo.Repo;

@Controller
@RequestMapping("/")
public class cartCTRL {
    private static final Logger logger = LoggerFactory.getLogger(cartCTRL.class);

    @Autowired
    ApplicationArguments appArgs;

    @Autowired
    Repo repo;

    @GetMapping("/shopcart")
    public String showCartList(Model model){
        Cart carT = new Cart(null, null, null);
        model.addAttribute("cart",carT);
        return"shopcart";
    }
    
    @GetMapping
    public String showAllCart(Model model, @RequestParam String username) {
        setupRepo(username);
        Cart carT = repo.load();
        model.addAttribute("cart", carT);
        return "shoppingcart";
    }

    
    private void setupRepo(String username) {
        repo.setUsernamE(username);
        repo.setFileRepo(new File(getDataDir(appArgs, "/tmp/data")));
    }


    //Adding the ADD function//
    //int before = currCart.getContents().size();{
        //for(int i=1; i < terms.length; i++)
            //currCart.add(terms[i]);
        //int addedCount = currCart.getContents().size() - before;
       //System.out.printf("Added %d item(s) to %s's cart\n",
            //addedCount, currCart.getUserName());
    //}
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

    
    @PostMapping("/update")
    public String updateCartItem(@ModelAttribute Cart cart, Model model) throws IOException {
        logger.info(" update cart");
        setupRepo(cart.getUserName());
        Cart c = this.repo.load();
        int index = 0;
        for (Contents item : c.getContents()) {
            logger.info(" item.getId() " + item.getId());
            if (item.getId().equals(cart.getEditCart())) {
                item.setPrice(cart.getPrice());
                c.getContents().set(index, item);
            }
            index++;
        }
        repo.save(c);
        model.addAttribute("cart", c);
        return "shoppingcart";
    }

    @GetMapping("/delete/{cartId}")
    public String deleteCartItem(@ModelAttribute Cart cart, Model model,
            @PathVariable String cartId,
            @RequestParam String username) {
        setupRepo(cart.getUserName());
        Cart c = this.repo.load();
        // remove from the data file
        this.repo.delete(cartId, c);
        Cart newCart = repo.load();
        model.addAttribute("cart", newCart);
        return "shoppingcart";
    }

    @GetMapping("/edit/{cartId}")
    public String editCartItem(@ModelAttribute Cart cart, Model model, @PathVariable String cartId, @RequestParam String username) {
        setupRepo(cart.getUserName());
        Cart c = this.repo.load();
        Cart editCart = new Cart(username, username, username);
        for (Contents item : c.getContents()) {
            if (item.getId().equals(cartId)) {
                editCart.setItem(item);
                editCart.setUserName(username);
                editCart.setPrice(item.getPrice());
                editCart.setEditCart(item.getId());
                editCart.setContents(c.getContents());
            }
        }
        model.addAttribute("cart", editCart);
        return "shoppingcart";
    }

    private String getDataDir(ApplicationArguments appArgs, String defaultDataDir) {
        String dataDirResult = "";
        List<String> optValues = null;
        String[] optValuesArr = null;
        Set<String> opsNames = appArgs.getOptionNames();
        String[] optNamesArr = opsNames.toArray(new String[opsNames.size()]);
        if (optNamesArr.length > 0) {
            optValues = appArgs.getOptionValues(optNamesArr[0]);
            optValuesArr = optValues.toArray(new String[optValues.size()]);
            dataDirResult = optValuesArr[0];
        } else {
            dataDirResult = defaultDataDir;
        }

        return dataDirResult;
    }

}

