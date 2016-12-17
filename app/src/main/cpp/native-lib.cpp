#include <jni.h>
#include <string>

extern "C"

jstring Java_com_dkjar_mobile_MainActivity_stringFromJNI(JNIEnv* env, jobject /* this */) {

    std::string hello = "hi jetty,  Hello from C++ ";

    return env->NewStringUTF(hello.c_str());
}
