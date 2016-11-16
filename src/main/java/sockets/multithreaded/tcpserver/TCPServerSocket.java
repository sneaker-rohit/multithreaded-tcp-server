// TCPServerSocket creates the server and waits for the incoming connections from client.
// On receiving a connection, it delegates the thread to the PoolController to manage it and 
// itself continously listens for the incoming connections

package sockets.multithreaded.tcpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerSocket {
	public static void main(String[] args) {
		if (args.length != 1) {
            System.err.println("Usage: java TCPServerSocket <Port Number>");
            System.exit(1);
        }
		
		int port = Integer.parseInt(args[0]);
		TCPServerSocket server = new TCPServerSocket();
		server.startServer(port);		
	}
	
	
	public void startServer(int port){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        
        try {
        	serverSocket = new ServerSocket(port);
            System.out.println("[+] Server is running.");
        } catch (IOException ex){
        	System.out.println("[-] Error while creating server socket");
        }
        
        try{
	        while (true){
	        	clientSocket = serverSocket.accept(); 
	        	System.out.println("Connected to the client: "+clientSocket.getInetAddress());
	        	
	        	PoolController responderThread = PoolController.getInstance();
	        	responderThread.performTask(clientSocket);
	        }           
        } catch (IOException e) {
            System.out.println("Exception:" +e.getMessage());
        }
        
        finally{
        	if(serverSocket != null){
        		try{
        			serverSocket.close();
        		}catch(Exception ex){
        			System.out.println("Closing server socket.");
        			ex.printStackTrace(System.err);
        		}
        		
        	}
        }
	}
}
