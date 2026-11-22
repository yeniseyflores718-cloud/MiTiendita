package com.example.mitiendita.interfaceApi

import com.example.mitiendita.modelo.CategoriaResponse
import com.example.mitiendita.modelo.EstadoPedidoRequest
import com.example.mitiendita.modelo.EstadoPedidoResponse
import com.example.mitiendita.modelo.InventarioResponse
import com.example.mitiendita.modelo.LoginRequest
import com.example.mitiendita.modelo.LoginResponse
import com.example.mitiendita.modelo.ProductosResponse
import com.example.mitiendita.modelo.RegistroRequest
import com.example.mitiendita.modelo.RegistroResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @GET("productos.php")
    suspend fun obtenerProductos(): ProductosResponse

    @GET("productos.php")
    suspend fun obtenerProducto(@Query("id_producto") idProducto: Int): ProductosResponse

    @GET("inventario.php")
    suspend fun obtenerInventario(
        @Query("id_usuario") idUsuario: Int,
        @Query("stock_bajo") stockBajo: Boolean = false,
        @Query("buscar") buscar: String = ""
    ): InventarioResponse

    @POST("estado_pedido.php")
    suspend fun cambiarEstadoPedido(@Body request: EstadoPedidoRequest): EstadoPedidoResponse

    @GET("obtener_categorias.php")
    suspend fun obtenerCategorias(
        @Query("solo_categorias") soloCategorias: Int = 1
    ): CategoriaResponse

    @GET("obtener_categorias.php")
    suspend fun filtrarProductos(
        @Query("nombre") nombre: String = "",
        @Query("id_categoria") idCategoria: Int = 0
    ): CategoriaResponse
    @POST("registro.php")
    suspend fun registrar(@Body request: RegistroRequest): Response<RegistroResponse>
}