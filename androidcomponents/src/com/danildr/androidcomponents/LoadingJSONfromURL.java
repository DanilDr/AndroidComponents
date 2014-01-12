package com.danildr.androidcomponents;

import org.json.JSONObject;

import android.os.AsyncTask;

public class LoadingJSONfromURL extends AsyncTask<String, Void, JSONObject> {

	@Override
	protected JSONObject doInBackground(String... params) {
		String urljson = params[0];
		JSONObject inputJSON = null;
		inputJSON = new GetJSONfromUrl(urljson).getJson();
		return inputJSON;
	}

	@Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }
}
