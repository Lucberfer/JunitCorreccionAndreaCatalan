package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BaseDatosTest {

    @BeforeAll
    static void configurarBaseDatos() {
        // Registrar un usuario inicial para pruebas
        BaseDatos.registrarUsuario("12345678A");
    }

    @AfterEach
    void limpiarUsuarios() {
        // Limpiar la base de datos eliminando todos los usuarios después de cada prueba
        try (var conn = java.sql.DriverManager.getConnection("jdbc:sqlite:usuarios.db");
             var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM usuarios");
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Error limpiando la base de datos", e);
        }
    }

    @Test
    void autenticarUsuario() {
        // Registrar un usuario de prueba
        BaseDatos.registrarUsuario("87654321B");

        // Caso 1: Usuario existente
        assertTrue(BaseDatos.autenticarUsuario("87654321B"));

        // Caso 2: Usuario no existente
        assertFalse(BaseDatos.autenticarUsuario("11111111C"));
    }

    @Test
    void validarDNI() {
        // Casos válidos
        assertTrue(BaseDatos.validarDNI("12345678A"));
        assertTrue(BaseDatos.validarDNI("87654321Z"));

        // Casos inválidos
        assertFalse(BaseDatos.validarDNI("12345678"));
        assertFalse(BaseDatos.validarDNI("ABCDEFGHI"));
        assertFalse(BaseDatos.validarDNI("12345678a"));
    }

    @Test
    void registrarUsuario() {
        // Caso 1: Registro exitoso
        assertTrue(BaseDatos.registrarUsuario("12345678C"));
        assertTrue(BaseDatos.autenticarUsuario("12345678C"));

        // Caso 2: Intento de registro con DNI inválido
        assertFalse(BaseDatos.registrarUsuario("ABCDEFGH"));
        assertFalse(BaseDatos.autenticarUsuario("ABCDEFGH"));
    }

    @Test
    void obtenerSaldo() {
        // Registrar un usuario y comprobar saldo inicial
        BaseDatos.registrarUsuario("23456789D");
        assertEquals(0, BaseDatos.obtenerSaldo("23456789D"));

        // Usuario inexistente
        assertEquals(0, BaseDatos.obtenerSaldo("99999999Z"));
    }

    @Test
    void actualizarSaldo() {
        // Registrar un usuario y actualizar su saldo
        BaseDatos.registrarUsuario("34567890E");
        BaseDatos.actualizarSaldo("34567890E", 500);

        // Verificar que el saldo se actualizó correctamente
        assertEquals(500, BaseDatos.obtenerSaldo("34567890E"));

        // Actualizar nuevamente el saldo
        BaseDatos.actualizarSaldo("34567890E", 750);
        assertEquals(750, BaseDatos.obtenerSaldo("34567890E"));
    }
}
