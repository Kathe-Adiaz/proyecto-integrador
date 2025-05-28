package proyectohotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MostrarReservasAdminUI extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;

    public MostrarReservasAdminUI() {
        setTitle("Reservas (Administrador)");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        modelo = new DefaultTableModel(new String[]{
                "ID", "Usuario", "Tipo Habitación", "Número de Personas", "Fecha"
        }, 0);
        tabla = new JTable(modelo);
        cargarReservas();

        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel();
        JButton eliminarBtn = new JButton("Eliminar");
        JButton editarBtn = new JButton("Editar");

        botonesPanel.add(editarBtn);
        botonesPanel.add(eliminarBtn);

        panel.add(botonesPanel, BorderLayout.SOUTH);

        // Acción eliminar
        eliminarBtn.addActionListener(e -> eliminarReserva());

        // Acción editar
        editarBtn.addActionListener(e -> editarReserva());

        add(panel);
        setVisible(true);
    }

    private void cargarReservas() {
        modelo.setRowCount(0);
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM reservas";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("tipo_habitacion"),
                        rs.getInt("numero_personas"),
                        rs.getString("fecha")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar reservas: " + e.getMessage());
        }
    }

    private void eliminarReserva() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para eliminar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta reserva?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = Database.getConnection()) {
                String sql = "DELETE FROM reservas WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                cargarReservas();
                JOptionPane.showMessageDialog(this, "Reserva eliminada.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar reserva: " + e.getMessage());
            }
        }
    }

    private void editarReserva() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para editar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        String tipo = (String) modelo.getValueAt(fila, 2);
        int personas = (int) modelo.getValueAt(fila, 3);
        String fecha = (String) modelo.getValueAt(fila, 4);

        JTextField tipoField = new JTextField(tipo);
        JTextField personasField = new JTextField(String.valueOf(personas));
        JTextField fechaField = new JTextField(fecha);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Tipo Habitación:"));
        panel.add(tipoField);
        panel.add(new JLabel("Número de Personas:"));
        panel.add(personasField);
        panel.add(new JLabel("Fecha:"));
        panel.add(fechaField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Reserva", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = Database.getConnection()) {
                String sql = "UPDATE reservas SET tipo_habitacion = ?, numero_personas = ?, fecha = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tipoField.getText());
                stmt.setInt(2, Integer.parseInt(personasField.getText()));
                stmt.setString(3, fechaField.getText());
                stmt.setInt(4, id);
                stmt.executeUpdate();
                cargarReservas();
                JOptionPane.showMessageDialog(this, "Reserva actualizada.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar reserva: " + e.getMessage());
            }
        }
    }
}
