package pv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {
    private static Game game;

    // Juego de prueba
    @BeforeEach
    public static void setUp() {
        game = new Game("Test", 50000, "Accion", "Computador", 10);
        
    }

    // Se revisa que los datos se hayan inicializado correctamente
    @Test
    public void testGameDetails() {
        String details = game.toString();
        String expectedDetails = "Juego: Test\nPrecio: 50000\nGénero: Accion\nPlataforma: Computador\nCantidad: 10\n";
        assertEquals(expectedDetails, details);
    }

    // Se revisa que se actualicen correctamente los datos del juego luego de comprarlo
    @Test
    public void testBuyGameSuccess() {
        game.decreaseQuantity(5);
        assertEquals(5, game.quantity);
        assertEquals(1, game.salesHistory.size());
        assertEquals(250000, game.salesTotal);
    }

    // Se revisa que no se pueda comprar un juego con una cantidad mayor a la disponible
    @Test
    public void testBuyGameFailure() {
        assertThrows(IllegalArgumentException.class, () -> game.decreaseQuantity(15));
    }

    // Se revisa que se actualicen correctamente los datos en el historial luego de comprarlo
    @Test
    public void testSalesHistoryUpdate() {
        game.decreaseQuantity(5);
        assertEquals(1, game.salesHistory.size());
        assertEquals(5, game.salesHistory.get(0).quantity);
        assertEquals(250000, game.salesHistory.get(0).total, 0.01);
    }
}