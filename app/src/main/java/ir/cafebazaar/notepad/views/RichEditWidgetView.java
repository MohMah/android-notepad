package ir.cafebazaar.notepad.Views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.commonsware.cwac.richedit.Effect;
import com.commonsware.cwac.richedit.RichEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.cafebazaar.notepad.App;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.utils.Utils;

import static com.commonsware.cwac.richedit.RichEditText.BOLD;
import static com.commonsware.cwac.richedit.RichEditText.ITALIC;
import static com.commonsware.cwac.richedit.RichEditText.STRIKETHROUGH;
import static com.commonsware.cwac.richedit.RichEditText.SUBSCRIPT;
import static com.commonsware.cwac.richedit.RichEditText.SUPERSCRIPT;
import static com.commonsware.cwac.richedit.RichEditText.UNDERLINE;

/**
 * Created by MohMah on 8/23/2016.
 */
public class RichEditWidgetView extends HorizontalScrollView{
	@BindView(R.id.linear_layout) LinearLayout linearLayout;
	private static final String TAG = "RichEditWidgetView";
	private boolean isShown = false;
	private RichEditText richEditText;
	private OnTouchListener onTouchListener = new OnTouchListener();
	private OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener();
	private RichEditText.OnSelectionChangedListener selectionListener = new OnSelectionChangedListener();
	private final OnClickListener actionClickListener = new OnClickListener(){
		@Override public void onClick(View v){
			if (v instanceof ImageButton){
				ImageButton action = (ImageButton) v;
				Effect effect = (Effect) action.getTag();
				if (lastSelectionEffects.contains(effect)){
					richEditText.applyEffect(effect, false);
					setActionOff(action);
					lastSelectionEffects.remove(effect);
				}else{
					richEditText.applyEffect(effect, true);
					setActionOn(action);
					lastSelectionEffects.add(effect);
				}
			}
		}
	};

	Effect[] supportedEffects = new Effect[] {
			BOLD,
			ITALIC,
			UNDERLINE,
			STRIKETHROUGH,
			SUBSCRIPT,
			SUPERSCRIPT
	};
	int[] effectIcons = new int[] {
			R.drawable.ic_format_bold_white_24dp,
			R.drawable.ic_format_italic_white_24dp,
			R.drawable.ic_format_underlined_white_24dp,
			R.drawable.ic_format_strikethrough_white_24dp,
			R.drawable.ic_format_subscript,
			R.drawable.ic_format_superscript,
	};
	//ImageButton[] actions = new ImageButton[6];

	public RichEditWidgetView(Context context){
		this(context, null);
	}

	public RichEditWidgetView(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}

	public RichEditWidgetView(Context context, AttributeSet attrs, int defStyleAttr){
		super(context, attrs, defStyleAttr);
		View view = LayoutInflater.from(context).inflate(R.layout.view_rich_edit_widget, this, true);
		ButterKnife.bind(this, view);
		setVisibility(GONE);
		for (int i = 0; i < supportedEffects.length; i++){
			LayoutInflater.from(context).inflate(R.layout.view_rich_edit_action_button, linearLayout, true);
			ImageButton action = (ImageButton) linearLayout.getChildAt(linearLayout.getChildCount() - 1);
			action.setTag(supportedEffects[i]);
			action.setImageResource(effectIcons[i]);
			action.setOnClickListener(actionClickListener);
		}
	}

	public void show(){
		if (!isShown){
			clearAnimation();
			setAlpha(0);
			setVisibility(VISIBLE);
			ObjectAnimator.ofFloat(this, "alpha", 1f).setDuration(200).start();
			isShown = true;
		}
	}

	public void hide(){
		if (isShown){
			clearAnimation();
			ObjectAnimator.ofFloat(this, "alpha", 0f).setDuration(200).start();
			postDelayed(new Runnable(){
				@Override public void run(){
					RichEditWidgetView.this.setVisibility(GONE);
					isShown = false;
				}
			}, 200);
		}
	}

	private int lastSelectionStart = -1;
	private int lastSelectionEnd = -1;
	private List<Effect<?>> lastSelectionEffects;

	public void setRichEditView(RichEditText richEditText){
		this.richEditText = richEditText;
		richEditText.setOnFocusChangeListener(onFocusChangeListener);
		richEditText.setOnSelectionChangedListener(selectionListener);
		richEditText.setOnTouchListener(onTouchListener);
	}

	private ImageButton getEffectAction(Effect effect){
		int index = -1;
		for (int i = 0; i < supportedEffects.length; i++){
			if (supportedEffects[i].equals(effect)){
				index = i;
				break;
			}
		}
		if (index == -1) return null;
		else return (ImageButton) linearLayout.getChildAt(index);
	}

	private void setActionOn(ImageButton view){
		view.setBackgroundResource(R.drawable.background_rich_edit_action_on);
		view.setColorFilter(App.CONTEXT.getResources().getColor(R.color.md_blue_grey_500));
	}

	private void setActionOff(ImageButton view){
		view.setBackgroundResource(0);
		view.setColorFilter(App.CONTEXT.getResources().getColor(R.color.md_blue_grey_300));
	}

	private class OnTouchListener implements View.OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event){
			v.onTouchEvent(event);
			if (event.getAction() == MotionEvent.ACTION_UP){
				if (lastSelectionStart != lastSelectionEnd){
					show();
					Utils.hideSoftKeyboard(v);
				}else{
					hide();
					Utils.showSoftKeyboard(v);
				}
			}
			return true;
		}
	}

	private class OnFocusChangeListener implements View.OnFocusChangeListener{
		@Override public void onFocusChange(View v, boolean hasFocus){
			if (!hasFocus) hide();
		}
	}

	private class OnSelectionChangedListener implements RichEditText.OnSelectionChangedListener{
		@Override public void onSelectionChanged(int i, int i1, List<Effect<?>> list){
			Log.e(TAG, "onSelectionChanged() called with: " + "i = [" + i + "], i1 = [" + i1 + "], list = [" + list + "]");
			lastSelectionStart = i;
			lastSelectionEnd = i1;
			if (lastSelectionEffects != null)
				for (Effect effect : lastSelectionEffects){
					setActionOff(getEffectAction(effect));
				}
			lastSelectionEffects = list;
			for (Effect effect : list){
				setActionOn(getEffectAction(effect));
			}
		}
	}
}
