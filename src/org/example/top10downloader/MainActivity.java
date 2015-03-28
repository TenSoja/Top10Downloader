package org.example.top10downloader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	Button btnParse;
	ListView listApps;
	String xmlData;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnParse = (Button) findViewById(R.id.btnParse);
		listApps = (ListView) findViewById(R.id.listApps);
		
		btnParse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseApplications parse = new ParseApplications(xmlData);
				boolean operationStatus = parse.process();
				if(operationStatus) {
					ArrayList<Application> allApps = parse.getApplications();
					
					ArrayAdapter<Application> adapter = new ArrayAdapter<Application>(MainActivity.this, R.layout.list_item, allApps);
					listApps.setVisibility(listApps.VISIBLE);
					listApps.setAdapter(adapter);
				} else {
					Log.d("MainActivity", "Error parsing file");
				}
				
				
			}
		});
		
		new DownloadData().execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class DownloadData extends AsyncTask<String, Void, String> {
		
		String myXmlData;

		@Override
		protected String doInBackground(String... urls) {
			try {
				myXmlData = downloadXML(urls[0]);
			} catch(IOException e) {
				return "Unable to download XML file.";
			}
			return "";
		}
		
		protected void onPostExecute(String result) {
			Log.d("OnPostExecute", myXmlData);
			xmlData = myXmlData;
		}

		private String downloadXML(String theUrl) throws IOException {
			int BUFFER_SIZE = 2000;
			InputStream is = null;
			
			String xmlContents = "";
			try {
				URL url = new URL(theUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(15000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				int response = conn.getResponseCode();
				Log.d("DownloadXML", "The response returned is " + response);
				is = conn.getInputStream();
				
				InputStreamReader isr = new InputStreamReader(is);
				int charRead;
				char[] inputBuffer = new char[BUFFER_SIZE];
				try {
					while((charRead = isr.read(inputBuffer))>0) {
						String readString = String.copyValueOf(inputBuffer, 0, charRead);
						xmlContents += readString;
						inputBuffer = new char[BUFFER_SIZE];
					}
					return xmlContents;
				} catch(IOException e) {
					e.printStackTrace();
					return null;
				}
				
			} finally {
				if(is != null)
					is.close();
			}
		}
	}
}
