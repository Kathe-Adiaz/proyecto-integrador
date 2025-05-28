package proyectohotel;

import javax.swing.*;
import java.awt.*;

public class LoginRegisterUI extends JFrame {

    public LoginRegisterUI() {
        setTitle("Bienvenido");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Cargar imagen de fondo correctamente
        Image background = new ImageIcon(getClass().getResource("Fondo.png")).getImage();
        BackgroundPanel panel = new BackgroundPanel(background);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegister = new JButton("Registrarse");

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnLogin, gbc);

        gbc.gridy = 1;
        panel.add(btnRegister, gbc);

        btnLogin.addActionListener(e -> {
            new LoginUI().setVisible(true);
            dispose();
        });

        btnRegister.addActionListener(e -> {
            new RegisterUI().setVisible(true);
            dispose();
        });

        add(panel);
    }
}
