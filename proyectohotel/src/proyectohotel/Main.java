package proyectohotel;

import java.awt.EventQueue;

public class Main {
   public static void main(String[] args) {

    DatabaseSetup.initialize();

    EventQueue.invokeLater(() -> {
        new LoginRegisterUI().setVisible(true);
    });
    }
}

