import com.example.mitiendita.modelo.InventarioResponse
import com.example.mitiendita.modelo.RetrofitCliente

class clsInventarioModelo {

    private val api = RetrofitCliente.apiService

    suspend fun obtenerInventario(idUsuario: Int): InventarioResponse {
        return api.obtenerInventario(idUsuario)
    }

    suspend fun obtenerStockBajo(idUsuario: Int): InventarioResponse {
        return api.obtenerInventario(idUsuario, true)
    }

    suspend fun buscarProducto(idUsuario: Int, nombre: String): InventarioResponse {
        return api.obtenerInventario(idUsuario, false, nombre)
    }
}