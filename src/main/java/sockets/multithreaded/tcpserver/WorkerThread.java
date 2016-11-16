// WorkerThread contains the actual thread that is performing the task

package sockets.multithreaded.tcpserver;

import java.net.Socket;

public class WorkerThread extends Thread{
	public static final String STATUS_BUSY = "BUSY";
	public static final String STATUS_AVAILABLE = "AVAILABLE";
	
	DisplayMessages task = null;
	private Socket clientSocket = null;
	private volatile String status;

	public WorkerThread(){
		status = new String();
		task = new DisplayMessages();
	}
	
	public void setClientSocket(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	public String getRunnbingStatus() {
		return status;
	}
	
	public void setRunningStatus(String status) {
		this.status = status;
	}

	public void run() {
		while(true){
			if(status.equals(STATUS_BUSY)){
				task.displayMessage(clientSocket);
			}
		}
	}
}
