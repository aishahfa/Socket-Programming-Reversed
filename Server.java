/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/*  SimpleServer.java
*  @author Charles Bell
*  @version December 15, 2000
*  email: charles@quantumhyperspace.com
*/
 
import java.io.*;
import java.net.*;
import java.util.*;
 
/**  When started allows one client to connect. It listens on port 8189.
*  It returns whatever a connected client sends.
*  It shuts down when the client sends a Bye line.
*
*/
public class Server{
 
  /**  Instantiates an instance of the SimpleServer class and initilaizes it.
  */
  public static void main(String[] args){
    Server simpleserver = new Server();
    simpleserver.init();
  }
 
  /**  Sets up a ServerSocket and listens on port 8189.
  */
  public void init(){
    ServerSocket serversocket = null;
    Socket socket = null;
    try{
      //establish a server socket monitoring port 8189 
      //port 8189 is not used by any services
      serversocket = new ServerSocket(8189);
      System.out.println("Listening at 127.0.0.1 on port 8189");
 
      //wait indefinitely until a client connects to the socket
      socket = serversocket.accept();
 
      //set up communications for sending and receiving lines of text data
      //establish a bufferedreaderr from the input stream provided by the socket object
      InputStreamReader inputstreamreader = new InputStreamReader(socket.getInputStream());
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
 
      //establish an printwriter using the output streamof the socket object
      //and set auto flush on    
      PrintWriter printwriter = new PrintWriter(socket.getOutputStream(),true);
 
      //for binary data use
      // DataInputStream and DataOutputStream
 
      //for serialized objects use
      // ObjectInputStream and ObjectOutputStream
 
      String datetimestring = (Calendar.getInstance()).getTime().toString();
      printwriter.println("You connected to the Simple Server at " + datetimestring);
 
      String lineread = "";
      String result = "";
      boolean done = false;
      StringBuilder sb = new StringBuilder();
      while (((lineread = bufferedreader.readLine()) != null) && (!done)){
        System.out.println("Received from Client: " + lineread);
        sb.append(lineread);
            
            String str = sb.toString();
            
            char[] messChar = str.toCharArray();
            
            for (int i=messChar.length-1; i>=0; i--) {
                System.out.print(messChar[i]+" ");
                result = result + messChar[i];
            }
                printwriter.println("You sent " + lineread);
                printwriter.println("So, here in return : " + result);
        
        if (lineread.compareToIgnoreCase("Bye") == 0) done = true;
      }
      System.out.println("Closing connection.");
      bufferedreader.close();
      inputstreamreader.close();
      printwriter.close();
      socket.close();
    }catch(UnknownHostException unhe){
      System.out.println("UnknownHostException: " + unhe.getMessage());
    }catch(InterruptedIOException intioe){
      System.out.println("Timeout while attempting to establish socket connection.");
    }catch(IOException ioe){
      System.out.println("IOException: " + ioe.getMessage());
    }finally{
      try{
        socket.close();
        serversocket.close();
      }catch(IOException ioe){
        System.out.println("IOException: " + ioe.getMessage());
      }
    }
  }
} 