package org.example.top10downloader;

import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
public class ParseApplications {
	
	private String data;
	private ArrayList<Application> applications;
	
	public ParseApplications(String xmlData) {
		super();
		this.data = xmlData;
		applications = new ArrayList<Application>();
	}

	public ArrayList<Application> getApplications() {
		return applications;
	}
	
	public boolean process() {
		
		boolean operationStatus = true;
		Application currentRecord = null;
		boolean inEntry = false;
		String textValue = "";
		String tagAttributeHeight = "";
		boolean is53 = false;
		
		try {
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			xpp.setInput(new StringReader(this.data));
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				
				String tagname = xpp.getName();
				if(eventType == XmlPullParser.START_TAG) {
					if(tagname.equalsIgnoreCase("image")){
						tagAttributeHeight = xpp.getAttributeValue(null, "height");
							if(tagAttributeHeight.equals("53")) {
								is53 = true;
								
							}
					}
					if(tagname.equalsIgnoreCase("entry")) {
						inEntry = true;
						currentRecord = new Application();
					}
					
				} else if(eventType == XmlPullParser.TEXT) {
					textValue = xpp.getText();
					
				} else if(eventType == XmlPullParser.END_TAG) {
					if(inEntry) {
						if(tagname.equalsIgnoreCase("entry")) {
							applications.add(currentRecord);
							inEntry = false;
						}
						if(tagname.equalsIgnoreCase("image")) {
							if(is53){
								currentRecord.setImage(textValue);
								is53 = false;
							}
						}	else if(tagname.equalsIgnoreCase("name")) {
							currentRecord.setName(textValue);
						} else if(tagname.equalsIgnoreCase("artist")) {
							currentRecord.setArtist(textValue);
						} else if(tagname.equalsIgnoreCase("releaseDate")) {
							currentRecord.setReleaseDate(textValue);
						}
					}
				}
				
				eventType = xpp.next();
			}
		} catch(Exception e) {
			e.printStackTrace();
			operationStatus = false;
		}
		
		for(Application app : applications) {
			Log.d("LOG" , "*****************");
			Log.d("LOG" , app.getImage());
			Log.d("LOG" , app.getName());
			Log.d("LOG" , app.getArtist());
			Log.d("LOG" , app.getReleaseDate());

		}
		
		return operationStatus;
	}
	
	
	
}
