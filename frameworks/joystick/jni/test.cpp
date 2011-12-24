#include <stdio.h>
#include <string.h>
#include "Jsfunctions.h"

int main(void){

	printf("\nopening device %d\n", openDevice());
	for(int i=0;i<10;i++) {
		printf("Waiting for key %d\n", i);
		printf("\nkey %d\n",getKey());
	}
	closeDevice();
	return 0;
}
