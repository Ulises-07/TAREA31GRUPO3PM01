package com.example.tarea31grupo3pm01;

import com.example.tarea31grupo3pm01.ModeloProducto;
import com.example.tarea31grupo3pm01.RespuestaLogin;
import com.example.tarea31grupo3pm01.SolicitudLogin;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Header;


public interface InterfazApi {

    @POST("crud_oauth_db/Autenticacion.php")
    Call<RespuestaLogin> iniciarSesion(@Body SolicitudLogin solicitud);

    @GET("crud_oauth_db/Productos_API.php")
    Call<List<ModeloProducto>> obtenerProductos(@Header("Authorization") String tokenCompleto);

    @POST("crud_oauth_db/Productos_API.php")
    Call<Void> crearProducto(
            @Header("Authorization") String token,
            @Body ModeloProducto producto
    );

    @PUT("crud_oauth_db/Productos_API.php/{id}")
    Call<Void> actualizarProducto(
            @Header("Authorization") String tokenCompleto,
            @Path("id") int idProducto,
            @Body ModeloProducto producto
    );

    @DELETE("crud_oauth_db/Productos_API.php/{id}")
    Call<Void> eliminarProducto(
            @Header("Authorization") String tokenCompleto,
            @Path("id") int idProducto
    );
}
