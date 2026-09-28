package pe.edu.vallegrande;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DescuentoServiceTest {

    @Test
    void debeAplicarDescuento() {

        DescuentoService service = new DescuentoService();

        double resultado = service.calcularPrecio(100, true);

        assertEquals(80, resultado);
    }

    @Test
    void noDebeAplicarDescuento() {

        DescuentoService service = new DescuentoService();

        double resultado = service.calcularPrecio(100, false);

        assertEquals(100, resultado);
    }
}
