package com.visademo.cartExtra.repo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.visademo.cartExtra.model.Cart;
import com.visademo.cartExtra.model.Contents;

@Component(value = "cartRepo")
public class Repo {
    private static final Logger logger = LoggerFactory.getLogger(Repo.class);
    private static List<Contents> contents = new LinkedList<>();
    private File fileRepo;
    private String usernamE;


    public Repo() {

    }

    public Repo(String name, String dataDir) {
        this.usernamE = name;
        this.fileRepo = new File(dataDir);
    }

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

    public synchronized void save(Cart cart, boolean isDel){
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

            if (isDel) {
                os = new FileOutputStream(saveLocation, true);
            } else {
                os = new FileOutputStream(saveLocation, false);
            }

            this.save(os, cart.getContents());
            os.flush();
            os.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void save(OutputStream os, List<Contents> items) throws IOException {
        logger.info("save to file");
        OutputStreamWriter ows = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(ows);
        for (Contents item : items) {
            logger.info("" + item.getPrice());
            bw.write(item.getPrice() + "," + item.getId() + "\n");
        }
        ows.flush();
        bw.flush();
        bw.close();
        ows.close();
    }

    public synchronized void save(Cart cart) {
        this.save(cart, false);
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

    public void delete(String cartId, Cart c) {
        int index = 0;
        logger.info(" cartid " + cartId);
        logger.info(" contents " + c.getContents().size());
        List<Contents> contentsDel = c.getContents();
        for (Contents item : contentsDel) {
            logger.info(" item.getId() " + item.getId());
            if (item.getId().equals(cartId)) {
                logger.info("Delete cartid " + cartId);
                contentsDel.remove(index);
                break;
            }
            index++;
        }
        c.setContents(contentsDel);
        this.save(c, false);
    }
}
