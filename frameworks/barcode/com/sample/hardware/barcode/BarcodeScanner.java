package com.sample.hardware.barcode;

public class BarcodeScanner {
	static {

		System.loadLibrary("sample_barcode");
	}

	public static native boolean open();
	public static native String getBarcode();
	public static native boolean close();
};
