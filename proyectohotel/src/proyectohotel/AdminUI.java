package proyectohotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Properties;
import java.util.Calendar;
import java.util.Date;
import java.sql.*;

import org.jdatepicker.impl.*;

public class AdminUI extends JFrame {
    private JTable tablaReservas;
    private DefaultTableModel modelo;
    private JDatePickerImpl datePicker;

    public AdminUI() {
        setTitle("Panel de Administrador");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Image background = new ImageIcon(getClass().getResource("Fondo.png")).getImage();
        BackgroundPanel panel = new BackgroundPanel(background);
        panel.setLayout(new BorderLayout());

        // Tabla
        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Tipo Habitación", "Personas", "Fecha"}, 0);
        tablaReservas = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con calendario y botón
        JPanel panelInferior = new JPanel();

        // Configurar JDatePicker
        UtilDateModel dateModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        panelInferior.add(new JLabel("Seleccionar fecha:"));
        panelInferior.add(datePicker);

        JButton btnMostrar = new JButton("Mostrar Reservas");
        btnMostrar.addActionListener(e -> mostrarReservasPorFecha());
        panelInferior.add(btnMostrar);

        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void mostrarReservasPorFecha() {
        Date fechaSeleccionada = (Date) datePicker.getModel().getValue();

        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una fecha.");
            return;
        }

        modelo.setRowCount(0); // limpiar tabla

        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM reservas WHERE fecha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(fechaSeleccionada.getTime()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("tipo_habitacion"),
                    rs.getInt("num_personas"),
                    rs.getDate("fecha")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener reservas.");
        }
    }
}
