package xtvapps.iproc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class Utils {
	
	private static final int BUF_SIZE = 0x10000;


	public static byte[] loadFromAssets(Context context, String path) throws IOException {
		InputStream is = null;
		try {
			is = context.getAssets().open(path);
			return loadFromStream(is);
		} finally {
			if (is!=null) is.close();
		}
		
	}
	
	public static byte[] loadFromStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUF_SIZE];
		int bufferLength = 0;

		while ((bufferLength = is.read(buffer)) > 0) {
			baos.write(buffer, 0, bufferLength);
		}
		return baos.toByteArray();
	}
	
	
	public static Bitmap decodeBitmap(byte[] raw) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		return BitmapFactory.decodeByteArray(raw , 0, raw .length, options);
	}
	
	public static void relayoutChildren(View view) {
		if (view == null) return;
	    view.measure(
	        View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
	        View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
	    view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
	}

}
