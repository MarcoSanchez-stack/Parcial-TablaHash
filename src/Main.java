import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

// grafica principal en Java Swing para administrar el inventario
public class Main extends JFrame {
    private InventarioTienda inventario;

    // Componentes para Registro de Productos
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JButton btnRegistrar;

    // Componentes para Consultar y Actualizar
    private JTextField txtCodigoConsulta;
    private JButton btnBuscar;
    private JTextField txtNuevoStock;
    private JButton btnActualizarStock;

    // Componentes para Productos Registrados
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnListarOrdenados;

    public Main() {
        inventario = new InventarioTienda();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("SISTEMA DE INVENTARIO");
        setSize(650, 740);
        setMinimumSize(new Dimension(580, 680));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        JPanel panelContenedor = new JPanel(new BorderLayout(10, 10));
        panelContenedor.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Titulo superior
        JLabel lblTitulo = new JLabel("SISTEMA DE INVENTARIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelContenedor.add(lblTitulo, BorderLayout.NORTH);

        // Panel central para las secciones superiores (Registro y Consulta)
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

        // 1. SECCION: REGISTRO DE PRODUCTOS
        JPanel panelRegistro = new JPanel(new BorderLayout(8, 8));
        panelRegistro.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "REGISTRO DE PRODUCTOS",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12)
        ));

        JPanel panelCamposRegistro = new JPanel(new GridBagLayout());
        GridBagConstraints gbcReg = new GridBagConstraints();
        gbcReg.insets = new Insets(5, 10, 5, 10);
        gbcReg.fill = GridBagConstraints.HORIZONTAL;

        txtCodigo = new JTextField(20);
        txtNombre = new JTextField(20);
        txtCategoria = new JTextField(20);
        txtPrecio = new JTextField(20);
        txtStock = new JTextField(20);

        agregarFilaFormulario(panelCamposRegistro, gbcReg, 0, "Código:", txtCodigo);
        agregarFilaFormulario(panelCamposRegistro, gbcReg, 1, "Nombre:", txtNombre);
        agregarFilaFormulario(panelCamposRegistro, gbcReg, 2, "Categoría:", txtCategoria);
        agregarFilaFormulario(panelCamposRegistro, gbcReg, 3, "Precio:", txtPrecio);
        agregarFilaFormulario(panelCamposRegistro, gbcReg, 4, "Stock:", txtStock);

        panelRegistro.add(panelCamposRegistro, BorderLayout.CENTER);

        JPanel panelBtnRegistro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegistrar = new JButton("REGISTRAR PRODUCTO");
        panelBtnRegistro.add(btnRegistrar);
        panelRegistro.add(panelBtnRegistro, BorderLayout.SOUTH);

        panelSuperior.add(panelRegistro);
        panelSuperior.add(Box.createVerticalStrut(10));

        // 2. SECCION: CONSULTAR Y ACTUALIZAR
        JPanel panelConsulta = new JPanel(new GridBagLayout());
        panelConsulta.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "CONSULTAR Y ACTUALIZAR",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12)
        ));

        GridBagConstraints gbcCon = new GridBagConstraints();
        gbcCon.insets = new Insets(6, 10, 6, 10);
        gbcCon.fill = GridBagConstraints.HORIZONTAL;

        txtCodigoConsulta = new JTextField(20);
        agregarFilaFormulario(panelConsulta, gbcCon, 0, "Código del producto:", txtCodigoConsulta);

        gbcCon.gridx = 0;
        gbcCon.gridy = 1;
        gbcCon.gridwidth = 2;
        gbcCon.fill = GridBagConstraints.NONE;
        gbcCon.anchor = GridBagConstraints.CENTER;
        btnBuscar = new JButton("BUSCAR PRODUCTO");
        panelConsulta.add(btnBuscar, gbcCon);

        gbcCon.gridwidth = 1;
        gbcCon.fill = GridBagConstraints.HORIZONTAL;
        txtNuevoStock = new JTextField(20);
        agregarFilaFormulario(panelConsulta, gbcCon, 2, "Nuevo stock:", txtNuevoStock);

        gbcCon.gridx = 0;
        gbcCon.gridy = 3;
        gbcCon.gridwidth = 2;
        gbcCon.fill = GridBagConstraints.NONE;
        gbcCon.anchor = GridBagConstraints.CENTER;
        btnActualizarStock = new JButton("ACTUALIZAR STOCK");
        panelConsulta.add(btnActualizarStock, gbcCon);

        panelSuperior.add(panelConsulta);
        panelSuperior.add(Box.createVerticalStrut(10));

        panelContenedor.add(panelSuperior, BorderLayout.CENTER);

        // 3. SECCION: PRODUCTOS REGISTRADOS
        JPanel panelTabla = new JPanel(new BorderLayout(8, 8));
        panelTabla.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "PRODUCTOS REGISTRADOS",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12)
        ));

        String[] columnas = {"Código", "Nombre", "Categoría", "Precio", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(22);
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.setPreferredSize(new Dimension(580, 170));
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBtnListar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnListarOrdenados = new JButton("LISTAR PRODUCTOS ORDENADOS");
        panelBtnListar.add(btnListarOrdenados);
        panelTabla.add(panelBtnListar, BorderLayout.SOUTH);

        panelContenedor.add(panelTabla, BorderLayout.SOUTH);

        add(panelContenedor, BorderLayout.CENTER);

        configurarEventos();
    }

    private void agregarFilaFormulario(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JTextField campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(campo, gbc);
    }

    
    private void configurarEventos() {
        // 1. Evento Registrar Producto
        btnRegistrar.addActionListener(e -> {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            String stockStr = txtStock.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty() || categoria.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, complete todos los campos para registrar el producto.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double precio = Double.parseDouble(precioStr);
                int stock = Integer.parseInt(stockStr);

                if (Double.isNaN(precio) || Double.isInfinite(precio) || precio < 0 || stock < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Ingrese un precio válido y un stock que no sea negativo.",
                            "Valores inválidos",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Producto nuevoProducto = new Producto(codigo, nombre, categoria, precio, stock);
                boolean exito = inventario.registrarProducto(nuevoProducto);

                if (exito) {
                    JOptionPane.showMessageDialog(this,
                            "Producto '" + nombre + "' (Código: " + codigo + ") registrado correctamente.",
                            "Registro exitoso",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiarCamposRegistro();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo registrar: ya existe un producto con el código '" + codigo + "'.",
                            "Código duplicado",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese valores numéricos válidos para Precio y Stock.",
                        "Error de formato numérico",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // 2. Evento Buscar Producto
        btnBuscar.addActionListener(e -> {
            String codigo = txtCodigoConsulta.getText().trim();

            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese el código del producto que desea buscar.",
                        "Campo vacío",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Producto encontrado = inventario.consultarProducto(codigo);
            if (encontrado != null) {
                JOptionPane.showMessageDialog(this,
                        "Producto encontrado:\n\n" +
                        "Código: " + encontrado.getCodigo() + "\n" +
                        "Nombre: " + encontrado.getNombre() + "\n" +
                        "Categoría: " + encontrado.getCategoria() + "\n" +
                        "Precio: $" + String.format("%.0f", encontrado.getPrecio()) + "\n" +
                        "Stock disponible: " + encontrado.getStock(),
                        "Información del producto",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró ningún registro con el código '" + codigo + "'.",
                        "Producto no encontrado",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // 3. Evento Actualizar Stock
        btnActualizarStock.addActionListener(e -> {
            String codigo = txtCodigoConsulta.getText().trim();
            String nuevoStockStr = txtNuevoStock.getText().trim();

            if (codigo.isEmpty() || nuevoStockStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese el código del producto y la nueva cantidad de stock.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int nuevaCantidad = Integer.parseInt(nuevoStockStr);
                boolean actualizado = inventario.actualizarStock(codigo, nuevaCantidad);

                if (actualizado) {
                    JOptionPane.showMessageDialog(this,
                            "Stock del producto '" + codigo + "' actualizado exitosamente a " + nuevaCantidad + " unidades.",
                            "Stock actualizado",
                            JOptionPane.INFORMATION_MESSAGE);
                    txtNuevoStock.setText("");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Operación fallida: verifique que el código '" + codigo + "' exista y que la cantidad no sea negativa.",
                            "Error al actualizar stock",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese un número entero válido para el nuevo stock.",
                        "Error de formato numérico",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // 4. Evento Listar Productos Ordenados
        btnListarOrdenados.addActionListener(e -> {
            actualizarTablaProductos();
        });
    }

    private void actualizarTablaProductos() {
        modeloTabla.setRowCount(0);
        List<Producto> listaOrdenada = inventario.listarProductosOrdenados();

        for (Producto p : listaOrdenada) {
            Object[] fila = {
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    String.format("%.0f", p.getPrecio()),
                    p.getStock()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCamposRegistro() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtCodigo.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main ventana = new Main();
            ventana.setVisible(true);
        });
    }
}
