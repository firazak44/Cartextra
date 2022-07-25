package com.visademo.cartExtra.repo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.visademo.cartExtra.model.Cart;
import com.visademo.cartExtra.model.Contents;

public class Repo {
    private static final Logger logger = LoggerFactory.getLogger(Repo.class);
    private static List<Contents> contents = new LinkedList<>();
    private File fileRepo;
    private String usernamE;



    public File getFileRepo() {
        return fileRepo;
    }
    public void setFileRepo(File fileRepo) {
        this.fileRepo = fileRepo;
    }
    public String getUsernamE() {
        return usernamE;
    }
    public void setUsernamE(String usernamE) {
        this.usernamE = usernamE;
    }
    
    public void add(Contents item){
        for(Contents inCart: contents)
            if(inCart.equals(item))
                return;
        contents.add(item);
    }

    public synchronized void save(Cart cart){
        String cartName = cart.getUserName() + ".cart";
        String saveLocation = fileRepo.getPath() + File.separator + cartName;
        File saveFile = new File(saveLocation);
        OutputStream os = null;
        try{
            if(!saveFile.exists()){
                Path path= Paths.get(fileRepo.getPath());
                Files.createDirectories(path);
                saveFile.createNewFile();
            }
                
            os = new FileOutputStream(saveLocation);
            this.save(os);
            os.flush();
            os.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void save(OutputStream os) {
    }
    
    public synchronized Cart load(){
        String cartName = this.usernamE + ".cart";
        Cart cart = new Cart(usernamE, cartName, cartName);
        
        for(File cartFile: fileRepo.listFiles())
            if(cartFile.getName().equals(cartName)){
                try{
                    InputStream is = new FileInputStream(cartFile);
                    cart.load(is);
                    is.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
    return cart;
    }

    public List<Contents> getContents(){
        return contents;
        
    }
}
