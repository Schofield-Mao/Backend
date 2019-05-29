package com.server.server;

import org.junit.Test;

import java.io.*;

public class FIleTest {
    @Test
    public void fileTest(){
        try{
            File imagePath = new File("/home/mjh/images");
            String fileName = "test.jpg";
            if(!imagePath.exists()){
                imagePath.mkdir();
            }
            File imageFile = new File(imagePath,fileName);
            if(!imageFile.exists()){
                imageFile.createNewFile();
            }
            OutputStream os = new FileOutputStream(imageFile);
            BufferedOutputStream bs = new BufferedOutputStream(os);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
