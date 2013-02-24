package com.luckybrews.taxman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class ChooseColumnsActivity extends Activity {

	private final static String TAG = "ChooseColumnsActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_columns);
		setTitle("Choose Columns");
		
		CheckBox aux;
		aux = (CheckBox) findViewById(R.id.checkPercentage);
		aux.setChecked(TableActivity.instance.showPercentage);
		aux.setOnClickListener(new CheckBoxListener());
		
		aux = (CheckBox) findViewById(R.id.checkYear);
		aux.setChecked(TableActivity.instance.showYear);
		aux.setOnClickListener(new CheckBoxListener());
		
		aux = (CheckBox) findViewById(R.id.checkMonth);
		aux.setChecked(TableActivity.instance.showMonth);
		aux.setOnClickListener(new CheckBoxListener());
		
		aux = (CheckBox) findViewById(R.id.checkWeek);
		aux.setChecked(TableActivity.instance.showWeek);
		aux.setOnClickListener(new CheckBoxListener());
		
		aux = (CheckBox) findViewById(R.id.check4weeks);
		aux.setChecked(TableActivity.instance.show4Weeks);
		aux.setOnClickListener(new CheckBoxListener());
		
		aux = (CheckBox) findViewById(R.id.check2weeks);
		aux.setChecked(TableActivity.instance.show2Weeks);
		aux.setOnClickListener(new CheckBoxListener());
		
		aux = (CheckBox) findViewById(R.id.checkDay);
		aux.setChecked(TableActivity.instance.showDay);
		aux.setOnClickListener(new CheckBoxListener());
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		TableActivity.instance.hideColumns();
		Log.d(TAG, "onDestroy()");
	}
	
	class CheckBoxListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.checkPercentage:
				TableActivity.instance.showPercentage = !TableActivity.instance.showPercentage;
				break;
			case R.id.checkYear:
				TableActivity.instance.showYear = !TableActivity.instance.showYear;
				break;
			case R.id.checkMonth:
				TableActivity.instance.showMonth = !TableActivity.instance.showMonth;
				break;
			case R.id.checkWeek:
				TableActivity.instance.showWeek = !TableActivity.instance.showWeek;
				break;
			case R.id.check4weeks:
				TableActivity.instance.show4Weeks = !TableActivity.instance.show4Weeks;
				break;
			case R.id.check2weeks:
				TableActivity.instance.show2Weeks = !TableActivity.instance.show2Weeks;
				break;
			case R.id.checkDay:
				TableActivity.instance.showDay = !TableActivity.instance.showDay;
				break;
			}	
		}
	}	
}
