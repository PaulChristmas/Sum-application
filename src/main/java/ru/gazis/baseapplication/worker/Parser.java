package ru.gazis.baseapplication.worker;

import org.json.*;

public class Parser {
	
	public JSONObject parse(String jsonString) {
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			return jsonObj;
		} catch (JSONException e) {
			System.err.println("Wrong format: " + e.getMessage());
		}
		return null;
	}

}