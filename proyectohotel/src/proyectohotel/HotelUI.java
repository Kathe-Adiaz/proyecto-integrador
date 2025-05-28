package proyectohotel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyectohotel.Usuario;

public class HotelUI extends JFrame {
    private Usuario usuarioActual;
    private JButton btnReservar;
    private JButton btnMostrar;

    public HotelUI(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        setTitle("Hotel");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);

        FondoPanel background = new FondoPanel();
        background.setLayout(null);
        setContentPane(background);

        btnReservar = new JButton("Reservar");
        btnReservar.setBounds(130, 80, 120, 40);
        background.add(btnReservar);

        btnMostrar = new JButton("Mostrar Reservas");
        btnMostrar.setBounds(130, 140, 120, 40);
        background.add(btnMostrar);

        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservaUI(usuarioActual).setVisible(true);
            }
        });

        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservasListaUI(usuarioActual).setVisible(true);
            }
        });
    }
}

