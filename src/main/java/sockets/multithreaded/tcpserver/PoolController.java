// PoolController class manages the life cycle of the threads  

package sockets.multithreaded.tcpserver;

import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PoolController {
	private int maximumCapacity;
	private static PoolController instance; 
	
	private Set<WorkerThread>  pool;
	
	private PoolController(){
		maximumCapacity = 4;		
		pool = new HashSet<WorkerThread>(maximumCapacity);
	}
	
	public static PoolController getInstance(){
		if(instance == null){
			instance = new PoolController();
		}		
		return instance;
	}
	
	public int getMaximumPoolSize(){
		return maximumCapacity;
	}
	
	public int getCurrentPoolSize(){
		return pool.size();
	}
	
	public int getAvailableThreads(){
		int counter = 0;
		for (Iterator<WorkerThread> iterator = pool.iterator(); iterator.hasNext();) {
			WorkerThread thread = (WorkerThread) iterator.next();
			if(thread.getRunnbingStatus().equals(WorkerThread.STATUS_AVAILABLE)){
				++counter;
			}			
		}
		return counter;
	}
	
	public int getBusyThreads(){
		int counter = 0;
		for (Iterator<WorkerThread> iterator = pool.iterator(); iterator.hasNext();) {
			WorkerThread thread = (WorkerThread) iterator.next();
			if(thread.getRunnbingStatus().equals(WorkerThread.STATUS_BUSY)){
				++counter;
			}			
		}
		return counter;
	}

	public void performTask(Socket clientSocket){
		WorkerThread thread = getThreadFromPool();
    	thread.setClientSocket(clientSocket);
    	thread.setRunningStatus(WorkerThread.STATUS_BUSY);    	
	}
	
	public void releaseTask(){
		WorkerThread responderThread = (WorkerThread)Thread.currentThread();
		responderThread.setRunningStatus(WorkerThread.STATUS_AVAILABLE);		
	}
	
	
	public WorkerThread getThreadFromPool(){
		System.out.println("Current size of pool "+pool.size());
		if(pool.size() == maximumCapacity){
			System.out.println("Number of connections exceeded. Please wait");
			return null;
		}
		
		for (Iterator<WorkerThread> iterator = pool.iterator(); iterator.hasNext();) {
			WorkerThread thread = (WorkerThread) iterator.next();
			String threadStatus = thread.getRunnbingStatus();
			if(threadStatus.equals(WorkerThread.STATUS_AVAILABLE)){
				return thread;
			}
		}
	
		WorkerThread newThread = new WorkerThread();
		newThread.setRunningStatus(WorkerThread.STATUS_AVAILABLE);
		newThread.start();
		pool.add(newThread);
		return newThread;
	}
}
