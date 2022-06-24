#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_zy_collector_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject thiz) {
    std::string hello = "Hello JNI";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_zy_collector_MainActivity_getIndex(JNIEnv *env, jobject thiz) {
    return 2;
}