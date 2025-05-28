package proyectohotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.jdatepicker.impl.*;

public class ReservaUI extends JFrame {
    private JComboBox<String> tipoHabitacion;
    private JComboBox<Integer> numeroPersonas;
    private JDatePickerImpl fechaEntrada;
    private JDatePickerImpl fechaSalida;
    private String usuario;
    private Usuario usuarioActual;

    public ReservaUI(Usuario usuario) {
    this.usuarioActual = usuario;


        setTitle("Reservar habitación");
        setSize(400, 400);
        setLayout(new GridLayout(0, 1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] tipos = {"Simple", "Doble", "Suite"};
        tipoHabitacion = new JComboBox<>(tipos);

        Integer[] personas = {1, 2, 3, 4};
        numeroPersonas = new JComboBox<>(personas);

        UtilDateModel modelEntrada = new UtilDateModel();
        UtilDateModel modelSalida = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        JDatePanelImpl panelEntrada = new JDatePanelImpl(modelEntrada, p);
        fechaEntrada = new JDatePickerImpl(panelEntrada, new DateLabelFormatter());

        JDatePanelImpl panelSalida = new JDatePanelImpl(modelSalida, p);
        fechaSalida = new JDatePickerImpl(panelSalida, new DateLabelFormatter());

        JButton reservar = new JButton("Reservar");

        add(new JLabel("Tipo de habitación:"));
        add(tipoHabitacion);
        add(new JLabel("Número de personas:"));
        add(numeroPersonas);
        add(new JLabel("Fecha entrada:"));
        add(fechaEntrada);
        add(new JLabel("Fecha salida:"));
        add(fechaSalida);
        add(reservar);

        reservar.addActionListener(e -> {
            String tipo = (String) tipoHabitacion.getSelectedItem();
            int personasSel = (int) numeroPersonas.getSelectedItem();
            Date entrada = (Date) fechaEntrada.getModel().getValue();
            Date salida = (Date) fechaSalida.getModel().getValue();

            if (entrada == null || salida == null) {
                JOptionPane.showMessageDialog(this, "Seleccione fechas válidas.");
                return;
            }

Connection conn = null;
try {
    conn = Database.getConnection();
                String sql = "INSERT INTO reservas (usuario, tipo_habitacion, num_personas, fecha_entrada, fecha_salida) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, usuario.getNombre());
                stmt.setString(2, tipo);
                stmt.setInt(3, personasSel);
                stmt.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(entrada));
                stmt.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(salida));
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Reserva realizada con éxito.");
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar la reserva.");
} finally {
    if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
}
        });

        setVisible(true);
    }
}

