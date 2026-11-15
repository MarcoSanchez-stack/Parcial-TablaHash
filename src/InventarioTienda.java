import java.util.HashMap;
import java.util.List;

// Clase administradora del inventario que integra el HashMap y el ArbolProductos.
public class InventarioTienda implements IInventario {
    private HashMap<String, Producto> productosPorCodigo;
    private ArbolProductos arbolProductos;

    public InventarioTienda() {
        this.productosPorCodigo = new HashMap<>();
        this.arbolProductos = new ArbolProductos();
    }

    @Override
    public boolean registrarProducto(Producto producto) {
        if (producto == null || producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) {
            return false;
        }

        String codigo = producto.getCodigo().trim();

        // 1. Verificar primero que el codigo no exista en el HashMap
        if (productosPorCodigo.containsKey(codigo)) {
            return false;
        }

        // 2. Si es valido, agregar la misma referencia del objeto al HashMap y al arbol
        productosPorCodigo.put(codigo, producto);
        arbolProductos.insertar(producto);
        return true;
    }

    @Override
    public Producto consultarProducto(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }
        // 3. Las consultas por codigo se realizan utilizando el HashMap
        return productosPorCodigo.get(codigo.trim());
    }

    @Override
    public boolean actualizarStock(String codigo, int nuevaCantidad) {
        // Verificar que los parametros sean validos
        if (codigo == null || codigo.trim().isEmpty() || nuevaCantidad < 0) {
            return false;
        }

        // Buscar el producto en el HashMap y verificar que exista
        Producto producto = productosPorCodigo.get(codigo.trim());
        if (producto == null) {
            return false;
        }

        // Modificar su cantidad disponible (se refleja tambien en el arbol por compartir referencia)
        producto.setStock(nuevaCantidad);
        return true;
    }

    @Override
    public List<Producto> listarProductosOrdenados() {
        // 4. Visualizacion ordenada mediante el recorrido inorden del arbol
        System.out.println("=== RECORRIDO INORDEN DEL ARBOL ===");
        arbolProductos.inorden();
        return arbolProductos.obtenerListaInorden();
    }
}
