package com.sample.hardware.barcode;

interface BarcodeCallback {
	void onBarcode(boolean valid, String barcode);
}
