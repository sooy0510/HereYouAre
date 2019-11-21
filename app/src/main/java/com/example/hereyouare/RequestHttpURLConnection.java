package com.example.hereyouare;

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

public class RequestHttpURLConnection {
    ArrayList<HashMap> jsonList = new ArrayList<>(); //return 해줄 route 정보들

    public static String setConn(String station) {
        // tmap api server 연결
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:8000/webserver/"+station+"/station_status");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 보내는 타입
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Charset", "utf-8"); // Accept-Charset 설정.
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            String body = "station="+station;

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
                Log.d("dddd", jsonObject.toString());
                //jsonList = jsonListParser(jsonObject, startPoint, endPoint);

                // 닫기
                os.close();
                //return jsonList;
                return "ss";

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



    // tmap api server에서 경로탐색 결과 받기
    public static JSONObject readJsonFromUrl(HttpURLConnection conn) throws IOException {
        String jsonText = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));


        //Log.d("line:",br.toString());

        String line = null;
        while ((line = br.readLine()) != null) {
            //System.out.println(line);
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


//    public ArrayList<HashMap> jsonListParser(JSONObject jsonObject, ViaPointVO startPoint, ViaPointVO endPoint) {
//
//        String totalDistance;
//        String totalTime;
//
//        ArrayList<HashMap> objList = new ArrayList<>();
//        try {
//            HashMap start = new HashMap();
//            start.put("longitude", startPoint.getViaX());
//            start.put("latitude", startPoint.getViaY());
//            objList.add(start);
//
//
//            HashMap map = new HashMap();
//            JSONObject jObject = jsonObject.getJSONArray("features").getJSONObject(0);
//            JSONObject jProperties = jObject.getJSONObject("properties");
//
//            totalDistance = jProperties.optString("totalDistance");
//            totalTime = jProperties.optString("totalTime");
//
//            map.put("longitude", endPoint.getViaX());
//            map.put("latitude", endPoint.getViaY());
//            map.put("totalDistance",totalDistance);
//            map.put("totalTime",totalTime);
//            objList.add(map);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return objList;
//    }


}