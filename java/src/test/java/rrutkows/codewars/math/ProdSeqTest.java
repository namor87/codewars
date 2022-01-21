package rrutkows.codewars.math;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

public class ProdSeqTest {
    private static boolean check(BigInteger[] ans, BigInteger[] expect) {
        if (ans.length != 2)
            return false;
        if (!(ans[0] instanceof BigInteger) || !(ans[1] instanceof BigInteger)) {
            System.out.println("A and B should be big integers");
            return false;
        }
        BigInteger r0 = ans[0], r1 = ans[1], rr0 = expect[0], rr1 = expect[1];
        if (r0.compareTo(BigInteger.ZERO) < 0 || r1.compareTo(BigInteger.ZERO) < 0) {
            System.out.println("A and B should be nonnegative");
            return false;
        }
        BigInteger p = rr0.multiply(rr0).add(rr1.multiply(rr1));
        BigInteger q = r0.multiply(r0).add(r1.multiply(r1));
        Boolean eqy = p.equals(q);
        if (eqy == false) {
            System.out.println("Incorrect product:\nyour product is " + q.toString() + "\nexpected is     " + p.toString());
            System.out.println("Your return is:  " + r0.toString() + ","+ r1.toString());
            System.out.println("Possible return: " + rr0.toString() + "," + rr1.toString());
        }
        return eqy;
    }
    private static void testing(int[] arr, BigInteger[] exp) {
        BigInteger[] ans = ProdSeq.solve(arr);
        boolean bl = check(ans, exp);
        assertEquals(true, bl);
    }
    @Test
    public void test() {
        int[] a0 = {0, 7, 0, 0};
        BigInteger[] aa = new BigInteger[2];
        aa[0] = new BigInteger("0"); aa[1] = new BigInteger("0");
        testing(a0, aa);

        int[] a1 = {1, 3, 1, 2, 1, 5, 1, 9};
        aa[0] = new BigInteger("250"); aa[1] = new BigInteger("210");
        testing(a1, aa);

        int[] a2 = {2, 1, 3, 4};
        aa[0] = new BigInteger("2"); aa[1] = new BigInteger("11");
        testing(a2, aa);

        int[] a3 = {3, 9, 8, 4, 6, 8, 7, 8, 4, 8, 5, 6, 6, 4, 4, 5};
        aa[0] = new BigInteger("13243200"); aa[1] = new BigInteger("25905600");
        testing(a3, aa);

        int[] a4 = {4, 3, 4, 2, 4, 5, 5, 9};
        aa[0] = new BigInteger("870"); aa[1] = new BigInteger("1190");
        testing(a4, aa);

        int[] a5 = {7, 6, 6, 4, 9, 2, 6, 2, 4, 7, 4, 9, 8, 6, 8, 3};
        aa[0] = new BigInteger("2165800"); aa[1] = new BigInteger("26210600");
        testing(a5, aa);

        int[] a6 = {3, 2, 9, 9, 3, 6, 8, 7, 5, 2, 4, 9, 3, 9, 8, 4, 2, 3, 3, 6};
        aa[0] = new BigInteger("105928560"); aa[1] = new BigInteger("340093080");
        testing(a6, aa);

        int[] a7 = {2, 3, 6, 8, 9, 7, 9, 5};
        aa[0] = new BigInteger("780"); aa[1] = new BigInteger("4160");
        testing(a7, aa);

        int[] a8 = {6, 6, 6, 6};
        aa[0] = new BigInteger("0"); aa[1] = new BigInteger("72");
        testing(a8, aa);

        aa[0] = new BigInteger("0"); aa[1] = new BigInteger("54");
        testing(new int[]{3, 3, 9, 9}, aa);

    }


//    @Ignore
    @Test
    public void longTest() {
        int[] a = {3, 14, 4, 22, 6, 19, 16, 24, 3, 7, 1, 12, 10, 22, 2, 16, 6, 11, 5, 23, 5, 8, 4, 23, 1, 13, 0, 7, 1, 32, 6, 23, 0, 6, 4, 25};
        BigInteger[] aa = {new BigInteger("2635484062411200000"), new BigInteger("4506057775801545600000")};
        testing(a, aa);
    }
}