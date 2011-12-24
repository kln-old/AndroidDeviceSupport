#include <stdio.h>
#include "Barcode.h"

int main() {
	if(openDevice()) {
		printf("Waiting for barcode...\n");
		char *bc;
		if((bc = getBarcode())!=0) {
			printf("Barcode = '%s'\n", bc);
		} else {
			printf("Unable to get barcode\n");
		}
		closeDevice();
	} else {
		fprintf(stderr, "Unable to open device\n");
	} 
	return 0;
}
