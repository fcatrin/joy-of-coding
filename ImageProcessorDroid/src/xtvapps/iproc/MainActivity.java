package xtvapps.iproc;

import java.io.IOException;

import android.app.Activity;
import android.app.NativeActivity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final String MEDIA_SRC = "media/input.jpg";
	private static final int MATRIX_RANGE = 20;
	Bitmap srcImage = null;
	Bitmap dstImage = null;
	
	float matrix[] = {
			0, 0, 0,
			0, 1, 0,
			0, 0, 0
	};
	
	int pixels[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		int barResourceIds[] = {
				R.id.barMatrix0, R.id.barMatrix1, R.id.barMatrix2,
				R.id.barMatrix3, R.id.barMatrix4, R.id.barMatrix5,
				R.id.barMatrix6, R.id.barMatrix7, R.id.barMatrix8,
		};
		
		for(int i=0; i< barResourceIds.length; i++) {
			final int index = i;
			SeekBar seekBar = (SeekBar)findViewById(barResourceIds[i]);
			seekBar.setMax(MATRIX_RANGE);
			seekBar.setProgress((int)(matrix[i] + MATRIX_RANGE/2));
			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					matrix[index] = progress - MATRIX_RANGE / 2;
					updateImage();
				}
			});
		}
		
		loadSourceImage();
	}

	private void loadSourceImage() {
		AsyncTask<String, Void, Bitmap> task = new AsyncTask<String, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(String... params) {
				try {
					byte raw[] = Utils.loadFromAssets(MainActivity.this, params[0]);
					return Utils.decodeBitmap(raw);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				if (result == null) return;

				srcImage = result;

				int w = srcImage.getWidth();
				int h = srcImage.getHeight();
				dstImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
				pixels = new int[w*h];
				
				updateImage();
			}
		};
		task.execute(MEDIA_SRC);
	}
	
	private void updateImage() {
		int w = srcImage.getWidth();
		int h = srcImage.getHeight();
		srcImage.getPixels(pixels, 0, w, 0, 0, w, h);
		
		NativeInterface.setMatrix(matrix);
		NativeInterface.process(w, h, pixels);
		
		dstImage.setPixels(pixels, 0, w, 0, 0, w, h);
		
		ImageView imageView = (ImageView)findViewById(R.id.image);
		imageView.setImageBitmap(dstImage);
		
		// fucking Android
		imageView.setMinimumWidth(imageView.getHeight() * w  / h);
		
		TextView txtMatrix = (TextView)findViewById(R.id.txtMatrix);
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<matrix.length; i++) {
			buf.append(matrix[i]);
			if ( (i+1) % 3 == 0) {
				buf.append("\n");
			} else {
				buf.append(", ");
			}
		}
		txtMatrix.setText(buf);
	}
}
