LOCAL_PATH := $(call my-dir)

# The JAVA code for the platform library that supports
# the Joystick
# ============================================================
include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
        com/sample/hardware/joystick/Joystick.java \
	com/sample/hardware/joystick/IJoystick.aidl \
	com/sample/hardware/joystick/JoystickCallback.aidl

LOCAL_MODULE_TAGS := eng

# This is the target being built.
LOCAL_MODULE:= com.sample.hardware.joystick

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
LOCAL_PACKAGE_NAME := JoystickService

# Only compile source java files in this apk.
LOCAL_SRC_FILES := \
	com/sample/service/JoystickService.java 

# Link against the current Android SDK.
LOCAL_SDK_VERSION := current

# Also link against our own fps library.
LOCAL_JAVA_LIBRARIES := com.sample.hardware.joystick

# We ask for restricted permissions for our service, 
# so the apk has to be signed
LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)

# Put the file for the platform library in the right place
PRODUCT_COPY_FILES += $(LOCAL_PATH)/com.sample.hardware.joystick.xml:system/etc/permissions/com.sample.hardware.joystick.xml
