CC = gcc
LD = gcc


LDFLAGS = -lm -shared 
LIBPREFIX =
LIBARCH =

LIBPREFIX = lib
LDFLAGS += -lstdc++

UNAME_S := $(shell uname -s)
UNAME_P := $(shell uname -p)
ifeq ($(UNAME_S),Linux)
	JAVA_HOME = /usr/lib/jvm/default-java/
    CFLAGS += -D LINUX -fPIC
    LIBEXT = so
    ifeq ($(UNAME_P),x86_64)
		LIBARCH = 64
	endif
	ifneq ($(filter %86,$(UNAME_P)),)
	endif
endif
ifeq ($(UNAME_S),Darwin)
	JAVA_HOME = /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home
    CFLAGS += -D OSX -I$(JAVA_HOME)/include/darwin/ -mmacosx-version-min=10.8
    LDFLAGS += -mmacosx-version-min=10.8
    LIBEXT = jnilib
	LIBARCH = 64
endif


OBJDIR = ../obj
LIBSDIR = ../libs

LINK_TARGET = $(LIBSDIR)/$(LIBPREFIX)processor$(LIBARCH).$(LIBEXT)

CFLAGS += \
 -O2 -Wall -I . -g -I$(JAVA_HOME)/include/ 
 
CFLAGS_ONLY = -std=gnu99
  
OBJS = \
	processor.o \
	filter.o

%.o: %.c 
	$(CC) -c $(CFLAGS) $(CFLAGS_ONLY) -o $@ $< 

%.o: %.cpp
	$(CC) -c $(CFLAGS) -o $@ $< 


$(LINK_TARGET) : $(OBJS)
	$(LD) -o $@ $^ $(LDFLAGS)

all : $(LINK_TARGET)

clean :
	find . -name "*.o" -exec rm {} \;
