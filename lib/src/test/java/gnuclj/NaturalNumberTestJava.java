package gnuclj;

import components.naturalnumber.*;
//import gnucl.naturalnumber.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NaturalNumberJavaTest {
    @Test
    public void add() {
        NaturalNumber nn1 = new NaturalNumber1L(8);
        NaturalNumber nn2 = new NaturalNumber2(5);
        nn1.add(nn2);
        assertEquals(new NaturalNumber1L(13), nn1);
        assertEquals(new NaturalNumber1L(5), nn2);
    }

    @Test
    public void radix() {
        assertEquals(10, NaturalNumber.RADIX);
    }
}
