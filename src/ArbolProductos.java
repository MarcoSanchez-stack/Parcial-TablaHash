import java.util.ArrayList;
import java.util.List;

//implementacion propia de un Arbol Binario de Busqueda (BST)que organiza los productos utilizando su codigo como criterio de comparacion.
public class ArbolProductos {
    private Nodo raiz;

    public ArbolProductos() {
        this.raiz = null;
    }

    //Inserta un nuevo producto en el arbol respetando las reglas del BST.
    public void insertar(Producto producto) {
        raiz = insertarRecursivo(raiz, producto);
    }

    private Nodo insertarRecursivo(Nodo actual, Producto producto) {
        if (actual == null) {
            return new Nodo(producto);
        }

        int comparacion = producto.getCodigo().compareTo(actual.getProducto().getCodigo());

        if (comparacion < 0) {
            actual.setIzquierdo(insertarRecursivo(actual.getIzquierdo(), producto));
        } else if (comparacion > 0) {
            actual.setDerecho(insertarRecursivo(actual.getDerecho(), producto));
        }
        // Si el codigo es igual (comparacion == 0), no se inserta nuevamente.
        return actual;
    }

    //Realiza el recorrido inorden (Subarbol izquierdo -> Nodo actual -> Subarbol derecho) e imprime los productos ordenados de menor a mayor codigo en consola.
     
    public void inorden() {
        inordenRecursivo(raiz);
    }

    private void inordenRecursivo(Nodo actual) {
        if (actual != null) {
            inordenRecursivo(actual.getIzquierdo());
            System.out.println(actual.getProducto().toString());
            inordenRecursivo(actual.getDerecho());
        }
    }

    //Obtiene una lista con los productos ordenados por codigo mediante recorrido inorden para ser visualizados en la interfaz grafica.
    public List<Producto> obtenerListaInorden() {
        List<Producto> listaOrdenada = new ArrayList<>();
        recolectarInorden(raiz, listaOrdenada);
        return listaOrdenada;
    }

    private void recolectarInorden(Nodo actual, List<Producto> lista) {
        if (actual != null) {
            recolectarInorden(actual.getIzquierdo(), lista);
            lista.add(actual.getProducto());
            recolectarInorden(actual.getDerecho(), lista);
        }
    }

    //Busca un producto dentro del arbol binario mediante su codigo.
     
    public Producto buscar(String codigo) {
        return buscarRecursivo(raiz, codigo);
    }

    private Producto buscarRecursivo(Nodo actual, String codigo) {
        if (actual == null) {
            return null;
        }

        int comparacion = codigo.compareTo(actual.getProducto().getCodigo());

        if (comparacion == 0) {
            return actual.getProducto();
        } else if (comparacion < 0) {
            return buscarRecursivo(actual.getIzquierdo(), codigo);
        } else {
            return buscarRecursivo(actual.getDerecho(), codigo);
        }
    }
}
