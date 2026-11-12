package com.qualcomm.QCARSamples.ImageTargets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.NetworkOnMainThreadException;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

//PROJECT_TAG: 
/*
 * A helper class that gathers all the methods required for
 * downloading files from the remote web server and for
 * parsing the index file containing the posters' data.
 */
public class ContentGetter {
	private String webServerUrl;
	private String localDataPath;
	private String indexFileName;
	private String picFileNamePrefix;
	private String picFileNamePostfix;
	
	private Context 	mContext;
	private FileInfo 	indexFile;
	private FileInfo[] 	picFiles;
	
	public static boolean isFilesCreated = false;
	public boolean isStillDownloading = true;
	public int currentPoster = -1;
	public int numOfPics = 2;
	public int progressPercent = 0;
	
	//PROJECT_TAG:
	/*
	 * The class constructor - create fileInfo instances for every picture
	 */
	public ContentGetter(Context context,int numOfPics) {		
		try 
		{
			this.mContext = context;
			this.numOfPics = numOfPics;
			
			this.webServerUrl = mContext.getString(R.string.webServerUrl);
			this.localDataPath = mContext.getString(R.string.localDataPath);
			this.indexFileName = mContext.getString(R.string.indexFileName);
			this.picFileNamePrefix = mContext.getString(R.string.picFileNamePrefix);
			this.picFileNamePostfix = mContext.getString(R.string.picFileNamePostfix);
							
			/* Create the index file */
			this.indexFile = new FileInfo(localDataPath,indexFileName,true);
			
			/* Create the picture files */
			this.picFiles = new FileInfo[numOfPics];
			for (int i=0 ; i< numOfPics ; i++)
			{
				this.picFiles[i] = new FileInfo(localDataPath,picFileNamePrefix+Integer.toString(i)+picFileNamePostfix,true);
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//PROJECT_TAG:
	/*
	 * A private class that is used for basic files operations
	 */
	private class FileInfo {
		/* class constructor - creates a new file descriptor */
		public FileInfo(String path, String name,boolean leaveIfExists) throws IOException {
			super();
			this.path = path;
			this.name = name;
			this.fd = new File(path + name);			
		}
		
		/* whether the file already exists in the file system or not */
		public boolean Exists()
		{
			return this.fd.exists();
		}
		
		/* if the file is already created then delete it */
		public boolean Clear()
		{
			if (this.fd.exists()) 
			{
				this.fd.delete();
				return true;
			}
			return false;
		}
		
		/* create a new file in the filesystem */
		public boolean Create() throws IOException {
			if (this.fd.createNewFile() == false) 
			{
				Log.d("FileInfo.createNewFile", "Failed to create " + path + name);
				return false;
			}
			return true;
		}
		
		public String 	name;
		public String 	path;
		public File 	fd;
		public boolean 	isDownloaded = false;
	}
	
	//PROJECT_TAG:
	/*
	 * An asynchronous task for downloading files in the background
	 * and not on the main UI activity
	 */
	private class DownloadWebPageTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isStillDownloading = true;
			progressPercent = 0;
		}
		
		@Override
		protected String doInBackground(String... urls) {
			for (int i=0 ; i<urls.length ; i++)
			{
				String delims = "[/]+";
				String[] tokens = urls[i].split(delims);
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpGetTitlesFile = new HttpGet(webServerUrl + urls[i]);
				HttpResponse response = null;
				
				try {
					response = httpclient.execute(httpGetTitlesFile);
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						FileOutputStream fos = new FileOutputStream(localDataPath + tokens[tokens.length-1]);
						entity.writeTo(fos);
						fos.close();
						publishProgress((int) ((i+1)/urls.length*100));
					}
					else
					{
						DebugLog.LOGE("DownloadWebPageTask.doInBackground - response.getEntity() returned null");
					}
				} catch (ClientProtocolException e) {
					DebugLog.LOGE("DownloadWebPageTask.doInBackground - ClientProtocolException exception");
					e.printStackTrace();
				} catch (IOException e) {
					DebugLog.LOGE("DownloadWebPageTask.doInBackground - IOException exception");
					e.printStackTrace();
					return "Unable to resolve host";
				} catch (NetworkOnMainThreadException e) {
					DebugLog.LOGE("DownloadWebPageTask.doInBackground - NetworkOnMainThreadException exception");
					e.printStackTrace();
				}

			}
			isStillDownloading = false;
			progressPercent = 100;
			publishProgress(100);
			return "OK";
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
	    	 progressPercent = progress[0];
	     }
		
	     @Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			isStillDownloading = false;
		}
	}
	
	//PROJECT_TAG:
	/*
	 * The method download the index file from the web server
	 * and parses it into posters data for displaying them in the list.
	 * @return array of PosterData objects
	 */
	public PosterData[] parseIndexFile() throws ClientProtocolException, IOException {
		try 
		{
			if (indexFile.isDownloaded == false)
			{
				/* Create the index file */
				if (indexFile.Exists())
				{
					indexFile.Clear();
				}
				indexFile.Create();
				
				/* Download index file */
				DownloadWebPageTask task = new DownloadWebPageTask();
				task.execute(new String[] {indexFile.name});
				if (task.isCancelled()==true)
				{
					DebugLog.LOGE("Task was canceled");
					return null;
				}
				if (task.get() != "OK")
				{
					DebugLog.LOGE("parseIndexFile() - failed to download the file");
					return null;
				}				
				
				/* wait for download to end */
				while (isStillDownloading == true)
				{
					Thread.sleep(1000);
				}
				isStillDownloading = true;
				indexFile.isDownloaded = true;
			}
							
			/* Parse the file to a strings array (per line) */
			
			FileInputStream fstream	= new FileInputStream(indexFile.path + indexFile.name);
			DataInputStream in 		= new DataInputStream(fstream);
			BufferedReader 	br 		= new BufferedReader(new InputStreamReader(in));
			
			String titleLine,namesLine,webSiteLine;
			ArrayList<PosterData> postersList = new ArrayList<PosterData>();
			
			titleLine = br.readLine();
			if (titleLine == null)
			{
				DebugLog.LOGE("Empty index file");
				return null;
			}
			
			namesLine = br.readLine();
			webSiteLine = br.readLine();
			for (int i=0 ; titleLine != null ; i++) {
				
				PosterData pd = new PosterData(i,titleLine,namesLine,webSiteLine);
				postersList.add(pd);
				titleLine = br.readLine();
				namesLine = br.readLine();
				webSiteLine = br.readLine();
			}
			in.close();
			return postersList.toArray(new PosterData[postersList.size()]);
		} 
		catch (ArrayStoreException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
		
	}
	
	//PROJECT_TAG:
	/*
	 * The method downloads the pictures of a selected poster
	 */
	public int DownloadPosterPics(Integer posterNum,boolean[] isPicEnabled) throws ClientProtocolException, IOException {
		if (currentPoster == posterNum)
		{
			progressPercent = 100;
			return 0;
		}
		try 
		{
			/* Create the path to the pictures on the server according to the poster id */
			String[] picFilesPaths = new String[numOfPics];
			int i=0,j=0;
			for (i=0 ; i<numOfPics ; i++)
			{
				if (isPicEnabled[i]==true)
				{
					picFilesPaths[j++] = "p" + posterNum.toString() + "/" + picFileNamePrefix + Integer.toString(i) + picFileNamePostfix;
				}
			}
			
			/* Create the picture files */
			for (i=0 ; i< numOfPics ; i++)
			{
				if (picFiles[i].Exists())
				{
					picFiles[i].Clear();
				}
				picFiles[i].Create();
			}
			
			/* Download all the pics of the poster */			
			DownloadWebPageTask task = new DownloadWebPageTask();
			task.execute(picFilesPaths);
			if (task.isCancelled()==true)
			{
				DebugLog.LOGE("Task was canceled");
				return -1;
			}
			if (task.get() != "OK")
			{
				DebugLog.LOGE("DownloadPosterPics() - failed to download the file");
				return -1;
			}	
			
			currentPoster = posterNum;
		} 
		catch (ArrayStoreException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return 0;
		
	}
	
}
