#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>

int main(){
	int clientSock, portNo, nBytes;
	char buffer[1024];
	struct sockaddr_in serverAddr;
	socklen_t addr_size;

	clientSock = socket(PF_INET, SOCK_STREAM, 0);

	portNo = 7891;

	serverAddr.sin_family = AF_INET;
	serverAddr.sin_port = htons(portNo);
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
	memset(serverAddr.sin_zero, '\0', sizeof serverAddr.sin_zero);  

	addr_size = sizeof serverAddr;
	connect(clientSock, (struct sockaddr *) &serverAddr, addr_size);

	printf("Type a sentence to send to server:\n");

	fgets(buffer,1024,stdin);

	printf("You typed: %s",buffer);

	nBytes = strlen(buffer) + 1;

	send(clientSock,buffer,nBytes,0);

	recv(clientSock, buffer, 1024, 0);

	printf("Received from server: %s\n\n",buffer); 

	return 0;
}
