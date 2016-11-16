// DisplayMessages Class is responsible for handling the different kinds of messages that arrive from multiple clients.
// On receiving HELO or helo it responds back to the client in the specified format. On receiving KILL_SERVICE it terminates 
// the connection to the client. 


package sockets.multithreaded.tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DisplayMessages {
	
	public void displayMessage(Socket clientSocket){
		try{ 
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                   
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
       
			String inputLine;
			while ((inputLine = in.readLine()) != null) {				
				WorkerThread resThread = (WorkerThread)Thread.currentThread();
				if ((inputLine == null) || inputLine.equalsIgnoreCase("KILL_SERVICE")) {
					
					PoolController.getInstance().releaseTask();
					out.println("Client connection closed");
					clientSocket.close();
                    return;
                } else if ((inputLine == null) || inputLine.equalsIgnoreCase("helo")){
                	out.println("Response from server: "+inputLine+ " IP address: "+clientSocket.getInetAddress() + " Port number: "+clientSocket.getPort() + " Student: Rohit Tahiliani");
                } else {
                	out.println("Sorry, incorrect input. Please type in HELO or helo.");
                }
			}
		} catch (IOException ex){
			System.out.println("Error while sending or receiving data");
		}
	}
}
