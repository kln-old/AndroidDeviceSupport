#include <stdio.h>

FILE *fp = NULL;
static char buffer[256];

bool openDevice() {
	fp = fopen("/dev/ttyACM0", "r");
	return (fp!=NULL);
}

char* getBarcode() {
	bool eolReached = false;
	char *pBuffer = buffer;
	while(!eolReached) {
		int ch = fgetc(fp);
		if(ch<0) {
			return NULL;
		}
		if((ch==13) || (ch==10)) {
			eolReached = true;
		} else {
			*pBuffer++ = ch;
		}
		if(pBuffer == (buffer+sizeof(buffer)-1))
			return NULL;
	}
	*pBuffer++ = 0;
	return buffer;
}

bool closeDevice() {
	if(fp) {
		fclose(fp);
		fp = NULL;
		return true;
	}
	return false;
}
