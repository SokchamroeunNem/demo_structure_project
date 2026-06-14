#include <jni.h>
#include <string>

extern "C" {
    JNIEXPORT jstring JNICALL
    Java_com_example_demo_util_AppSecretManager_getApiBaseUrl(JNIEnv *env, jobject obj) {
        return env->NewStringUTF("https://jsonplaceholder.typicode.com/production");
    }
}