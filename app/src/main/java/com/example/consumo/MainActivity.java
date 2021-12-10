package com.example.consumo;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.consumo.modelo.ListaUser;
import com.example.consumo.modelo.Listado;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.consumo.busqueda.BuscarListado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    EditText txtCodigo;
    EditText multitxtResultado;
    Button btnBuscar;
    Button btnBuscarV;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);

        multitxtResultado = findViewById(R.id.mltxt_resultado);
        btnBuscar = findViewById(R.id.btnBuscarR);
        btnBuscarV = findViewById(R.id.btnBuscarV);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multitxtResultado.setText("");
                Busqueda();
            }
        });

        btnBuscarV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multitxtResultado.setText("");
                jsonRequest();
            }
        });
    }
    private void jsonRequest(){
        String url = "https://gorest.co.in/public/v1/users";
        JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                        JSONArray tamaño = response.getJSONArray("data");
                        for(int i=0; i<tamaño.length(); i++) {
                            JSONObject jsonObject = new JSONObject(tamaño.get(i).toString());
                            Listado listado = new Listado(jsonObject.getString("id"),
                                    jsonObject.getString("name"), jsonObject.getString("email"),
                                    jsonObject.getString("gender"), jsonObject.getString("status"));
                            String json = "";
                            json += ("id:" + listado.getId() + "; ");
                            json += ("Nombre:" + listado.getName() + "; ");
                            json += ("Email:" + listado.getEmail() + "; ");
                            json += ("Gender:" + listado.getGender() + "; ");
                            json += ("Estado:" + listado.getStatus() + "; \n");
                            multitxtResultado.append(json);
                        }
                        }catch(JSONException jex){
                                jex.printStackTrace();
                            }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonrequest);
    }

    private void Busqueda ()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gorest.co.in/public/v1/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        BuscarListado buscarListado = retrofit.create(BuscarListado.class);
        Call<ListaUser> call = buscarListado.getData();
        call.enqueue(new Callback<ListaUser>() {
            @Override
            public void onResponse(Call<ListaUser> call, Response<ListaUser> response) {
                ListaUser lista=response.body();
                List<Listado> lis = lista.getData();
                for(Listado r: lis){
                    String json = "";
                    json += ("id:" + r.getId() + "; ");
                    json += ("Nombre:" + r.getName() + "; ");
                    json += ("Email:" + r.getEmail() + "; ");
                    json += ("Gender:" + r.getGender() + "; ");
                    json += ("Estado:" + r.getStatus() + ";\n");
                  multitxtResultado.append(json);
                }
            }
            @Override
            public void onFailure(Call<ListaUser> call, Throwable t) {
                multitxtResultado.setText(t.getMessage());
            }
        });
    }
}