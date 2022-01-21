package rrutkows.codewars.math;

import org.junit.Test;
import rrutkows.codewars.math.ProdSeq.Pair;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static rrutkows.codewars.math.ProdSeq.diophantus;


public class ProdSeqMyTest {
    public static BigInteger squareSum(Pair p) {
        return p.left.pow(2).add(p.right.pow(2));
    }
    @Test
    public void testDiophantus() {
        assertDiophantusResult(10, 5, 7, 14);
        assertDiophantusResult(3, 14, 4, 22);
        assertDiophantusResult(6, 19, 16, 24);
        assertDiophantusResult(3, 7, 1, 12);
        assertDiophantusResult(10, 22, 2, 16);
        assertDiophantusResult(6, 11, 5, 23);
        assertDiophantusResult(5, 8, 4, 23);
        assertDiophantusResult(1, 13, 0, 7);
        assertDiophantusResult(1, 32, 6, 23);
        assertDiophantusResult(0, 6, 4, 25);
    }

    private void assertDiophantusResult(int a, int b, int c, int d) {
        Pair p = new Pair(a, b);
        Pair q = new Pair(c, d);
        Pair dioph = diophantus(p, q);
        assertEquals(squareSum(p).multiply(squareSum(q)).longValue(), squareSum(dioph).longValue());
    }
}