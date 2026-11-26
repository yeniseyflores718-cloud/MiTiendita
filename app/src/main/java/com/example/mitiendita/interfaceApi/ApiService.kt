package com.example.mitiendita.interfaceApi

import com.example.mitiendita.modelo.CarritoAccionResponse
import com.example.mitiendita.modelo.CarritoActualizarRequest
import com.example.mitiendita.modelo.CarritoAgregarRequest
import com.example.mitiendita.modelo.CarritoEliminarRequest
import com.example.mitiendita.modelo.CarritoResponse
import com.example.mitiendita.modelo.CategoriaResponse
import com.example.mitiendita.modelo.EstadoPedidoRequest
import com.example.mitiendita.modelo.EstadoPedidoResponse
import com.example.mitiendita.modelo.InicioAdminResponse
import com.example.mitiendita.modelo.InventarioResponse
import com.example.mitiendita.modelo.LoginRequest
import com.example.mitiendita.modelo.LoginResponse
import com.example.mitiendita.modelo.PedidosResponse
import com.example.mitiendita.modelo.ProductosResponse
import com.example.mitiendita.modelo.RegistroRequest
import com.example.mitiendita.modelo.RegistroResponse
import com.example.mitiendita.modelo.ReportesResponse
import com.example.mitiendita.modelo.RespuestaSimple
import com.example.mitiendita.modelo.RestablecerRequest
import com.example.mitiendita.modelo.SolicitarRecuperacionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login.php")
    suspend fun iniciarSesion(@Body request: LoginRequest): LoginResponse
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

    @GET("pedidos.php")
    suspend fun obtenerPedidos(
        @Query("estado") estado: String? = null
    ): Response<PedidosResponse>

    @GET("obtener_categorias.php")
    suspend fun obtenerCategorias(
        @Query("solo_categorias") soloCategorias: Int = 1
    ): CategoriaResponse

    @GET("obtener_categorias.php")
    suspend fun filtrarProductos(
        @Query("nombre") nombre: String = "",
        @Query("id_categoria") idCategoria: Int = 0
    ): CategoriaResponse

    @GET("reportes.php")
    suspend fun obtenerReportes(
        @Query("id_usuario") idUsuario: Int =0,
        @Query("periodo") periodo: String =""
    ): ReportesResponse
    @POST("registro.php")
    suspend fun registrar(@Body request: RegistroRequest): Response<RegistroResponse>

    @GET("inicioAdmin.php")
    suspend fun obtenerInicioAdmin(
        @Query("id_usuario") idUsuario: Int
    ): InicioAdminResponse

    @POST("solicitar_recuperacion.php")
    suspend fun solicitarRecuperacion(@Body request: SolicitarRecuperacionRequest): Response<RespuestaSimple>

    @POST("restablecer_contrasena.php")
    suspend fun restablecerContrasena(@Body request: RestablecerRequest): Response<RespuestaSimple>

    @GET("carrito.php")
    suspend fun obtenerCarrito(
        @Query("id_usuario") idUsuario: Int
    ): CarritoResponse

    @POST("carrito_agregar.php")
    suspend fun agregarAlCarrito(
        @Body request: CarritoAgregarRequest
    ): CarritoAccionResponse

    @POST("carrito_actualizar.php")
    suspend fun actualizarCantidadCarrito(
        @Body request: CarritoActualizarRequest
    ): CarritoAccionResponse

    @POST("carrito_eliminar.php")//
    suspend fun eliminarDelCarrito(
        @Body request: CarritoEliminarRequest
    ): RespuestaSimple
}