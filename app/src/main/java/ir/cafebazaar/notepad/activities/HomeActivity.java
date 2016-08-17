package ir.cafebazaar.notepad.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.cafebazaar.notepad.R;

/**
 * Created by MohMah on 8/17/2016.
 */
public class HomeActivity extends AppCompatActivity{
	@BindView(R.id.toolbar) Toolbar toolbar;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
	}
}
