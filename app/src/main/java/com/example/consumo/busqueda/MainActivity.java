package com.example.consumo.busqueda;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.consumo.Listado;
import com.example.consumo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    EditText txtCodigo;
    EditText multitxtResultado;
    Button btnBuscar;
    Button btnBuscarV;
    RequestQueue requestQueue;
    private static final String URL1 = "https://gorest.co.in/public/v1/users/ws/issues.php?j_id=";
    private static final String URL2 = "https://gorest.co.in/public/v1/users/ws/issues.php?j_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);

        txtCodigo= findViewById(R.id.txtCodigo);
        multitxtResultado = findViewById(R.id.mltxt_resultado);
        btnBuscar = findViewById(R.id.btnBuscarR);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            multitxtResultado.setText("");
            Busqueda(txtCodigo.getText().toString());
        }
    });
        initUI();

        btnBuscarV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multitxtResultado.setText("");
                jsonRequest(txtCodigo.getText().toString());
            }
        });
}

    private void initUI(){
        txtCodigo = findViewById(R.id.txtCodigo);
        multitxtResultado = findViewById(R.id.mltxt_resultado);
        btnBuscarV = findViewById(R.id.btnBuscarV);
    }

    private void jsonRequest(String codigo){
        String o_id = getString(R.string.id);
        String o_name = getString(R.string.name);
        String o_email = getString(R.string.email);
        String o_gender = getString(R.string.gender);
        String o_status = getString(R.string.status);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL2+codigo,
                null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int tamaño = response.length();
                        for(int i=0; i<tamaño; i++)
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String email = jsonObject.getString("email");
                                String gender = jsonObject.getString("gender");
                                String status = jsonObject.getString("status");
                                multitxtResultado.append(o_id + " " + id + "\n" +
                                        o_name + " " + name + "\n" +
                                        o_email + " " + email + "\n" +
                                        o_gender + " " + gender + "\n" +
                                        o_status + " " + status + "\n");
                            }catch(JSONException jex){
                                jex.printStackTrace();
                            }
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void Busqueda (String codigo)
    {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://gorest.co.in/public/v1/users")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BuscarListado buscarListado = retrofit.create(BuscarListado.class);
        Call<List<Listado>> call = buscarListado.find(codigo);
        call.enqueue(new Callback<List<Listado>>() {
            @Override
            public void onResponse(Call<List<Listado>> call, Response<List<Listado>> response) {
                List<Listado> listado = response.body();
                String id = getString(R.string.id);
                String name = getString(R.string.name);
                String email = getString(R.string.email);
                String gender = getString(R.string.gender);
                String status = getString(R.string.status);
                for(Listado r: listado){
                    String json = "";
                    json+= id + " " + r.getId() + "\n" +
                            name + " " + r.getName() + "\n" +
                            email + " " + r.getEmail() + "\n" +
                            gender + " " + r.getGender() + "\n" +
                            status + " " + r.getStatus() + "\n" ;
                    multitxtResultado.append(json);
                }
            }
            @Override
            public void onFailure(Call<List<Listado>> call, Throwable t) {
                multitxtResultado.setText(t.getMessage());
            }
        });
    }
}