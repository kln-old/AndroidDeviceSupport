package com.sample.hardware.joystick;

public class Joystick {
	static {

		System.loadLibrary("sample_joystick");
	}

	public static native boolean open();
	public static native int getKey();
	public static native void close();
};
