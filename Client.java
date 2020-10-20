/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment1;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author kaustubhrajpathak
 */

public class Client {
    
    final static int ServerPort=1234;

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
        
        try {
            final Scanner scn = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip,ServerPort);
            final DataInputStream dis = new DataInputStream(s.getInputStream());
            final DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            Thread sendMessage;
            sendMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true)
                    {
                        try
                        {
                            String msg = scn.nextLine();
                            dos.writeUTF(msg);
                        }
                        catch(IOException e)
                        {
                            System.out.println("writethread");
                            e.printStackTrace();
                        }
                    }
                }
            });
            
            Thread readMessage;
            readMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    String resp;
                    try 
                    {
                        //System.out.println(dis.readUTF()+" before loop");
                        while(true)
                        {
                            String msg = dis.readUTF();
                            System.out.println(msg);
                        }
                    } 
                    catch (IOException ex) 
                    {
                       System.out.println("readthread");
                       ex.printStackTrace();
                    } 
                }
            });
            
            sendMessage.start();
            readMessage.start();
        }
        
        catch (UnknownHostException ex) 
        {
            ex.printStackTrace();
        }
    }
    
}