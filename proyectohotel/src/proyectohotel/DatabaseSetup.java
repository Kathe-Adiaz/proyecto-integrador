package proyectohotel;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {

    public static void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS reservas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario TEXT NOT NULL," +
                "tipo_habitacion TEXT NOT NULL," +
                "num_personas INTEGER NOT NULL," +
                "fecha_entrada DATE NOT NULL," +
                "fecha_salida DATE NOT NULL" +
                ");";

        try (Connection conn = new Database().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'reservas' verificada o creada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

