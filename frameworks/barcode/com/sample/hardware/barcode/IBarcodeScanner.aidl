package com.sample.hardware.barcode;
import com.sample.hardware.barcode.BarcodeCallback;

interface IBarcodeScanner {
	boolean setCallback(in BarcodeCallback cb);
	boolean getBarcode();
	boolean clearCallback();
}
