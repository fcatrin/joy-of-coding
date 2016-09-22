#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "filter.h"

static float matrix[] = {
		-1.0, -1.0, -1.0,
		-1.0, 9.0,  -1.0,
		-1.0, -1.0, -1.0
};

#define MATRIX_SIZE 1

static long addR(long sum, float mag, int pixel) {
	int component = (pixel & 0xFF0000) >> 16;
	return sum + component * mag;
}

static long addG(long sum, float mag, int pixel) {
	int component = (pixel & 0x00FF00) >> 8;
	return sum + component * mag;
}

static long addB(long sum, float mag, int pixel) {
	int component = (pixel & 0x0000FF) >> 0;
	return sum + component * mag;
}

static int *staticBuffer = NULL;
static int lastWidth = -1;
static int lastHeight = -1;


static int getBufferSize(int width, int height) {
	return width * height * sizeof(int);
}

static int *getBuffer(int width, int height) {
	if (staticBuffer == NULL || width!=lastWidth || height!=lastHeight) {
		if (staticBuffer != NULL) free(staticBuffer);
		staticBuffer = malloc(getBufferSize(width, height));
	}
	return staticBuffer;
}

void filter_set_matrix(float new_matrix[], int new_matrix_size) {
	if (new_matrix_size != MATRIX_ELEMENTS) {
		printf("Invalid matrix size %d. Required: %d", new_matrix_size, MATRIX_ELEMENTS);
		return;
	}

	memcpy(matrix, new_matrix, MATRIX_ELEMENTS * sizeof(float));
}

void filter(int width, int height, int pixels[]) {

	int matrix_w = MATRIX_SIZE*2 + 1;
	float divisor = 0;
	for(int n = 0; n < matrix_w * matrix_w; n++) {
		if (matrix[n]!=0) divisor += matrix[n];
	}

	if (divisor == 0) divisor = 1;

	int *buffer = getBuffer(width, height);

	for(int y=0; y<height; y++) {
		for(int x=0; x<width; x++) {
			long r = 0;
			long g = 0;
			long b = 0;

			int x0 = x - MATRIX_SIZE;
			int y0 = y - MATRIX_SIZE;
			int x1 = x + MATRIX_SIZE;
			int y1 = y + MATRIX_SIZE;

			for(int py = y0; py <= y1; py++) {
				for(int px = x0; px <= x1; px++) {
					int rx = px<0 ? 0 : (px<width  ? px : (width-1));
					int ry = py<0 ? 0 : (py<height ? py : (height-1));

					int mx = px - x0;
					int my = py - y0;
					int mindex = my * (MATRIX_SIZE * 2 + 1) + mx;

					int index = ry * width + rx;
					r = addR(r, matrix[mindex], pixels[index]);
					g = addG(g, matrix[mindex], pixels[index]);
					b = addB(b, matrix[mindex], pixels[index]);
				}
			}

			r = r / divisor;
			g = g / divisor;
			b = b / divisor;

			if (r<0) r = 0;
			if (r>255) r = 255;

			if (g<0) g= 0;
			if (g>255) g = 255;

			if (b<0) b = 0;
			if (b>255) b = 255;

			int index = y*width + x;
			buffer[index] = 0xFF000000 | ((r << 16) + (g << 8) + b);
		}
	}
	memcpy(pixels, buffer, getBufferSize(width, height));
}
