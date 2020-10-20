/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author kaustubhrajpathak
 */
class ClientHandler implements Runnable {
    
    Scanner sch = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    
    public ClientHandler(Socket s,String name,DataInputStream dis, DataOutputStream dos)
    {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin = true;

    }
    
    

    @Override
    public void run() {
        String recieved;
        while(true)
        {
            try
            {
                //System.out.println(name);
                recieved = dis.readUTF();
                System.out.println(recieved);
                if(recieved.equals("logout"))
                {
                    this.isloggedin = false;
                    this.s.close();
                    break;
                }
                
                StringTokenizer st = new StringTokenizer(recieved,"@");
                String MsgToSend = st.nextToken();
                String Recipient = st.nextToken();
                for(ClientHandler mc : Server.ar)
                {
                    if(mc.name.equals(Recipient)&&mc.isloggedin==true)
                    {
                        mc.dos.writeUTF(this.name+":"+MsgToSend);
                        break;
                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
            try
            {
                this.dis.close();
                this.dos.close();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
        

        //To change body of generated methods, choose Tools | Templates.
    }
    
}
