/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TIGER
 */
public class Config {
    public static void set(String user, String o) {
        if (o == null) return;
        try {
            new FileOutputStream(System.getProperty("user.home")+"\\.config").write((user+","+o).getBytes("GBK"));
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String get(String key) {
        try {
            byte[] bs = new byte[1024];
            File file = new File(System.getProperty("user.home")+"\\.config");
            if (!file.exists()) return null;
            new FileInputStream(file).read(bs);
            String str[] = new String(bs, "GBK").split(",");
            if ("user".equals(key)) return str[0].trim();
            if ("savepath".equals(key)) return str[1].trim();
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
