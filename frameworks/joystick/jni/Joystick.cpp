#define LOG_TAG "Joystick"
#include "utils/Log.h"

#include <stdint.h>
#include <linux/input.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <assert.h>

#include "Joystick.h"
#include "Jsfunctions.h"

JNIEXPORT jboolean JNICALL Java_Joystick_open
  (JNIEnv *, jclass) {

	return (openDevice());
}

JNIEXPORT jint JNICALL Java_Joystick_getKey
  (JNIEnv *, jclass) {
	
	return (getKey());
}

JNIEXPORT void JNICALL Java_Joystick_close
  (JNIEnv *, jclass) {

	closeDevice();
	return;
}

static const JNINativeMethod gMethods[] = {
    { "open",          "()Z", (void*)Java_Joystick_open },
    { "getKey",          "()I", (void*)Java_Joystick_getKey },
    { "close",          "()V", (void*)Java_Joystick_close },
};

static int registerMethods(JNIEnv* env) {
    LOGI("Registering Methods\n");
    static const char* const kClassName =
        "com/sample/hardware/joystick/Joystick";
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
        LOGE("ERROR: Joystick native registration failed\n");
        goto bail;
    }

    /* success -- return valid version number */
    result = JNI_VERSION_1_4;

bail:
    return result;
}
