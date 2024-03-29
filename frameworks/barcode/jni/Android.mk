
LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := eng

# This is the target being built.
LOCAL_MODULE:= libsample_barcode

# All of the source files that we will compile.
LOCAL_SRC_FILES:= BarcodeJNI.cpp Barcode.cpp 

# All of the shared libraries we link against.
LOCAL_SHARED_LIBRARIES := \
	libandroid_runtime \
	libnativehelper \
	libcutils \
	libutils \
	libc \

# No static libraries.
LOCAL_STATIC_LIBRARIES :=

# Also need the JNI headers.
LOCAL_C_INCLUDES += \
	$(JNI_H_INCLUDE) \

# No special compiler flags.
LOCAL_CFLAGS +=

# Don't prelink this library.  For more efficient code, you may want
# to add this library to the prelink map and set this to true.
LOCAL_PRELINK_MODULE := false

include $(BUILD_SHARED_LIBRARY)

#
# Test program
#
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := eng

# This is the target being built.
LOCAL_MODULE:= BarcodeTest

# All of the source files that we will compile.
LOCAL_SRC_FILES:= BarcodeTest.cpp Barcode.cpp

# All of the shared libraries we link against.
LOCAL_SHARED_LIBRARIES := \
	libandroid_runtime \
	libnativehelper \
	libcutils \
	libutils \
	libc \

# No static libraries.
LOCAL_STATIC_LIBRARIES :=

# No special compiler flags.
LOCAL_CFLAGS +=

# Don't prelink this library.  For more efficient code, you may want
# to add this library to the prelink map and set this to true.
LOCAL_PRELINK_MODULE := false

include $(BUILD_EXECUTABLE)
