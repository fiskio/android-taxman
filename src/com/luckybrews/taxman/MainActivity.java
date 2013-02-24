package com.luckybrews.taxman;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private final static String TAG = "MainActivity";

	Context mContext;
	public static Map<String, String> params = new HashMap<String, String>();
	
	String[] arr_fields = new String[] {"ingr", "add", "childcare", "code", "pension"};
	String[] arr_childcare = new String[]{"12", "53"};
	String[] arr_gross = new String[]{"1", "12", "13", "26", "52", "260", "1950"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;

		// default "" 
		for (String str : arr_fields) { params.put(str, ""); }
		
		// tax year spinner
		Spinner yearSpin = (Spinner) findViewById(R.id.spinnerTY);
		yearSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        String item = (String) parent.getItemAtPosition(pos);
		        params.put("yr", item.substring(0,4));
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		// married checkbox
		final CheckBox married = (CheckBox) findViewById(R.id.checkMarried);
		married.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (married.isChecked())
					params.put("married", "y");
			}
		});
		
		// blind checkbox
		final CheckBox blind = (CheckBox) findViewById(R.id.checkBlind);
		blind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (blind.isChecked())
					params.put("blind", "y");
			}
		});
		
		// NONI checkbox
		final CheckBox noni = (CheckBox) findViewById(R.id.checkNONI);
		noni.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (noni.isChecked())
					params.put("exNI", "y");
			}
		});
		
		// student checkbox
		final CheckBox student = (CheckBox) findViewById(R.id.checkSL);
		student.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (student.isChecked())
					params.put("student", "y");
			}
		});
		
		// age spinner
		Spinner ageSpin = (Spinner) findViewById(R.id.spinnerAge);
		ageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        params.put("age", ""+pos);
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		// childcare layout
		/*
		EditText voucher = (EditText) findViewById(R.id.textVoucher);
		voucher.setonsetOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				LinearLayout lay_voucher = (LinearLayout) findViewById(R.id.voucherHidden);
				lay_voucher.setVisibility(View.VISIBLE);	
				return true;
			}
		});
		*/
		// childcare checkbox
		final CheckBox chilcare = (CheckBox) findViewById(R.id.checkVoucher2);
		chilcare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (chilcare.isChecked())
					params.put("childcare_pre2011", "on");
			}
		});
		
		// childcare spinner
		Spinner childcareSpin = (Spinner) findViewById(R.id.spinnerVoucher);
		childcareSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				params.put("childcare_freq", arr_childcare[pos]);
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		// gross spinner
		Spinner grossSpin = (Spinner) findViewById(R.id.spinnerGross);
		grossSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				params.put("time", arr_gross[pos]);
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		// GUIDE
		// every question mark brings the user to the associated section in the guide
		ImageButton button;
		
		button = (ImageButton) findViewById(R.id.qTaxYear); // TAX YEARS
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "year");
				mContext.startActivity(guideIntent);
			}
		});
		button = (ImageButton) findViewById(R.id.qMStatus); // MARRIED
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "married");
				mContext.startActivity(guideIntent);
			}
		});
		button = (ImageButton) findViewById(R.id.qStudentLoad); // STUDENT LOAN
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "student");
				mContext.startActivity(guideIntent);
			}
		});
		button = (ImageButton) findViewById(R.id.qAge); // AGE	
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "age");
				mContext.startActivity(guideIntent);
			}
		});
		button = (ImageButton) findViewById(R.id.qAllowances); // ALLOWANCES
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "allowances");
				mContext.startActivity(guideIntent);
			}
		});
		button = (ImageButton) findViewById(R.id.qTaxCode); // TAX CODE
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "code");
				mContext.startActivity(guideIntent);
			}
		});
		button = (ImageButton) findViewById(R.id.qPension); // PENSION
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "pension");
				mContext.startActivity(guideIntent);
			}
		});
		button = (ImageButton) findViewById(R.id.qVouchers); // CHILDCARE
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "childcare");
				mContext.startActivity(guideIntent);
			}
		});
		button = (ImageButton) findViewById(R.id.qVouchers2); // CHILDCARE 2
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "childcare");
				mContext.startActivity(guideIntent);
			}
		});
		button = (ImageButton) findViewById(R.id.qGross); // GROSS
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent guideIntent = new Intent(mContext, GuideActivity.class);
				guideIntent.putExtra("url", "gross");
				mContext.startActivity(guideIntent);
			}
		});
	
		// calculate button
		Button calcButton = (Button) findViewById(R.id.calcButton);
		calcButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				addFields();
				
				if (!isNetworkAvailable()) {
					Toast.makeText(mContext, "Please check your wireless connection and try again.", Toast.LENGTH_SHORT).show();
				} else {
					Intent showTable = new Intent(mContext, TableActivity.class);
					Log.d(TAG, "PARAMS: " + params.toString());
					startActivity(showTable);
				}
				
			}
		});
	}
	
	public void addFields() {
		
		// tax code edit
		EditText code = (EditText) findViewById(R.id.textTaxCode);
		String str_code = code.getText().toString();
		Log.d(TAG, "CODE: " + str_code);
		params.put("code", str_code);
		
		// allowances edit
		EditText allow = (EditText) findViewById(R.id.textDeduction);
		String str_allow = allow.getText().toString();
		Log.d(TAG, "ALLOW: " + str_allow);
		params.put("add", str_allow);
		
		// pensidon edit
		EditText pension = (EditText) findViewById(R.id.textPension);
		String str_pension = pension.getText().toString();
		Log.d(TAG, "PENSION: " + str_pension);
		params.put("pension", str_pension);
		
		// voucher edit
		EditText voucher = (EditText) findViewById(R.id.textVoucher);
		String str_voucher = voucher.getText().toString();
		Log.d(TAG, "VOUCHER: " + str_voucher);
		params.put("childcare", str_voucher);
		
		// gross edit
		EditText gross = (EditText) findViewById(R.id.textGross);
		String str_gross = gross.getText().toString();
		Log.d(TAG, "GROSS: " + str_gross);
		params.put("ingr", str_gross);
	}
	
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}	
	
}
