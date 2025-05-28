package proyectohotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:sqlite:hotel.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public boolean guardarReserva(String usuario, String tipoHabitacion, int numPersonas, java.util.Date fechaEntrada, java.util.Date fechaSalida) {
        String sql = "INSERT INTO reservas (usuario, tipo_habitacion, num_personas, fecha_entrada, fecha_salida) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, tipoHabitacion);
            stmt.setInt(3, numPersonas);
            stmt.setDate(4, new java.sql.Date(fechaEntrada.getTime()));
            stmt.setDate(5, new java.sql.Date(fechaSalida.getTime()));

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

