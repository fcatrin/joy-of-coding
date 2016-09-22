#include <stdlib.h>
#include "xtvapps_iproc_NativeInterface.h"
#include "filter.h"

JNIEXPORT void JNICALL Java_xtvapps_iproc_NativeInterface_process
  (JNIEnv *env, jclass clazz, jint width, jint height, jintArray pixels) {

	jint *c_pixels = env->GetIntArrayElements(pixels, NULL);

	filter(width, height, c_pixels);

	env->ReleaseIntArrayElements(pixels, c_pixels, 0);
}


JNIEXPORT void JNICALL Java_xtvapps_iproc_NativeInterface_setMatrix
  (JNIEnv *env, jclass clazz, jfloatArray matrix) {

	jsize matrix_size = env->GetArrayLength(matrix);

	jfloat *c_matrix = env->GetFloatArrayElements(matrix, NULL);

	filter_set_matrix(c_matrix, matrix_size);

	env->ReleaseFloatArrayElements(matrix, c_matrix, 0);

}
