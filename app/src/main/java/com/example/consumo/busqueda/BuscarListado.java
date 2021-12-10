package com.example.consumo.busqueda;

import com.example.consumo.modelo.ListaUser;

import retrofit2.Call;
import retrofit2.http.GET;


public interface BuscarListado {
    @GET("users")
    public Call<ListaUser>getData();
}
