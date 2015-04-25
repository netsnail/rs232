package ot;

import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.Enumeration;  
import java.util.TooManyListenersException;  
  
import gnu.io.CommPortIdentifier;  
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;  
import gnu.io.SerialPort;  
import gnu.io.SerialPortEvent;  
import gnu.io.SerialPortEventListener;  
import gnu.io.UnsupportedCommOperationException;  
import java.nio.ByteBuffer;
  
public class CommUtil implements SerialPortEventListener {  
  
    InputStream inputStream; // 从串口来的输入流  
    OutputStream outputStream;// 向串口输出的流  
    SerialPort serialPort; // 串口的引用  
    CommPortIdentifier portId;  
    
    
    public CommUtil(Log log, String portname, int baudrate, int databit, int stopbit, int parity) {  
        this.log = log;
        try {
            portId = CommPortIdentifier.getPortIdentifier(portname);
        } catch (NoSuchPortException e) {
            log.error(e.getMessage()); return;
        }
        
         try {  
            serialPort = (SerialPort) portId.open("My "+portname, 2000);  
        } catch (PortInUseException e) {  
            log.error(e.getMessage()); return;
        }  
         
        try {  
            inputStream = serialPort.getInputStream();  
            outputStream = serialPort.getOutputStream();  
        } catch (IOException e) {  
            log.error(e.getMessage()); return;
        }  
        try {  
            serialPort.addEventListener(this); // 给当前串口天加一个监听器  
        } catch (TooManyListenersException e) {  
            log.error(e.getMessage()); return;
        }  
        serialPort.notifyOnDataAvailable(true); // 当有数据时通知  
        try {  
            serialPort.setSerialPortParams(baudrate, databit, // 设置串口读写参数  
                    stopbit, parity);  
        } catch (UnsupportedCommOperationException e) {  
            log.error(e.getMessage()); return;
        }  
        log.info(portname+" connected.");
    }
  
    public CommUtil(Enumeration portList, String name) {  
        while (portList.hasMoreElements()) {  
            CommPortIdentifier temp = (CommPortIdentifier) portList.nextElement();  
            if (temp.getPortType() == CommPortIdentifier.PORT_SERIAL) {// 判断如果端口类型是串口  
                if (temp.getName().equals(name)) { // 判断如果端口已经启动就连接  
                    portId = temp;  
                }  
            }  
        }  
        
        try {  
            serialPort = (SerialPort) portId.open("My "+name, 2000);  
        } catch (PortInUseException e) {  
            log.error(e.getMessage()); return;
        }  
        try {  
            inputStream = serialPort.getInputStream();  
            outputStream = serialPort.getOutputStream();  
        } catch (IOException e) {  
            log.error(e.getMessage()); return;
        }  
        try {  
            serialPort.addEventListener(this); // 给当前串口天加一个监听器  
        } catch (TooManyListenersException e) {  
            log.error(e.getMessage()); return;
        }  
        serialPort.notifyOnDataAvailable(true); // 当有数据时通知  
        try {  
            serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8, // 设置串口读写参数  
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);  
        } catch (UnsupportedCommOperationException e) {  
            log.error(e.getMessage()); return;
        }  
        log.info("connected...");
    }  
    
    ByteBuffer bb = ByteBuffer.allocateDirect(Data.DATA_LENGTH);
  
    @Override
    public void serialEvent(SerialPortEvent event) {  
        log.info("Data receiving...");
        
        switch (event.getEventType()) {  
        case SerialPortEvent.BI:  
        case SerialPortEvent.OE:  
        case SerialPortEvent.FE:  
        case SerialPortEvent.PE:  
        case SerialPortEvent.CD:  
        case SerialPortEvent.CTS:  
        case SerialPortEvent.DSR:  
        case SerialPortEvent.RI:  
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:  
            break;  
          
        case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据,并且给串口返回数据  
        {
            int b = 0; 
            try {
                while ((b = inputStream.read()) > 0) {
                    if (0x02 == b) {
                        bb.clear();
                    }
                    if (bb.hasRemaining())
                        bb.put((byte) b);
                }
                
                if (!bb.hasRemaining()) {
                    log.write(bb);
                    bb.clear();
                }
                
            } catch (Exception e) {
                log.error("receive data, "+e.getMessage()); return;
            }
        }
            
//            byte[] readBuffer = new byte[BUFFER_SIZE];  
//  
//            try {  
//                while (inputStream.available() > 0) {  
//                    log.info("stream available size: "+inputStream.available());  
//                    inputStream.read(readBuffer); 
//                }  
//                log.info("received: "+new String(readBuffer));  
//            } catch (IOException e) {  
//                log.error(e.getMessage()); return;  
//            }  
            break;    
        }  
    }  
    
    public void send(String content){  
        try {  
            outputStream.write(content.getBytes());
            outputStream.flush();
        } catch (IOException e) {  
            log.error(e.getMessage());         
        }  
    }  
     public void send(byte[] content){  
        try {  
            outputStream.write(content);
            outputStream.flush();
        } catch (IOException e) {  
            log.error(e.getMessage());         
        }  
    }  
      
    public void closePort() {  
        log.info(portId.getName() +" closed.");
        if (serialPort != null) {  
          serialPort.close();  
        } 
    }  
    
    private Log log;
    private static final int BUFFER_SIZE = 1024;
}  