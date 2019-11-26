package com.example.hereyouare;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestHttpURLConnection2 extends AsyncTask<String, Void, JSONObject> { //시작파라미터, 진행상태, 리턴타입

    JSONObject jsonObject= new JSONObject(); //return 해줄 열차 정보들

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        //mTextViewResult.setText(result);
        Log.d("connection", "POST response  - " + result);
    }



    @Override
    protected JSONObject doInBackground(String... params) {
        URL url = null;
        try {
            String station = params[0];
            String actNum = params[1];
            if(actNum.equals("a1")){
                url = new URL("http://70.12.115.72:8002/webserver/"+station+"/station_status");
            }else if(actNum.equals("a2")){
                url = new URL("http://70.12.115.72:8002/webserver/"+station+"/station_status_detail");
            }else{
                String trainNum = params[2];
                url = new URL("http://70.12.115.72:8002/webserver/"+station+"/station_status_detail/"+trainNum+"/station_train_detail");
            }
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 보내는 타입
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Charset", "utf-8"); // Accept-Charset 설정.
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            String body = "station="+station;
            Log.d("connection","station:"+station);

            // 전송
            OutputStream os = conn.getOutputStream();

            try {
                //보낼거
                Log.d("connection", body);
                os.write(body.getBytes("UTF-8"));
                os.flush();

                // 200 성공코드 // 400 문법에러
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // 성공시 처리
                    Log.d("connection","성공");
                }else{
                    // 실패시 처리
                    Log.d("connection","실패");
                    Log.d("connection",conn.getResponseCode()+"");
                }


                // tmap server에서 받아온 경로탐색 json파일 parsing
                jsonObject = readJsonFromUrl(conn);
                Log.d("connection", jsonObject.toString());

                // 닫기
                os.close();
                //return jsonList;
                return jsonObject;

            } catch (
                    MalformedURLException e) {
                Log.d("ssssss","잘못된 url");
                e.printStackTrace();
            } catch (
                    ProtocolException e) {
                Log.d("ssssss","잘못된 url");
                e.printStackTrace();
            } catch (
                    UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d("ssssss","인코딩");
            } catch (
                    IOException e) {
                Log.d("ssssss","입출력문제");
                e.printStackTrace();
                Log.d("ssssss", e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    // django server에서 데이터 받기
    public static JSONObject readJsonFromUrl(HttpURLConnection conn) throws IOException {
        String jsonText = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));


        //Log.d("line:",br.toString());

        String line = null;
        while ((line = br.readLine()) != null) {
            jsonText += line;
        }
        // json 파일로 변환
        try {
            JSONObject json = new JSONObject(jsonText);
            br.close();
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    //public ArrayList<HashMap> jsonListParser3(JSONObject jsonObject) {
    public void jsonListParser3(JSONObject jsonObject) {

        ArrayList<HashMap> objList = new ArrayList<>();
        Log.d("connection6", jsonObject.toString());

        try {
            HashMap train_3 = new HashMap();
            HashMap train_4 = new HashMap();
            HashMap train_5 = new HashMap();

            JSONArray obj3 = jsonObject.getJSONObject("trains").getJSONArray("003");
            JSONArray obj4 = jsonObject.getJSONObject("trains").getJSONArray("004");
            JSONArray obj5 = jsonObject.getJSONObject("trains").getJSONArray("005");

            for(int i=0; i<obj3.length(); i++){
                JSONArray seat = obj3.getJSONArray(i);
                String seat_n = seat.get(0).toString();
                String seat_f = seat.get(1).toString();
                train_3.put("seat"+(i+1), jsonObject);
            }

            for(int i=0; i<obj4.length(); i++){
                JSONArray seat = obj4.getJSONArray(i);
                String seat_n = seat.get(0).toString();
                String seat_f = seat.get(1).toString();
            }

            for(int i=0; i<obj5.length(); i++){
                JSONArray seat = obj5.getJSONArray(i);
                String seat_n = seat.get(0).toString();
                String seat_f = seat.get(1).toString();
            }

//            Log.d("connection", asc_trains.toString());
//            Log.d("connection", desc_trains.toString());



//            Log.d("connection", asc_trains.toString());
//            Log.d("connection", desc_trains.toString());
//
//            objList.add(asc_trains);
//            objList.add(desc_trains);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return objList;
    }

}