/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class Joystick */

#ifndef _Included_Joystick
#define _Included_Joystick
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     Joystick
 * Method:    open
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_Joystick_open
  (JNIEnv *, jclass);

/*
 * Class:     Joystick
 * Method:    getKey
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_Joystick_getKey
  (JNIEnv *, jclass);

/*
 * Class:     Joystick
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_Joystick_close
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif