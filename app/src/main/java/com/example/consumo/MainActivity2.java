package com.example.consumo;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    TextView textView;
    RequestQueue requestQueue;
    private static  final String URL1="https://gorest.co.in/public/v1/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        requestQueue=Volley.newRequestQueue(context: this);
       // initUI();
        stringRequest();
        jsonArrayRequest();
    }
   // private void initUI(){textView=findViewById(R.id.textView);}

    private void stringRequest(){
        StringRequest request= new StringRequest(Request.Method.GET,URL1,
                new Response.Listener<String>(){
            @Override
                    public void onResponse(String response){
                    textView.setText(response);
            }

                },
                new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });
        requestQueue.add(request);
    }

    private void jsonArrayRequest(){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, URL1, null,
                new Response.Listener<JSONArray>(){
            @Override
                    public void onResponse(JSONArray response){
                int size =response.length();
                for (int i=0; i<size; i++){
                    try{
                        JSONObject jsonObject=new JSONObject(response.get(i).toString());
                        String title=jsonObject.getString(name:"title");
                        textView.append("Title: "+title+ "\n");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
                },
                new Response.ErrorListenenr(){
            @Override
                    public void onErrorResponse(VolleyError error){

            }
                });
        rerquestQueue.add(jsonArrayRequest);
    }
}