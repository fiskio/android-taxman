package com.luckybrews.taxman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableActivity extends Activity {
	
	public static TableActivity instance;
	
	private final static String TAG= "TableActivity";
	
	private final static String color_light_yellow = "#FFF0C1";
	private final static String color_dark_yellow = "#FFE082";
	private final static String color_light_orange = "#E55E21";
	private final static String color_dark_orange = "#D54E21";
	private final static String color_light_pink = "#F5D0BF";
	private final static String color_dark_pink = "#ECB69D";
	private final static String color_bright_yellow = "#FFCC00";
	private final static String color_dark_purple = "#994488";
	private final static String color_light_purple = "#BB66AA";
	private final static String color_light_blue = "#42A7DA";
	private final static String color_dark_blue = "#3399CC";
	
	public boolean showPercentage = true;
	public boolean showYear = true;
	public boolean showMonth = true;
	public boolean showWeek = false;
	public boolean show4Weeks = false;
	public boolean show2Weeks = false;
	public boolean showDay = false;
	
	private ProgressDialog progress;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        instance = this;
       
        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        
        PostDataTask foo = new PostDataTask();
        foo.execute(new Void[]{});
    	
    }
    
    public String[][] parseTable(String data) {
    	
    	String[][] ret = new String[8][13]; 
    	
    	// FETCH TABLE
    	Document doc = null;
		try {
			doc = Jsoup.parse(data);
			
		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage());
			e.printStackTrace();
		}
		
 		String title = doc.title();
		Log.d(TAG, "TITLE: " + title);	
		
		Element table = doc.getElementById("calculator-results");
	
		// PARSE TABLE
	
		TableLayout tableView = (TableLayout) findViewById(R.id.table);
		
		Elements trs = table.select("tr");	
		int row_idx = 0;
		for (Element one : trs) {
			row_idx++;
			//Log.d(TAG, "TR: " + idx);
			TableRow row = new TableRow(this);
			row.setId(row_idx);
			Elements tds = one.select("td");
			for (Element bar : tds) {
				Log.d(TAG, "TD: " + bar.text());
				// ADD TextView
				TextView tw = new TextView(this);
				tw.setText(bar.text());
				tw.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				tw.setPadding(10, 5, 10, 5);
				tw.setGravity(Gravity.RIGHT);
				row.addView(tw);
				
			}
			if (row.getChildCount() < 2)
				continue;
			
			// color row
			String color_scheme = chooseColor(row);
			Log.d(TAG, "COLOR: " + color_scheme);
			colorRow(row, color_scheme);
			tableView.addView(row);
			Log.d(TAG, "");
		}
		
		// Hide unwanted columns
		hideColumns();
		
		return ret;
    }
    
    // choose color for cell
    private String chooseColor(TableRow row) {
    	
    	TextView tw = (TextView) row.getChildAt(0);
    	if (tw == null)
    		Log.d(TAG, "NULL: " + row.getId());
    	String str = tw.getText().toString();
    	
    	// ORANGE
    	if (str.equals("Tax due"))  return "orange";
    	
    	// PINK
    	if (str.contains("rate"))  return "pink";
    	
    	// BRIGHT YELLOW
    	if (str.equals("Net Wage"))  return "bright_yellow";
    	
    	// PURPLE
    	if (str.equals("Childcare Vouchers"))  return "purple";
    	
    	// BLUE
    	if (str.equals("Student Loan"))  return "blue";
    		
    	// YELLOW
    	return "yellow";
    }
    
    // choose the color of the row
    void colorRow(TableRow row, String color) {
    	
    	for (int i=0; i<row.getChildCount(); i++) {
    		TextView tw = (TextView) row.getChildAt(i);
    		// ORANGE
    		if (color.equals("orange")) {
    			if (i%2==0) 
    				tw.setBackgroundColor(Color.parseColor(color_light_orange));
    			else
    				tw.setBackgroundColor(Color.parseColor(color_dark_orange));
    		}
    		// PINK
    		if (color.equals("pink")) {
    			if (i%2==0) 
    				tw.setBackgroundColor(Color.parseColor(color_light_pink));
    			else
    				tw.setBackgroundColor(Color.parseColor(color_dark_pink));
    		}
    		// BRIGHT YELLOW
    		if (color.equals("bright_yellow")) {
    			tw.setBackgroundColor(Color.parseColor(color_bright_yellow));
    			tw.setTypeface(null, Typeface.BOLD);
    		}
    		// PURPLE
    		if (color.equals("purple")) {
    			if (i%2==0) 
    				tw.setBackgroundColor(Color.parseColor(color_light_purple));
    			else
    				tw.setBackgroundColor(Color.parseColor(color_dark_purple));
    		}
    		// BLUE
    		if (color.equals("blue")) {
    			if (i%2==0) 
    				tw.setBackgroundColor(Color.parseColor(color_light_blue));
    			else
    				tw.setBackgroundColor(Color.parseColor(color_dark_blue));
    		}
    		// YELLOW
    		if (color.equals("yellow")) {
    			if (i%2==0) 
    				tw.setBackgroundColor(Color.parseColor(color_light_yellow));
    			else
    				tw.setBackgroundColor(Color.parseColor(color_dark_yellow));
    		}
    	}
    }
     
    void hideColumns() {
    	
    	TableLayout tableView = (TableLayout) findViewById(R.id.table);
    	
    	tableView.setColumnCollapsed(1, !showPercentage);
    	tableView.setColumnCollapsed(2, !showYear);
    	tableView.setColumnCollapsed(3, !showMonth);
    	tableView.setColumnCollapsed(4, !showWeek);
    	tableView.setColumnCollapsed(5, !show4Weeks);
    	tableView.setColumnCollapsed(6, !show2Weeks);
    	tableView.setColumnCollapsed(7, !showDay);
    	
    	Log.d(TAG, "hideColumns()");
    }
 
    // post data from the hash table
    public String postData() {
    	
    	String data = null;
    	
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.listentotaxman.com/index.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            
            for (String key : MainActivity.params.keySet()) {
            	String value = MainActivity.params.get(key);
            	nameValuePairs.add(new BasicNameValuePair(key, value));
            }
            /*
            nameValuePairs.add(new BasicNameValuePair("c", "1"));
            nameValuePairs.add(new BasicNameValuePair("yr", "2012"));
            nameValuePairs.add(new BasicNameValuePair("age", "0"));
            nameValuePairs.add(new BasicNameValuePair("add", "0"));
            nameValuePairs.add(new BasicNameValuePair("code", ""));
            nameValuePairs.add(new BasicNameValuePair("pension", "0"));
            nameValuePairs.add(new BasicNameValuePair("childcare", "0"));
            nameValuePairs.add(new BasicNameValuePair("childcare_freq", "12"));
            nameValuePairs.add(new BasicNameValuePair("time", "1"));
            nameValuePairs.add(new BasicNameValuePair("ingr", "28000"));
            nameValuePairs.add(new BasicNameValuePair("email", ""));
            nameValuePairs.add(new BasicNameValuePair("calculate", "Calculate"));
            nameValuePairs.add(new BasicNameValuePair("vw[]", "yr"));
            nameValuePairs.add(new BasicNameValuePair("vw[]", "mth"));
            nameValuePairs.add(new BasicNameValuePair("vw[]", "wk"));
       	    */
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            
            data = new BasicResponseHandler().handleResponse(response);     
            
            //w.loadDataWithBaseURL(httppost.getURI().toString(), data, "text/html", HTTP.UTF_8, null);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
       
        return data;
    } 
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	 
	    switch (item.getItemId()) {

	    case R.id.choose_columns:
	    	Intent foo = new Intent(this, ChooseColumnsActivity.class);
	    	startActivity(foo);
	    	break;
	    }
	    return true;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_table, menu);
        return true;
    }
    
    public boolean onPrepareOptionsMenu(Menu menu){
        boolean result = super.onPrepareOptionsMenu(menu);
      
        return result;
    }
    
    
    // async task: post the data to server and gets response
    private class PostDataTask extends AsyncTask<Void, Void, String> {
    	
        @Override
        protected String doInBackground(Void... params) {
        	return postData();
        }

        @Override
        protected void onPostExecute(String result) {
     
        	if (result != null)
            	parseTable(result);
     
        	progress.dismiss();
        	
        	TableLayout tab = (TableLayout) findViewById(R.id.table);
        	tab.setVisibility(TableLayout.VISIBLE);
        }
      }
}
