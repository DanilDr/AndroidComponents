package com.danildr.cacheimageview;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class GetImageByUrl extends AsyncTask<String, Integer, Bitmap> {
	private Bitmap inputImage = null;
	
	@Override
	protected Bitmap doInBackground(String... params) {
		String urlstring = params[0];
		try {
			inputImage = BitmapFactory.decodeStream((InputStream)new URL(urlstring).getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputImage;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
    }

}
