/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot;

import javax.swing.*;  
import java.awt.*;
import java.net.*;  
  
public class Startup extends JWindow implements Runnable { 
    private static final int MAX_NUM = 40;
    
    Thread splashThread; // 进度条更新线程  
    JProgressBar progress; // 进度条  
  
    public Startup() {  
        Container container = getContentPane(); // 得到容器 
        
        //setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); // 设置光标  
        URL url = getClass().getResource("/res/load.jpg"); // 图片的位置  
        if (url != null) {
            container.add(new JLabel(new ImageIcon(url)), BorderLayout.CENTER); // 增加图片  
        }  
        progress = new JProgressBar(1, MAX_NUM); // 实例化进度条  
        progress.setStringPainted(true); // 描绘文字  
        progress.setString("加载程序中,请稍候......"); // 设置显示文字  
        progress.setBackground(Color.white); // 设置背景色  
        container.add(progress, BorderLayout.SOUTH); // 增加进度条到容器上  
        //AWTUtilities.setWindowOpacity(this, 0.8F);
  
        Dimension screen = getToolkit().getScreenSize(); // 得到屏幕尺寸  
        pack(); // 窗口适应组件尺寸  
        setLocation((screen.width - getSize().width) / 2,  
                (screen.height - getSize().height) / 2); // 设置窗口位置  
    }  
    
  
    public void start() {  
        this.toFront(); // 窗口前端显示  
        splashThread = new Thread(this); // 实例化线程  
        splashThread.start(); // 开始运行线程  
    }  
  
    @Override
    public void run() {  
        setVisible(true); // 显示窗口  
        try {  
            for (int i = 0; i < MAX_NUM; i++) {  
                Thread.sleep(MAX_NUM); // 线程休眠  
                progress.setValue(progress.getValue() + 1); // 设置进度条值  
            }  
        } catch (Exception ex) {  
        }  
        dispose(); // 释放窗口  
        showFrame(); // 运行主程序  
    }  
  
    static void showFrame() {  
        new MyTray();
    }  
  
    public static void main(String[] args) {  
        Startup splash = new Startup();
        splash.start(); // 运行启动界面  
    }  
}  