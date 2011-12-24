#include <stdint.h>
#include <linux/input.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

int fd, rd;
struct input_event ev;

char devPath[256];

bool getDevPath() {
	FILE * fp = fopen("/proc/bus/input/devices","r");
	bool isGamepad = false;
	while(!feof(fp)) {
		char line[256];
		fgets(line, sizeof(line), fp);
		if((line[0]=='N') && (strstr(line, "Gamepad")!=0)) {
			isGamepad = true;
		}
		if((line[0]=='H') && isGamepad){
			line[strlen(line)-2]=0; //remove newline at end
			sprintf(devPath, "/dev/input/%s", line+12);
			printf("Device = '%s'\n", devPath);
			fclose(fp);
			return true;
		}
	}
	fclose(fp);
	return false;
}

bool openDevice(void){
	if(!getDevPath())
		return false;

	if( (fd = open(devPath,O_RDONLY)) < 0){
                return false;
        }

        return true;
}

int getKey(void){

	while(1) {
		rd = read(fd, &ev, sizeof(struct input_event));

		if( rd < (int) sizeof(struct input_event)){
			return -1; //error reading 
		}

		switch(ev.type){

			case 1: // absolute key
				if(ev.value == 1) // key down
					return ev.code;
			case 3: //relative (axis) key down
				if(ev.code == 0 && ev.value ==1) // x-axis left
					return 250;

				if(ev.code == 0 && ev.value ==255) // x-axis right
					return 251;

				if(ev.code == 1 && ev.value ==1) // y-axis up
					return 252;

				if(ev.code == 1 && ev.value ==255) // y-axis down
					return 253;

		}
	}
	return 0;
}

void closeDevice(void){

	close(fd);
	return;
}
