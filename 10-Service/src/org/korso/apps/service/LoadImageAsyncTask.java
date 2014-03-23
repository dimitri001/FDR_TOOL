package org.korso.apps.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


//The AssyncTask is going to receive as parmeters url (in our case a String)
//In the progress is the percentage of donwloading or a string(in our case void0)
//The third one is the thing that we pass when the progress is finished (in our case a bitmap) 
public class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

	
	private ImageView imageView;

	//We need the imageView throught the constructor
	public LoadImageAsyncTask(ImageView imageView){
		this.imageView = imageView;
		
	}
	
	//We have a list of parameters that are going to be Strings, that could be 0, 1 o many
	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		String urlString = params[0];
		
		try{
			URL url = new URL(urlString); //we make an url object from the string
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();// we create an http connection
			connection.connect();// we just get connected, we sent the http connection 
			InputStream input = connection.getInputStream();//
			bitmap = BitmapFactory.decodeStream(input);//we get the bitmap object
			//this.isCancelled();//if this is cancel we close the soccket, we close the files... etc
			
			connection.disconnect();//we just disconnect 
			
		}catch(Exception e){
			bitmap= null;
			e.printStackTrace(); // if there is an error we catch it here
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);//we load the new image
		imageView.setImageBitmap(result);
	}

	@Override
	protected void onPreExecute() {
		imageView.setImageBitmap(null);//We deleted the previous image
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	
}
