LOCAL_PATH := $(call my-dir)

# The JAVA code for the platform library that supports
# the Barcode scanner
# ============================================================
include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
        com/sample/hardware/barcode/BarcodeScanner.java \
	com/sample/hardware/barcode/IBarcodeScanner.aidl \
	com/sample/hardware/barcode/BarcodeCallback.aidl

LOCAL_MODULE_TAGS := eng

# This is the target being built.
LOCAL_MODULE:= com.sample.hardware.barcode

include $(BUILD_JAVA_LIBRARY)

# The JNI components
# ============================================================
# Also build all of the sub-targets under this one: the library's
# associated JNI code, and a sample client of the library.
include $(call all-subdir-makefiles)

# Service Component
#
# ===========================================================
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := eng

# APK containing services & activity intents
#
# ==========================================================

# This is the target being built.
LOCAL_PACKAGE_NAME := BarcodeScannerService

# Only compile source java files in this apk.
LOCAL_SRC_FILES := \
	com/sample/service/barcode/BarcodeScannerService.java 

# Link against the current Android SDK.
LOCAL_SDK_VERSION := current

# Also link against our own fps library.
LOCAL_JAVA_LIBRARIES := com.sample.hardware.barcode

# We ask for restricted permissions for our service, 
# so the apk has to be signed
LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)

# Put the file for the platform library in the right place
PRODUCT_COPY_FILES += $(LOCAL_PATH)/com.sample.hardware.barcode.xml:system/etc/permissions/com.sample.hardware.barcode.xml
