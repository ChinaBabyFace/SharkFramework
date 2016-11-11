package com.soaring.io.http.net;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class SoaringParam extends JSONObject {

	public static final String FILE_NAME_TAG = "boma_file";
	private String mAppKey;

	public SoaringParam() {
		super();
	}

	public SoaringParam(JSONObject arg0, String[] arg1) throws JSONException {
		super(arg0, arg1);
	}

	public SoaringParam(String arg0) throws JSONException {
		super(arg0);
	}

	@SuppressWarnings("rawtypes")
	public SoaringParam(Map arg0) throws JSONException {
		super(arg0);
	}

	public SoaringParam(JSONTokener arg0) throws JSONException {
		super(arg0);
	}

	public String getAppKey() {
		return this.mAppKey;
	}

	public boolean containsKey(String key) {
		return has(key);
	}

	public int size() {
		return length();
	}

	public Iterator<String> keySet() {
		return keys();
	}

	public String toJsonString() {
		return toString();
	}

	public Object get(String key) {
		try {
			if (has(key)) {
				return super.get(key);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String encodeUrl() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		Iterator<String> iterator = keys();
		while (iterator.hasNext()) {
			try {
				String key = iterator.next();
				if (first) {
					first = false;
				} else {
					sb.append("&");
				}
				Object value = get(key);
				if ((value != null)) {
					String param = value.toString();
					if (!TextUtils.isEmpty(param)) {
						try {
							sb.append(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(param, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}