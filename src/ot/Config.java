/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TIGER
 */
public class Config {
    final static String file_name = System.getProperty("user.home")+File.separator+".otconfig";
    static Properties prop = new Properties();
    
    public static void set(String key, String value) {
        if (key != null)
            prop.setProperty(key, value);
        try {
            try (FileOutputStream fos = new FileOutputStream(file_name)) {
                prop.store(fos, "");
                fos.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String get(String key) {
        try {
            if (!new File(file_name).exists()) {
                new FileOutputStream(file_name).write(new byte[]{});
            }
            FileInputStream fis = new FileInputStream(file_name);
            prop.load(fis);
            return prop.getProperty(key);
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
