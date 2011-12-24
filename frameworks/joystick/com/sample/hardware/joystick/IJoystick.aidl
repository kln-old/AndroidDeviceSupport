package com.sample.hardware.joystick;
import com.sample.hardware.joystick.JoystickCallback;

interface IJoystick {
	boolean setCallback(in JoystickCallback cb);
	boolean clearCallback();
}
