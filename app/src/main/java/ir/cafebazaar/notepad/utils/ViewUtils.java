package ir.cafebazaar.notepad.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by MohMah on 8/18/2016.
 */
public class ViewUtils{
	public static View getEmptyView(Context context, int width, int height, @ColorRes int backgroundColor){
		View view = new View(context);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, height);
		view.setLayoutParams(params);
		view.setBackgroundResource(backgroundColor);
		return view;
	}
}
