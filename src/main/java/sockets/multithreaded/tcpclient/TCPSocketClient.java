// TCPSocketClient connects to the server. Allows the client to send messages and prints out the response from the server.

package sockets.multithreaded.tcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSocketClient {
	public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java TCPSocketClient <Host Name> <Port Number>");
            System.exit(1);
        }
 
        String host = args[0];
        int port = Integer.parseInt(args[1]);
 
        try {
            Socket echoSocket = new Socket(host, port);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        
            System.out.println("[+] Connected to the Server.");
            System.out.println("Send HELO or helo. To quit, send KILL_SERVICE ");
            
            String serverInput;
            while ((serverInput = stdIn.readLine()) != null) {
                out.println(serverInput);
  
                String serverResponse = in.readLine();
                System.out.println("[+] Server: " + serverResponse);
                if(serverResponse.equalsIgnoreCase("Client connection closed")){           	
                	break;
                }
            }
            System.out.println("Terminating client process.");
        } catch (UnknownHostException e) {
            System.err.println("Unknown Host " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IO Exception for " + host);
            System.exit(1);
        } 
    }
}
