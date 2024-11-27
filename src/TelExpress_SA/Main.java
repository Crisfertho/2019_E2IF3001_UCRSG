package TelExpress_SA;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Main extends JFrame {
    private TreeBinary arbolClientes = new TreeBinary();
    private Graph grafo = new Graph();
    private List<String> listaBarrios = new ArrayList<>();
    private JTable tablaClientes;
    private JTable tablaGrafo;

    public Main() {
        setTitle("TelExpress S.A.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JTabbedPane pestañas = new JTabbedPane();

        // Pestañas
        pestañas.add("Gestión de Clientes", crearPanelClientes());
        pestañas.add("Gestión de Barrios", crearPanelBarrios());
        pestañas.add("Grafo de Barrios", crearPanelGrafo());

        add(pestañas);
        setVisible(true);
    }

    // Panel para gestión de clientes
    private JPanel crearPanelClientes() {
        JPanel panelClientes = new JPanel(new BorderLayout());

        // Tabla para mostrar clientes
        DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"Nombre", "Barrio", "Monto"}, 0);
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        panelClientes.add(scrollTabla, BorderLayout.CENTER);

        // Formulario de inserción
        JPanel formulario = new JPanel(new GridLayout(5, 2, 10, 10));
        formulario.setBorder(BorderFactory.createTitledBorder("Nuevo Cliente"));

        JTextField campoNombre = new JTextField();
        JTextField campoTelefono = new JTextField();
        JTextField campoBarrio = new JTextField();
        JTextField campoMonto = new JTextField();
        JButton botonInsertar = new JButton("Insertar Cliente");

        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Teléfono (8 dígitos):"));
        formulario.add(campoTelefono);
        formulario.add(new JLabel("Barrio:"));
        formulario.add(campoBarrio);
        formulario.add(new JLabel("Monto:"));
        formulario.add(campoMonto);
        formulario.add(botonInsertar);

        panelClientes.add(formulario, BorderLayout.NORTH);

        // Acción para insertar cliente
        botonInsertar.addActionListener(e -> {
            try {
                String nombre = campoNombre.getText().trim();
                String telefono = campoTelefono.getText().trim();
                String barrio = campoBarrio.getText().trim();
                double monto = Double.parseDouble(campoMonto.getText().trim());

                if (nombre.isEmpty() || telefono.isEmpty() || barrio.isEmpty() || telefono.length() != 8 || monto <= 0) {
                    JOptionPane.showMessageDialog(this, "Datos inválidos, revise las entradas.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String telefonoHash = Utils.hashTelefono(telefono);
                Client nuevoCliente = new Client(nombre, telefonoHash, barrio, monto);
                arbolClientes.insertar(nuevoCliente);

                modeloTabla.addRow(new Object[]{nombre, barrio, monto});
                JOptionPane.showMessageDialog(this, "Cliente insertado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar campos
                campoNombre.setText("");
                campoTelefono.setText("");
                campoBarrio.setText("");
                campoMonto.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al insertar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panelClientes;
    }

    // Panel para gestión de barrios
    private JPanel crearPanelBarrios() {
        JPanel panelBarrios = new JPanel(new BorderLayout());

        JButton botonGenerarBarrios = new JButton("Generar Lista de Barrios");
        JTextField campoBusqueda = new JTextField(15);
        JButton botonBuscarBarrio = new JButton("Buscar Barrio");

        JTextArea areaResultados = new JTextArea(10, 30);
        areaResultados.setEditable(false);

        JPanel acciones = new JPanel();
        acciones.add(botonGenerarBarrios);
        acciones.add(new JLabel("Buscar Barrio:"));
        acciones.add(campoBusqueda);
        acciones.add(botonBuscarBarrio);

        panelBarrios.add(acciones, BorderLayout.NORTH);
        panelBarrios.add(new JScrollPane(areaResultados), BorderLayout.CENTER);

        // Acción para generar barrios
        botonGenerarBarrios.addActionListener(e -> {
            listaBarrios = Arrays.asList("Centro", "Sur", "Norte", "Este", "Oeste");
            Collections.sort(listaBarrios);
            grafo.agregarBarrio("Empresa");
            for (String barrio : listaBarrios) {
                grafo.agregarBarrio(barrio);
                grafo.agregarConexion("Empresa", barrio, new Random().nextInt(10) + 1);
            }
            areaResultados.setText("Lista de barrios generada: " + listaBarrios);
        });

        // Acción para buscar barrio
        botonBuscarBarrio.addActionListener(e -> {
            String barrio = campoBusqueda.getText().trim();
            if (listaBarrios.contains(barrio)) {
                areaResultados.setText("Barrio encontrado: " + barrio);
            } else {
                areaResultados.setText("Barrio no encontrado.");
            }
        });

        return panelBarrios;
    }

    // Panel para grafo de barrios
    private JPanel crearPanelGrafo() {
        JPanel panelGrafo = new JPanel(new BorderLayout());

        JButton botonMostrarGrafo = new JButton("Mostrar Grafo");
        JTextArea areaGrafo = new JTextArea(15, 40);
        areaGrafo.setEditable(false);

        panelGrafo.add(botonMostrarGrafo, BorderLayout.NORTH);
        panelGrafo.add(new JScrollPane(areaGrafo), BorderLayout.CENTER);

        // Acción para mostrar grafo
        botonMostrarGrafo.addActionListener(e -> {
            areaGrafo.setText("Conexiones del Grafo:\n" + grafo.mostrarGrafo());
        });

        return panelGrafo;
    }

    public static void main(String[] args) {
        new Main();
    }
}