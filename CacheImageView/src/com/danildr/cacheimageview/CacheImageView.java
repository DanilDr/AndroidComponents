package com.danildr.cacheimageview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CacheImageView extends ImageView {
	private Context context;
	private String[] packageName;
	private String appName;
	private String imageURL = null;
	private String STORAGE_DIR;
	private String CACHE_DIR;
	private String EXTERNAL_DIR;
	private Object mDiskCacheLock = new Object();
	public String imageKey;
	private CompressFormat compressBitmap = CompressFormat.PNG;
	
	public CacheImageView(Context context) {
		super(context);
		this.context = context;
		this.setImageResource(R.drawable.hourglass);
		initImage();
	}
	
	public CacheImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initImage();
		TypedArray a;
		// инициализация картинки
		try {
			a = context.obtainStyledAttributes(attrs, new int[] {R.attr.imageurl});
			imageURL = a.getString(0);
			setImage();
			a.recycle();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		// проверка папки для кэширования
	}
	
	private void initImage() {
		this.setImageResource(R.drawable.hourglass);
		packageName = context.getApplicationContext().getPackageName().split("[.]");
		appName = packageName[packageName.length - 1];
		EXTERNAL_DIR = Environment.getExternalStorageDirectory() + File.separator + appName;
		File storage = new File(EXTERNAL_DIR);
		if (!storage.exists() && !storage.mkdirs()) {
			CACHE_DIR = context.getFilesDir().getPath() + File.separator + "cache";
		} else {
			CACHE_DIR = EXTERNAL_DIR;
		}
	}
	
	private void addBitmapToCache(String key, Bitmap bitmap) {
		FileOutputStream imageFile;
		try {
			imageFile = new FileOutputStream(CACHE_DIR + File.separator + key);
			bitmap.compress(compressBitmap, 80, imageFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Bitmap getBitmapFromStorage(String key) {
		Bitmap bitmap = BitmapFactory.decodeFile(STORAGE_DIR + File.separator + key);
		return bitmap;
	}
	
	protected Boolean checkImageExist(String key) {
		File imageFile = new File(CACHE_DIR + File.separator + key);
		if (imageFile.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	private String getImageKey() {
		String imageKey = null;
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(imageURL.getBytes());
			byte[] digest = m.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			imageKey = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return imageKey;
	}
	
	private class AsyncSetImage extends AsyncTask<Object, Integer, Bitmap> {
		private CacheImageView imageView;
		
		public AsyncSetImage(CacheImageView imageView) {
			super();
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(Object... params) {
			String imageKey = (String) params[0];
			String imageURL = (String) params[1];
			String CACHE_DIR = (String) params[2];
			Bitmap image = null;
			if (!checkImageExist(imageKey)) {
				URL url;
				try {
					url = new URL(imageURL);
					image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					addBitmapToCache(imageKey, image);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
					image = BitmapFactory.decodeFile(CACHE_DIR + File.separator + imageKey);
			}
			return image;
		}
		
		@Override
		protected void onPostExecute(Bitmap image) {
			imageView.setImageBitmap(image);
		}
		
	}
	
	private void setImage() throws InterruptedException, ExecutionException, TimeoutException {
		if (imageURL != null) {
			imageKey = getImageKey();
			new AsyncSetImage(this).execute(imageKey, imageURL, CACHE_DIR);
		}
	}
	
	public void setImageUrl(String imageURL) {
		this.imageURL = imageURL;
		try {
			setImage();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	private boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	public String getAbsolutePath() {
		File cachedir = new File(CACHE_DIR);
		File cacheImage = new File(cachedir, imageKey);
		return cacheImage.getPath();
	}
	
	public void setCompressFormat(String compressFormat) {
		switch (compressFormat) {
			case "jpeg":
				compressBitmap = CompressFormat.JPEG;
				break;
			case "png":
				compressBitmap = CompressFormat.PNG;
				break;
			default:
				compressBitmap = CompressFormat.PNG;
				break;
		}
	}
	
}
