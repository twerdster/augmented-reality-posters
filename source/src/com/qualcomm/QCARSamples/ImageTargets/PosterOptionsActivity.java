
package com.qualcomm.QCARSamples.ImageTargets;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

// PROJECT_TAG:
// This class implements the poster options activity.
// It shows the posters information and it's extended details (web view) if available.
public class PosterOptionsActivity extends Activity implements OnClickListener {
	private PosterData poster;
	static private ContentGetter cg;
	private WebView formatedInfo;
	private ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poster_options);
		Bundle extras = getIntent().getExtras();
		poster = (PosterData) extras.get("PosterData");
		
		cg = new ContentGetter(this,2);
		
		TextView title = (TextView) findViewById(R.id.poster_activate_title);
		TextView names = (TextView) findViewById(R.id.poster_activate_names);
		TableRow wvControl = (TableRow) findViewById(R.id.tableRow3);
		formatedInfo = (WebView)findViewById(R.id.poster_activate_webView);
		Button homeButton = (Button) findViewById(R.id.webViewHomeButton);
		Button backButton = (Button) findViewById(R.id.webViewBackButton);
		Button fwdButton = (Button) findViewById(R.id.webViewForwardButton);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		Button goButton = (Button) findViewById(R.id.go_button);
		
		title.setText(poster.getTitle());
		names.setText(poster.getNames());
		
		/* 
		  	Three options for the web view:
			if poster.website==NA then show nothing
			else if poster.website==SERVER then load the web content located in the poster's directory on the server
			else load the web address which is poster.website 
		*/
		if (poster.getWebSite().equalsIgnoreCase("NA")==true)
		{
			formatedInfo.setVisibility(WebView.GONE);
			wvControl.setVisibility(TableRow.GONE);
		}
		else if (poster.getWebSite().equalsIgnoreCase("SERVER")==true)
		{
			String url = this.getString(R.string.webServerUrl) + "p" + poster.getId() + "/www/";
			formatedInfo.setVisibility(WebView.VISIBLE);
			wvControl.setVisibility(TableRow.VISIBLE);
			formatedInfo.setWebViewClient(new posterInfoWebViewClient());
			formatedInfo.loadUrl(url);
		}
		else
		{
			formatedInfo.setVisibility(WebView.VISIBLE);
			wvControl.setVisibility(TableRow.VISIBLE);
			formatedInfo.setWebViewClient(new posterInfoWebViewClient());
			formatedInfo.loadUrl(poster.getWebSite());		
		}
		
		pb.setMax(100);
		
		homeButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
		fwdButton.setOnClickListener(this);
		goButton.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()){
		case R.id.go_button:
			boolean picsToDownload[] = {true,true};
			try 
			{
				int id = poster.getId();
				pb.setProgress(0);
				pb.invalidate();
				cg.DownloadPosterPics(id,picsToDownload);
				do 
				{
					pb.setProgress(cg.progressPercent);
				}
				while (cg.progressPercent < 100);
				pb.setProgress(100);
			} 
			catch (ClientProtocolException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			intent = new Intent(this,ImageTargets.class);
			intent.putExtra("PicsToShow",picsToDownload);
			startActivity(intent);
			break;
		case R.id.webViewBackButton:
			if (formatedInfo.canGoBack())
			{
				formatedInfo.goBack();
			}
			break;	
		case R.id.webViewForwardButton:
			if (formatedInfo.canGoForward())
			{
				formatedInfo.goForward();
			}
			break;	
		case R.id.webViewHomeButton:
			if (poster.getWebSite().equalsIgnoreCase("SERVER")==true)
			{
				String url = this.getString(R.string.webServerUrl) + "p" + poster.getId() + "/www/";
				formatedInfo.loadUrl(url);
			}
			else
			{
				formatedInfo.loadUrl(poster.getWebSite());		
			}
			break;
		default:
		}
	}	
	
	private class posterInfoWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}		
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		formatedInfo.clearView();
	}


}
