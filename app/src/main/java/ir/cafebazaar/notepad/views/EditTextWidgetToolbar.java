package ir.cafebazaar.notepad.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import ir.cafebazaar.notepad.App;
import ir.cafebazaar.notepad.R;

/**
 * Created by MohMah on 8/20/2016.
 */
public class EditTextWidgetToolbar extends Toolbar{
	public EditTextWidgetToolbar(Context context){
		this(context, null);
	}

	public EditTextWidgetToolbar(Context context, @Nullable AttributeSet attrs){
		this(context, attrs, 0);
	}

	public EditTextWidgetToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
		super(context, attrs, defStyleAttr);
		setBackgroundColor(App.CONTEXT.getResources().getColor(R.color.md_grey_500));
		setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

	}
}
