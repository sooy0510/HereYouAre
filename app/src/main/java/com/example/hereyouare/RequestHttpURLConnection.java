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

public class RequestHttpURLConnection extends AsyncTask<String, Void, ArrayList<HashMap>> { //시작파라미터, 진행상태, 리턴타입

    ArrayList<HashMap> jsonList = new ArrayList<>(); //return 해줄 열차 정보들

    @Override
    protected void onPostExecute(ArrayList<HashMap> result) {
        super.onPostExecute(result);

        //mTextViewResult.setText(result);
        Log.d("connection", "POST response  - " + result);
    }



    @Override
    protected ArrayList<HashMap> doInBackground(String... params) {
        URL url = null;
        try {
            String station = params[0];
            url = new URL("http://70.12.115.72:8002/webserver/"+station+"/station_status");
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
                JSONObject jsonObject = readJsonFromUrl(conn);
                Log.d("connection", jsonObject.toString());
                jsonList = jsonListParser(jsonObject);
                Log.d("connection", jsonList.toString());

                // 닫기
                os.close();
                //return jsonList;
                return jsonList;

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


    public ArrayList<HashMap> jsonListParser(JSONObject jsonObject) {

        String ascTitle;
        String descTitle;
        String seat1;
        String seat2;

        ArrayList<HashMap> objList = new ArrayList<>();
        try {
            HashMap train = new HashMap();
            JSONArray trainLineNm = jsonObject.getJSONArray("trainLineNm");
            JSONObject empty_seat_status = jsonObject.getJSONObject("empty_seat_status");


            Log.d("connection", trainLineNm.toString());
            Log.d("connection", empty_seat_status.toString());

            ascTitle = trainLineNm.get(0).toString();
            descTitle = trainLineNm.get(1).toString();
            seat1 = empty_seat_status.get("up").toString();
            seat2 = empty_seat_status.get("down").toString();

            Log.d("connection", ascTitle);
            Log.d("connection", descTitle);
            Log.d("connection", seat1);
            Log.d("connection", seat2);


            train.put("ascTitle", ascTitle);
            train.put("descTitle", descTitle);
            train.put("seat1", seat1);
            train.put("seat2", seat2);
//            map.put("longitude", endPoint.getViaX());
//            map.put("latitude", endPoint.getViaY());
//            map.put("totalDistance",totalDistance);
//            map.put("totalTime",totalTime);
            objList.add(train);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objList;
    }


}