package proyectohotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import proyectohotel.Usuario;



public class LoginUI extends JFrame {
    public LoginUI() {
        setTitle("Iniciar Sesión");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new BackgroundPanel(new ImageIcon(getClass().getResource("Fondo.png")).getImage());
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField usuario = new JTextField(15);
        JLabel lblContraseña = new JLabel("Contraseña:");
        JPasswordField contraseña = new JPasswordField(15);
        JButton ingresar = new JButton("Ingresar");

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        panel.add(usuario, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblContraseña, gbc);
        gbc.gridx = 1;
        panel.add(contraseña, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ingresar, gbc);

        ingresar.addActionListener(e -> {
            try (Connection conn = Database.getConnection()) {
                String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, usuario.getText());
                stmt.setString(2, new String(contraseña.getPassword()));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String tipo = rs.getString("tipo");
                        Usuario user = new Usuario(nombre, tipo);


                        JOptionPane.showMessageDialog(this, "Bienvenido, " + nombre);

if ("cliente".equals(tipo)) {
    new HotelUI(user).setVisible(true);
} else {
    new AdminUI().setVisible(true);
}

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        add(panel);
    }
}
