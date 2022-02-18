package rrutkows.codewars.math;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

public class Primes {
    public static IntStream stream() {
        return IntStream.generate(new PrimeGenerator());
    }

    private static class PrimeGenerator implements IntSupplier {
        final LinkedList<Integer> initialPrimes = new LinkedList<>(List.of(2,3));
        final LinkedList<Integer> allPrimes = new LinkedList<>();

        @Override
        public int getAsInt() {
            if(! initialPrimes.isEmpty()) {
                allPrimes.addLast(initialPrimes.remove(0));
            }
            else {
                int candidate = allPrimes.getLast() + 2;
                while (!isPrime(candidate)) {
                    candidate += 2;
                }
                allPrimes.addLast(candidate);
            }
            return allPrimes.getLast();
        }

        private boolean isPrime(int p) {
            final double sqrt = Math.sqrt(p);
            for (Integer q : allPrimes) {
                if(q > sqrt) {
                    return true;
                }
                if (p % q == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
