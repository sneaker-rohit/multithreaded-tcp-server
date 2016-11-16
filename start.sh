# runs the TCP Socket Server on the entered port number 

mvn exec:java -Dexec.mainClass="sockets.multithreaded.tcpserver.TCPServerSocket" -Dexec.args="$1"
