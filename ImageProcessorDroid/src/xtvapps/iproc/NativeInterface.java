package xtvapps.iproc;

public class NativeInterface {
    static {
        System.loadLibrary("processor");
    }
    
    public static native void setMatrix(float matrix[]);
	public static native void process(int w, int h, int raw[]);
}
