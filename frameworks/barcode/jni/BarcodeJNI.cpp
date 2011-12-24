#define LOG_TAG "Barcode"
#include "utils/Log.h"

#include <jni.h>
#include "Barcode.h"
#include <assert.h>

/*
 * Method:    getBarcode
 * Signature: ()Ljava.lang.String;
 */
JNIEXPORT jstring JNICALL BarcodeScanner_getBarcode(JNIEnv *env, jobject thiz) {
	const char *cs = getBarcode();
	if(!cs) {
		return NULL;
	}

	jstring sbc = env->NewStringUTF(cs);

	return sbc;
}


/*
 * Method:    open
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL BarcodeScanner_open(JNIEnv *env, jobject thiz) {
	jboolean ret = false;

	ret = openDevice();

	if(ret) {
		LOGI("Barcode scanner open : SUCCESS.");
	} else {
		LOGI("Barcode scanner open : FAILED. Check apk permissions");
	}

	return ret;
}

/*
 * Method:    close
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL BarcodeScanner_close(JNIEnv *env, jobject thiz) {
	jboolean ret = false;

	ret = closeDevice();

	if(ret) {
		LOGI("Barcode scanner close : SUCCESS.");
	} else {
		LOGI("Barcode scanner close : FAILED");
	}

	return ret;
}

/*
 * Array of methods.
 *
 * Each entry has three fields: the name of the method, the method
 * signature, and a pointer to the native implementation.
 */
static const JNINativeMethod gMethods[] = {
    { "open",          "()Z", (void*)BarcodeScanner_open },
    { "getBarcode",          "()Ljava/lang/String;", (void*)BarcodeScanner_getBarcode },
    { "close",          "()Z", (void*)BarcodeScanner_close },
};

/*
 * Explicitly register all methods for our class.
 *
 * While we're at it, cache some class references and method/field IDs.
 *
 * Returns 0 on success.
 */
static int registerMethods(JNIEnv* env) {
    LOGI("Registering Methods\n");
    static const char* const kClassName =
        "com/sample/hardware/barcode/BarcodeScanner";
    jclass clazz;

    /* look up the class */
    clazz = env->FindClass(kClassName);
    if (clazz == NULL) {
        LOGE("Can't find class %s\n", kClassName);
        return -1;
    }

    /* register all the methods */
    if (env->RegisterNatives(clazz, gMethods,
            sizeof(gMethods) / sizeof(gMethods[0])) != JNI_OK)
    {
        LOGE("Failed registering %d methods for %s\n", sizeof(gMethods)/sizeof(gMethods[0]), kClassName);
        return -1;
    }

    //  Success!
    return 0;
}


/*
 * This is called by the VM when the shared library is first loaded.
 */
jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env = NULL;
    jint result = -1;

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("ERROR: GetEnv failed\n");
        goto bail;
    }
    assert(env != NULL);

    if (registerMethods(env) != 0) {
        LOGE("ERROR: Barcode Scanner native registration failed\n");
        goto bail;
    }

    /* success -- return valid version number */
    result = JNI_VERSION_1_4;

bail:
    return result;
}
