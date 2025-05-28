package proyectohotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import proyectohotel.Usuario;


public class ReservasListaUI extends JFrame {
    private JTable tablaReservas;
    private Usuario usuarioActual;

    public ReservasListaUI(Usuario usuario) {
        this.usuarioActual = usuario;

        setTitle("Mis Reservas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tablaReservas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        add(scrollPane, BorderLayout.CENTER);

        cargarReservas();
    }

    private void cargarReservas() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tipo Habitación", "Personas", "Entrada", "Salida"});
        tablaReservas.setModel(model);

        Connection conn = null;

        try {
            conn = Database.getConnection();
            String sql = "SELECT tipo_habitacion, num_personas, fecha_entrada, fecha_salida FROM reservas WHERE usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuarioActual.getNombre());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo_habitacion");
                int personas = rs.getInt("num_personas");
                String entrada = rs.getString("fecha_entrada");
                String salida = rs.getString("fecha_salida");

                model.addRow(new Object[]{tipo, personas, entrada, salida});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las reservas: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {}
            }
        }
    }
}


