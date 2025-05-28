package proyectohotel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterUI extends JFrame {
    public RegisterUI() {
        setTitle("Registro de Usuario");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField nombre = new JTextField(15);
        JTextField usuario = new JTextField(15);
        JPasswordField contraseña = new JPasswordField(15);
        String[] tipos = {"cliente", "admin"};
        JComboBox<String> tipoUsuario = new JComboBox<>(tipos);

        JButton registrar = new JButton("Registrar");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; panel.add(nombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; panel.add(usuario, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; panel.add(contraseña, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; panel.add(tipoUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(registrar, gbc);

        registrar.addActionListener(e -> {
            try (Connection conn = Database.getConnection()) {
                String sql = "INSERT INTO usuarios (nombre, usuario, contraseña, tipo) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nombre.getText());
                stmt.setString(2, usuario.getText());
                stmt.setString(3, new String(contraseña.getPassword()));
                stmt.setString(4, (String) tipoUsuario.getSelectedItem());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente");
                new LoginRegisterUI().setVisible(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        add(panel);
    }
}