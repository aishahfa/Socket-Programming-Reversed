
package Client;

import java.io.*;
import java.net.*;
 

public class Client{
    
    String serverurl = "127.0.0.1";
    int serverport = 8189;
 
    public static void main(String[] args){
        Client simpleclient = new Client();
        simpleclient.init();
    }
    
    public void init(){
        Socket socket = null;    
        try{
            System.out.println("Connecting to " + serverurl + " on port " + serverport);
            
            socket = new Socket(serverurl,serverport);

            socket.setSoTimeout(10000);
            System.out.println("Connected.");

            InputStreamReader inputstreamreader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            PrintWriter printwriter = new PrintWriter(socket.getOutputStream(),true);
            printwriter.println("abcdef");

            String lineread = "";

            while ((lineread = bufferedreader.readLine()) != null){
                System.out.println("Received from Server: " + lineread);
            }

            System.out.println("Closing connection.");
            
            bufferedreader.close();
            inputstreamreader.close();
            printwriter.close();
            socket.close();
            System.exit(0);

        }catch(UnknownHostException unhe){
            System.out.println("UnknownHostException: " + unhe.getMessage());
        }catch(InterruptedIOException interior){
            System.out.println("Timeout while attempting to establish socket connection.");
        }catch(IOException ioe){
            System.out.println("IOException: " + ioe.getMessage());
        }finally{
            try{
                socket.close();
            }catch(IOException ioe){
                System.out.println("IOException: " + ioe.getMessage());
          }
        }
      }
}

