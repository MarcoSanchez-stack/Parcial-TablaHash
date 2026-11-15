import java.util.List;

// Interfaz que define las operaciones que debe implementar el administrador del inventario de la tienda tecnologica.
 
public interface IInventario {
    boolean registrarProducto(Producto producto);
    Producto consultarProducto(String codigo);
    boolean actualizarStock(String codigo, int nuevaCantidad);
    List<Producto> listarProductosOrdenados();
}
