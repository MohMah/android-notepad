package ir.cafebazaar.notepad.activities.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import ir.cafebazaar.notepad.R;
import ir.cafebazaar.notepad.models.Note;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder
public class DrawingActivity extends AppCompatActivity{
	private static final String TAG = "DrawingActivity";
	@Extra
	Note note;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		DrawingActivityIntentBuilder.inject(getIntent(), this);
		ButterKnife.bind(this);
	}
}