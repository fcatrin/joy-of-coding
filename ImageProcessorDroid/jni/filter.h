#ifndef _FILTER_H
#define  _FILTER_H

#ifdef __cplusplus
extern "C" {
#endif


#define MATRIX_ELEMENTS 9

void filter(int w, int h, int pixels[]);
void filter_set_matrix(float new_matrix[], int new_matrix_size);

#ifdef __cplusplus
}
#endif

#endif
