/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot;

import gnu.io.CommPortIdentifier;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Enumeration;
import javax.swing.ImageIcon;

/**
 *
 * @author TIGER
 */
public class Main extends javax.swing.JFrame {

    Log log;
    CommUtil comm;
    
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/res/logo.png")).getImage());
        jScrollPane1.setVisible(false);
        setMinimumSize(new java.awt.Dimension(800, 135));
        setSize(new java.awt.Dimension(800, 135));
        
        String _savepath = Config.get(SAVEPATH);
        String _username = Config.get(USERNAME);
        if (_username == null)
            username = user.getText();
        else {
            user.setText(_username);
            username = _username;
        }
        if (_savepath == null)
            savepath = path.getText();
        else {
            path.setText(_savepath);
            savepath = _savepath;
        }
        
        log = new Log() {
            
            public void write(ByteBuffer bb) {
                try {
                    Data data = new Data().decode(bb);
                    if (data == null) {
                        error("Data Object decode failed !");
                        return;
                    }
                    info(data);
                        
                    new File(savepath).mkdirs();
                    File _file = new File(savepath + File.separatorChar + getDate() + ".txt");
                    info("open file "+_file.getAbsolutePath());
                    boolean add_title = false;
                    if (!_file.exists()) add_title = true;
                    
                    OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(_file, true), "GBK");
                    if (add_title) {
                        fw.append("流水号,样品编号,检测项目1,检测值1,判定1,检测项目2,检测值2,判定2,检测项目3,检测值3,判定3,产品批号,时间,操作者\r\n");
                    }
                    fw.append(wrap(data.data_no)).append(',');
                    if (sample_id.getText().length()>0) {
                        fw.append(wrap(sample_id.getText())).append(',');
                    } else {
                        fw.append(wrap(data.sample_id)).append(',');
                    }
                    if (wrap(data.test_name1).length() > 0) {
                        fw.append(wrap(data.test_name1)).append(',')
                        .append(wrap(data.concentration1)).append(',')
                        .append(wrap(data.judge1)).append(',');
                    } else fw.append("*,*,*,");
                    if (wrap(data.test_name2).length() > 0) {
                        fw.append(wrap(data.test_name2)).append(',')
                        .append(wrap(data.concentration2)).append(',')
                        .append(wrap(data.judge2)).append(',');
                    } else fw.append("*,*,*,");
                    if (wrap(data.test_name3).length() > 0) {
                        fw.append(wrap(data.test_name3)).append(',')
                        .append(wrap(data.concentration3)).append(',')
                        .append(wrap(data.judge3)).append(',');
                    } else fw.append("*,*,*,");
                    fw.append(wrap(data.lot_no)).append(',')
                    .append(wrap_date(data.diagnostic_date)+" "+wrap_time(data.diagnostic_time)).append(',')
                    .append(wrap(username))
                    .append("\r\n")
                    .close();
                    info("write successfully. ");
                } catch (Exception ex) {
                    error("write data file, "+ex.getMessage());
                }
            }
            
            public String wrap(Object o) {
                if (o == null) return "";
                return o.toString().trim();
            }
            public String wrap_date(String o) {
                if (o == null) return "";
                return "20"+o.replaceAll("/", "");
            }
            public String wrap_time(String o) {
                if (o == null) return "";
                if (o.length() > 4)
                    return o.substring(0,5);
                else return o;
            }
            
            public void log(Object o) {
                new File(savepath).mkdirs();
                File _file = new File(savepath + File.separatorChar + (System.currentTimeMillis()+"").substring(0, 8) + ".data");
                try {
                    FileWriter fw = new FileWriter(_file, true);
                    fw.append((o != null ? o.toString() : "") + "\r\n");
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void info(Object o) {
                console.append(o != null ? o.toString() : "");
                console.append("\r\n");
                console.setCaretPosition(console.getText().length());
                //log(o);
            }

            @Override
            public void error(Object o) {
                console.append("Error: ");
                console.append(o != null ? o.toString() : "");
                console.append("\r\n");
                console.setCaretPosition(console.getText().length());
            }

            private String getDate() {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
                return sdf.format(new Date());
            }

        };
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (comm != null) {
                    comm.closePort();
                }
                System.exit(0);
            }
        });
        
        String _serialport = Config.get(SERIALPORT);
        Enumeration portList = CommPortIdentifier.getPortIdentifiers(); //得到当前连接上的端口  
        while (portList.hasMoreElements()) {
            CommPortIdentifier temp = (CommPortIdentifier) portList.nextElement();
            if (temp.getPortType() == CommPortIdentifier.PORT_SERIAL) {// 判断如果端口类型是串口  
                serialport.addItem(temp.getName());
            }
        }
        if (serialport.getItemCount() < 1) {
            log.error("No available resources.");
            control.setEnabled(false);
        } else 
            serialport.setSelectedItem(_serialport);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        serialport = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        baudrate = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        databit = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        stopbit = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        parity = new javax.swing.JComboBox();
        control = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        sample_id = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        enablelog = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        path = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        user = new javax.swing.JTextField();
        set = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 500));
        setMinimumSize(new java.awt.Dimension(800, 500));
        setPreferredSize(new java.awt.Dimension(800, 500));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setText(" SerialPort: ");
        jPanel1.add(jLabel1);

        serialport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serialportActionPerformed(evt);
            }
        });
        jPanel1.add(serialport);

        jLabel2.setText(" BaudRate: ");
        jPanel1.add(jLabel2);

        baudrate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "9600", "19200" }));
        baudrate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baudrateActionPerformed(evt);
            }
        });
        jPanel1.add(baudrate);

        jLabel4.setText(" DataBit: ");
        jPanel1.add(jLabel4);

        databit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "6", "7", "8" }));
        databit.setSelectedIndex(3);
        jPanel1.add(databit);

        jLabel5.setText(" StopBit: ");
        jPanel1.add(jLabel5);

        stopbit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "1.5", "2" }));
        jPanel1.add(stopbit);

        jLabel3.setText(" Parity: ");
        jPanel1.add(jLabel3);

        parity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "None", "Odd", "Even", "Mark", "Space" }));
        jPanel1.add(parity);

        control.setForeground(new java.awt.Color(0, 51, 255));
        control.setText("Connect");
        control.setMaximumSize(new java.awt.Dimension(110, 25));
        control.setMinimumSize(new java.awt.Dimension(110, 25));
        control.setPreferredSize(new java.awt.Dimension(110, 25));
        control.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controlActionPerformed(evt);
            }
        });
        jPanel1.add(control);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel4.setMaximumSize(new java.awt.Dimension(300, 100));
        jPanel4.setName(""); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel10.setText("Sample ID: ");
        jPanel4.add(jLabel10);

        sample_id.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        sample_id.setForeground(new java.awt.Color(255, 0, 0));
        sample_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sample_idActionPerformed(evt);
            }
        });
        jPanel4.add(sample_id);

        jPanel3.add(jPanel4);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(380, 78));

        console.setColumns(20);
        console.setRows(5);
        console.setPreferredSize(new java.awt.Dimension(380, 75));
        jScrollPane1.setViewportView(console);

        jPanel3.add(jScrollPane1);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel7.setText(" ShowLog: ");
        jPanel2.add(jLabel7);

        enablelog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enablelogActionPerformed(evt);
            }
        });
        jPanel2.add(enablelog);

        jLabel6.setText(" SavePath: ");
        jPanel2.add(jLabel6);

        path.setText("d:\\data");
        jPanel2.add(path);

        jLabel8.setText(" Operator: ");
        jPanel2.add(jLabel8);

        user.setText("张三");
        jPanel2.add(user);

        set.setText("Set");
        set.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setActionPerformed(evt);
            }
        });
        jPanel2.add(set);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void controlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_controlActionPerformed
        if (control.getText().equals("Connect")) {
            String _port = serialport.getSelectedItem().toString();
            String _baudrate = baudrate.getSelectedItem().toString();
            String _databit = databit.getSelectedItem().toString();
            String _stopbit = stopbit.getSelectedItem().toString();
            int _parity = parity.getSelectedIndex();
            
            comm = new CommUtil(log, _port, Integer.valueOf(_baudrate), Integer.valueOf(_databit), Integer.valueOf(_stopbit), _parity);
          
//            comm = new CommUtil(log, _port, 19200, 8, 1, _parity);
//            byte[] bs = { 0x02, 0x43, 0x4e, 0x03 };
//            comm.send(bs);

            control.setText("Disconnect");
        } else {
            if (comm != null) comm.closePort();
            control.setText("Connect");
        }
    }//GEN-LAST:event_controlActionPerformed

    private void serialportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serialportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serialportActionPerformed

    private void baudrateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baudrateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_baudrateActionPerformed

    private void setActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setActionPerformed
        String __path = path.getText();
        File _path = new File(__path);
        _path.mkdirs();
        if (_path.isDirectory()) {
            savepath = __path;
            log.info("Path '"+__path+"' set successfully.");
            
            username = user.getText();
            Config.set(USERNAME, username);
            Config.set(SAVEPATH, savepath);
        } else 
            log.error("Path '"+__path+"' Invalid.");
        
        if (serialport.getItemCount() > 0)
            Config.set(SERIALPORT, serialport.getSelectedItem().toString());
    }//GEN-LAST:event_setActionPerformed
    

    private void enablelogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enablelogActionPerformed
        if (enablelog.isSelected()) {
            jScrollPane1.setVisible(true);
            setSize(800, 500);
        } else {
            jScrollPane1.setVisible(false);
            setSize(800, 135);
        }
    }//GEN-LAST:event_enablelogActionPerformed

    private void sample_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sample_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sample_idActionPerformed
    
    String savepath,username;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    private static final String SERIALPORT = "serialport";
    private static final String SAVEPATH = "savepath";
    private static final String USERNAME = "username";
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox baudrate;
    private javax.swing.JTextArea console;
    private javax.swing.JButton control;
    private javax.swing.JComboBox databit;
    private javax.swing.JCheckBox enablelog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox parity;
    private javax.swing.JTextField path;
    private javax.swing.JTextField sample_id;
    private javax.swing.JComboBox serialport;
    private javax.swing.JButton set;
    private javax.swing.JComboBox stopbit;
    private javax.swing.JTextField user;
    // End of variables declaration//GEN-END:variables
}
