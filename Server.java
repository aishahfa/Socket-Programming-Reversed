
package Server;

import java.io.*;
import java.net.*;
import java.util.*;
 
public class Server{
 
    public static void main(String[] args){
        Server simpleserver = new Server();
        simpleserver.init();
    }
 
    public void init(){
        ServerSocket serversocket = null;
        Socket socket = null;
        try{
            serversocket = new ServerSocket(8189);
            System.out.println("Listening at 127.0.0.1 on port 8189");

            socket = serversocket.accept();

            InputStreamReader inputstreamreader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            PrintWriter printwriter = new PrintWriter(socket.getOutputStream(),true);

            String datetimestring = (Calendar.getInstance()).getTime().toString();
            printwriter.println("You connected to the Server at " + datetimestring);

            String lineread = "";
            String result = "";
            boolean done = false;
            StringBuilder sb = new StringBuilder();
            
            while (((lineread = bufferedreader.readLine()) != null) && (!done)){
                System.out.println("Received from Client: " + lineread);
                sb.append(lineread);
                String str = sb.toString();

                char[] strchar = str.toCharArray();
                System.out.print("Send to Client: ");
                for (int i = strchar.length-1; i>=0; i--) {
                    System.out.print(strchar[i]);
                    result = result + strchar[i];
                }
                    printwriter.println("You sent " + lineread);
                    printwriter.println(result + "\n");

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