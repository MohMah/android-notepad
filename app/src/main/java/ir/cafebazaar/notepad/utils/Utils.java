package ir.cafebazaar.notepad.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import ir.cafebazaar.notepad.App;
import java.io.ByteArrayOutputStream;

/**
 * Created by MohMah on 8/19/2016.
 */
public class Utils{
	public static float getPixels(int unit, float size){
		DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
		return TypedValue.applyDimension(unit, size, metrics);
	}

	public static float sp2px(float sizeInSp){
		return getPixels(TypedValue.COMPLEX_UNIT_SP, sizeInSp);
	}

	public static float dp2px(float sizeInDp){
		return getPixels(TypedValue.COMPLEX_UNIT_DIP, sizeInDp);
	}

	public static void hideSoftKeyboard(View view){
		InputMethodManager inputMethodManager =
				(InputMethodManager) App.CONTEXT.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (view != null) inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static void showSoftKeyboard(View view){
		if (view == null) return;
		try{
			InputMethodManager inputManager =
					(InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		}catch (Exception e){
			Log.e("Utils", "can't show keyboard ", e);
		}
	}

	// convert from bitmap to byte array
	public static byte[] getBytes(Bitmap bitmap){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
		return stream.toByteArray();
	}

	// convert from byte array to bitmap
	public static Bitmap getImage(byte[] image){
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}
}
