package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class IngresosTest {

    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        // Crear un usuario para las pruebas
        usuario = new Usuarios("87654321A");
        BaseDatos.registrarUsuario("87654321A");
    }

    @AfterEach
    void limpiarBaseDatos() {
        // Limpiar los datos del usuario después de cada prueba
        try (var conn = java.sql.DriverManager.getConnection("jdbc:sqlite:usuarios.db");
             var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM usuarios");
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Error limpiando la base de datos", e);
        }
    }

    @Test
    void agregarIngresoNomina() {
        // Ingreso de nómina con IRPF
        Ingresos.agregarIngreso(usuario, 1, 1000); // Ingreso de 1000
        assertEquals(850, usuario.obtenerSaldo()); // Debería quedar 850 después de IRPF
    }

    @Test
    void agregarIngresoVenta() {
        // Ingreso por venta
        Ingresos.agregarIngreso(usuario, 2, 200); // Ingreso de 200
        assertEquals(200, usuario.obtenerSaldo()); // Saldo debería ser 200
    }

    @Test
    void agregarIngresoConceptoInvalido() {
        // Intentar agregar un ingreso con concepto no válido
        assertThrows(IllegalArgumentException.class, () -> {
            Ingresos.agregarIngreso(usuario, 999, 500); // Concepto inválido
        });
    }
}
