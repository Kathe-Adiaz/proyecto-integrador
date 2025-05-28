package proyectohotel; // o 'clases' si lo prefieres

import javax.swing.*;
import java.awt.*;

public class FondoPanel extends JPanel {
    private Image imagen;

    @Override
    public void paintComponent(Graphics g) {
        imagen = new ImageIcon(getClass().getResource("/proyectohotel/Fondo.png")).getImage();
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        setOpaque(false);
        super.paintComponent(g);
    }
}
