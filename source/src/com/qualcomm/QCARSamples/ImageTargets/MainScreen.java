
package com.qualcomm.QCARSamples.ImageTargets;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

// PROJECT_TAG:
// This class implements the main activity of the application.
// Firstly, Internet connection is tested and then the view is showed.
public class MainScreen extends Activity implements OnClickListener {
	static final int DIALOG_NO_CONNECTION_ID = 0;
	boolean isDeviceConnected = false;	
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0,0);
        startActivity(intent);
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case DIALOG_NO_CONNECTION_ID:
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	String msg = "No internet connection is available.\n";
			msg       += "Please first make sure the device is connected and then press \"Retry\".\n";
			msg       += "Press \"Exit\" to quit.\n";
        	builder.setMessage(msg);
        	builder.setCancelable(false);
        	builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   dialog.cancel();
	        	   MainScreen.this.reload();
	           }
	       });
        	builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   MainScreen.this.finish();
	           }
	       });
        	return builder.create();
		default:
            return null;
        }
    }    
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View SelectPosterButton = findViewById(R.id.SelectPosterIdButton);
        SelectPosterButton.setOnClickListener(this);
       
        View AboutButton = findViewById(R.id.AboutIdButton);
        AboutButton.setOnClickListener(this);
        
        View ExitButton = findViewById(R.id.ExitIdButton);
        ExitButton.setOnClickListener(this);
    }
    
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
        isDeviceConnected = isOnline();
		if (isDeviceConnected == false)
		{
			showDialog(DIALOG_NO_CONNECTION_ID);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isDeviceConnected == false)
		{
			isDeviceConnected = isOnline();
		}
	}

	public void onClick(View v) {
		Intent intent;
		switch (v.getId()){
		case R.id.SelectPosterIdButton:
			intent = new Intent(this,PostersListActivity.class);
			startActivity(intent);
			break;
		case R.id.AboutIdButton:
			AboutDialog about = new AboutDialog(this);
			about.setTitle("About A.R.Posters");
			about.show();
			break;
		case R.id.ExitIdButton:
			finish();
		break;
		}
	}
}