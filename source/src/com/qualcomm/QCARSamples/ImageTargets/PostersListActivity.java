package com.qualcomm.QCARSamples.ImageTargets;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


// PROJECT_TAG:
// This class implements the posters list activity.
// Tt populates the list items from the posterInfo array retrieved from the ContentGetter.   
public class PostersListActivity extends ListActivity {
	final private int NUM_OF_PICS = 2;
	private ContentGetter 	cg;
	private PosterData[]	posters;
	private ListView 		listView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		     
        // first, get the poster list from the server
		setContentView(R.layout.posters_list);
        cg = new ContentGetter(this,NUM_OF_PICS);
        try 
        {
        	posters = cg.parseIndexFile();
        	if (posters == null)
        	{
        		showDialog(0);
        	}
		} 
        catch (ClientProtocolException e) 
        {
			e.printStackTrace();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}

		// Use a list adapter to transform the posters array into a list view
		listView = (ListView)findViewById(android.R.id.list);
		PostersArrayAdapter postersAdapter = new PostersArrayAdapter(this, posters);
		Log.d("PostersListActivity.onCreate()","Number of elementes in the data set of the adapter is " + postersAdapter.getCount());
		listView.setAdapter(postersAdapter);
    }
	
	@Override
	protected Dialog onCreateDialog(int id) {
        switch (id) {
        default:
            // Create out AlterDialog
            Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Error. Press OK to exit.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	PostersListActivity.this.finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onCreateDialog(id);
    }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Intent getPoster = new Intent(this,PosterOptionsActivity.class);;
		getPoster.putExtra("PosterData", posters[(int)id]);
		startActivity(getPoster);		
	}
	
	public class PostersArrayAdapter extends ArrayAdapter<PosterData> {
		private final Activity context;
		private final PosterData[] posters;
		
		public PostersArrayAdapter(Activity context, PosterData[] posters) {
			super(context, R.layout.list_item, posters);
			this.context = context;
			this.posters = posters;
		}

		@Override
		public PosterData getItem(int position) {
			return posters[position];
		}
		
		// for holding the views of the outer class instead of finding them all the time
		class ViewHolder {
			public ImageView logo;
			public TextView  title;
			public TextView  names;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			
			// Recycle existing view if passed as parameter
			// This will save memory and time on Android
			View rowView = convertView;
			if (rowView == null) 
			{
				LayoutInflater inflater = context.getLayoutInflater();
				rowView = inflater.inflate(R.layout.list_item, null, true);
				holder = new ViewHolder();
				holder.title = (TextView) rowView.findViewById(R.id.title);
				holder.names = (TextView) rowView.findViewById(R.id.names);
				holder.logo = (ImageView) rowView.findViewById(R.id.logo);
				rowView.setTag(holder);
			} 
			else 
			{
				holder = (ViewHolder) rowView.getTag();
			}

			holder.title.setText(posters[position].getTitle());
			holder.names.setText("by " + posters[position].getNames());
			holder.logo.setImageResource(R.drawable.icon_poster);

			return rowView;
		}
	}

	
	
	
	
}

