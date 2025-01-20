package org.example;

import java.sql.*;

// Clase para manejar la base de datos SQLite
public class BaseDatos {

    // URL de la base de datos SQLite
    private static final String DB_URL = "jdbc:sqlite:usuarios.db";

    // Bloque estático que se ejecuta al cargar la clase y crea la tabla si no existe
    static {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "dni TEXT PRIMARY KEY, "
                    + "saldo REAL DEFAULT 0.0)";
            stmt.execute(createTable);
        } catch (SQLException e) {
            throw new RuntimeException("Error inicializando la base de datos", e);
        }
    }

    // Método para autenticar al usuario verificando si su DNI existe en la base de datos
    public static boolean autenticarUsuario(String dni) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT dni FROM usuarios WHERE dni = ?")) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Devuelve true si se encuentra el usuario
        } catch (SQLException e) {
            throw new RuntimeException("Error autenticando al usuario", e);
        }
    }

    // Método para validar que el formato del DNI es correcto (8 números + 1 letra)
    public static boolean validarDNI(String dni) {
        return dni.matches("\\d{8}[A-Z]"); // Se asegura de que la letra sea mayúscula
    }

    // Método para registrar un usuario nuevo en la base de datos
    public static boolean registrarUsuario(String dni) {
        if (!validarDNI(dni)) {
            System.out.println("DNI incorrecto, inténtalo otra vez.");
            return false;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO usuarios (dni, saldo) VALUES (?, 0.0)")) {
            pstmt.setString(1, dni);
            pstmt.executeUpdate(); // Inserta un nuevo usuario con saldo inicial en 0
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error registrando al usuario", e);
        }
    }

    // Método para obtener el saldo de un usuario a partir de su DNI
    public static double obtenerSaldo(String dni) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT saldo FROM usuarios WHERE dni = ?")) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo"); // Devuelve el saldo si el usuario existe
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error para obtener el saldo", e);
        }
        return 0.0; // Retorna saldo cero si no se encuentra el usuario
    }

    // Método para actualizar el saldo de un usuario
    public static void actualizarSaldo(String dni, double nuevoSaldo) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE usuarios SET saldo = ? WHERE dni = ?")) {
            pstmt.setDouble(1, nuevoSaldo);
            pstmt.setString(2, dni);
            pstmt.executeUpdate(); // Actualiza el saldo del usuario
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el saldo", e);
        }
    }
}
