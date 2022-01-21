package rrutkows.codewars.math;


import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;


public class ProdSeq {

    public static BigInteger[] solve(int ... arr) {
        Pair neutralElement = new Pair(ZERO, ONE);

        Pair result = batchStream(arr, 2)
                .map(batch -> new Pair(batch.get(0), batch.get(1)))
                .reduce(neutralElement, ProdSeq::diophantus);

        return new BigInteger[] {result.left.abs(), result.right.abs()};
    }

    public static Stream<List<Integer>> batchStream(int [] list, int batchSize) {
        AtomicInteger counter = new AtomicInteger();
        return IntStream.of(list)
                .boxed()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / batchSize))
                .values()
                .stream();
    }

    public static Pair diophantus(Pair a, Pair b) {
        return new Pair(
                a.left.multiply(b.left).add(a.right.multiply(b.right)),
                a.left.multiply(b.right).subtract(a.right.multiply(b.left))
        );
    }

    static class Pair {
        BigInteger left, right;

        public Pair(int left, int right) {
            this.left = BigInteger.valueOf(left);
            this.right = BigInteger.valueOf(right);
        }

        public Pair(BigInteger left, BigInteger right) {
            this.left = left;
            this.right = right;
        }
    }
}


