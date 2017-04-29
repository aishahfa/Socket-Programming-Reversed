#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>

int main(){
	int welcomeSock, newSock, portNo, clientLen, nBytes;
	char buffer[1024], revstr[1024];
	struct sockaddr_in serverAddr;
	struct sockaddr_storage serverStorage;
	socklen_t addr_size;
	int i, j;

	welcomeSock = socket(PF_INET, SOCK_STREAM, 0);

	portNo = 7891;
  
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_port = htons(portNo);
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
	memset(serverAddr.sin_zero, '\0', sizeof serverAddr.sin_zero);  

	bind(welcomeSock, (struct sockaddr *) &serverAddr, sizeof(serverAddr));

	if(listen(welcomeSock, 5) == 0)
		printf("Listening\n");
	else
		printf("Error\n");

	addr_size = sizeof serverStorage;

	while(1){
		newSock = accept(welcomeSock, (struct sockaddr *) &serverStorage, &addr_size);

		if(!fork()){
			nBytes = 1;
			j = 0;

      			while(nBytes!=0){
				nBytes = recv(newSock, buffer, 1024, 0);
  
				for (i=nBytes-2; i>=0; i--){
					revstr[j] = buffer[i];
					j = j+1;
				}
				send(newSock,buffer,nBytes,0);
			}

			printf("Received from client: %s", buffer);
			printf("Send to client: %s\n\n", revstr);
			close(newSock);
			exit(0);
    		} 
		else{
      			close(newSock);
		}
	}
	return 0; 
}