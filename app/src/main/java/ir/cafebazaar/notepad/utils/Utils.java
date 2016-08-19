package ir.cafebazaar.notepad.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

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
}
