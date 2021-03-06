/*
 * Copyright (C) 2014 Pygmy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lib.pygmy.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Encapsulates the data to transmit to the other players
 * @author Pygmy
 * 
 */
public class TurnData {
	
	private final static String TAG = "TurnData";

	public String game;
	public String version;
    public String state;
    public int turnCounter;

    public TurnData() {
    	
    }

    // This is the byte array we will write out to the TBMP API.
    public byte[] persist() {
        JSONObject retVal = new JSONObject();

        try {
        	retVal.put("game", game);
        	retVal.put("version", version);
            retVal.put("state", state);
            retVal.put("turnCounter", turnCounter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String st = retVal.toString();
        return st.getBytes(Charset.forName("UTF-16"));
    }

    // Creates a new instance of TurnData
    public static TurnData unpersist(byte[] byteArray) {

        if (byteArray == null) {
            Log.d(TAG, "Empty array - possible bug.");
            return new TurnData();
        }

        String st = null;
        try {
            st = new String(byteArray, "UTF-16");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return null;
        }

        TurnData retVal = new TurnData();
        try {
            JSONObject obj = new JSONObject(st);

            if (obj.has("state")) {
                retVal.state = obj.getString("state");
            }
            if (obj.has("turnCounter")) {
                retVal.turnCounter = obj.getInt("turnCounter");
            }
            if (obj.has("game")) {
                retVal.game = obj.getString("game");
            }
            if (obj.has("version")) {
                retVal.version = obj.getString("version");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return retVal;
    }
    
}