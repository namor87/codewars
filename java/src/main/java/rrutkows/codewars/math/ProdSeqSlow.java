package rrutkows.codewars.math;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.util.function.Function.identity;


public class ProdSeqSlow {

    private static final boolean DEBUG = true;

    public static BigInteger[] solve(int ... arr) {
        if(DEBUG) {
            System.out.println("--------------------------------");
            System.out.println(">> STARTING:");
        }

        List<Integer> arrList = IntStream.of(arr).boxed().collect(Collectors.toList());

        List<Integer> factorizedResult = calculateFormula(arrList);

        if(DEBUG) {
            System.out.println(">> PRODUCT COMPONENTS:");
            System.out.println(factorizedResult);
            System.out.println(factorizedResult.stream().map(BigInteger::valueOf).reduce(ONE, (a, b) -> a.multiply(b)));
        }

        if(factorizedResult.contains(0)) {
            return new BigInteger[] {ZERO, ZERO};
        }

        Map<Integer, Long> primeFactorsWithExponents = getPrimeFactorizationOfProduct(factorizedResult);

        if(DEBUG) {
            System.out.println(">> PRIME FACTORS:");
            System.out.println(primeFactorsWithExponents);
            printValue(primeFactorsWithExponents);
            System.out.println(primeFactorsWithExponents.entrySet().stream()
                    .map(e -> "" + e.getKey() + (isElf(e.getKey()) ? " [ELF] : " : "[ORC] : ") + e.getValue())
                    .collect(Collectors.toList()));
        }

        return calculateSolution(primeFactorsWithExponents);
    }

    private static boolean isElf(Integer prime) {
        return (prime % 4 == 1);
    }

    private static List<Integer> calculateFormula(List<Integer> arr) {
        Collection<List<Integer>> indexPartition = IntStream.range(0, arr.size())
                .boxed()
                .collect(Collectors.groupingBy(val -> val / 2))
                .values();

        return indexPartition.stream()
                .parallel()
                .map(indices -> powSum(arr.get(indices.get(0)), arr.get(indices.get(1))))
                .collect(Collectors.toList());

//                .collect(Collectors.toConcurrentMap(Function.identity(), x -> 1, (a, b) -> a + b));

    }

    private static Map<Integer, Long> getPrimeFactorizationOfProduct(List<Integer> factorizedResult) {
        int max = factorizedResult.stream().max(Comparator.comparingInt(Integer::intValue)).orElse(1);
        List<Integer> primes = sieveForFactorization(max);

        return factorizedResult.stream()
                .map(factor -> getPrimeComposition(factor, primes))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(identity(), Collectors.counting()));
    }

    private static List<Integer> sieveForFactorization(int max) {
        int limit = BigInteger.valueOf(max).sqrt().intValue();

        List<Integer> primes = new ArrayList<>();

        byte [] sieve = new byte[limit+1];
        for (int i = 2; i <= limit; i++) {
            if(sieve[i] == 0) {
                primes.add(i);
                for (int j =  2*i; j <= limit; j += i) {
                    sieve[j] = 1;
                }
            }
        }

        return primes;
    }


    private static int powSum(int a, int b) {
        return a*a + b*b;
    }

    private static List<Integer> getPrimeComposition(int toFactorize, List<Integer> primes) {
        List<Integer> factors = new ArrayList<>();

        int num = toFactorize;
        for (int i = 0 ; i < primes.size() && num > 1; i++) {
             Integer prime = primes.get(i);
             while (num > 1 && num % prime == 0) {
                 num = num / prime;
                 factors.add(prime);
             }
         }

         if (num > 1) {
             factors.add(num);
         }

        if(DEBUG) {
            System.out.println(">> factorized : " + toFactorize + " => " + factors);
        }
        return factors;
    }

    private static BigInteger[] calculateSolution(Map<Integer, Long> factorizedResult) {
        BigInteger commonSquareFactorRoot = factorizedResult.entrySet().stream()
                .map(e -> BigInteger.valueOf(e.getKey()).pow(trimOdd(e.getValue().intValue())/2))
                .reduce(ONE, BigInteger::multiply);

        BigInteger commonSquareFactor = commonSquareFactorRoot.pow(2);

        BigInteger nonSquareFactor = factorizedResult.entrySet().stream()
                .filter(e -> isOdd(e.getValue().intValue()))
                .map(Map.Entry::getKey)
                .map(BigInteger::valueOf)
                .reduce(ONE, BigInteger::multiply);

        if(DEBUG) {
            System.out.println(">> SOLVING:");
            System.out.println(">> common factor:");
            System.out.println(commonSquareFactor);
            System.out.println(">> nonSquareFactor:");
            System.out.println(nonSquareFactor);
        }

        BigInteger candidateA = ZERO;
        BigInteger limit = nonSquareFactor.sqrt();

        while (candidateA.compareTo(limit) < 0) {
            BigInteger squareA = candidateA.pow(2);
            BigInteger [] candidateB = nonSquareFactor.subtract(squareA).sqrtAndRemainder();
            if (ZERO.compareTo(candidateB[1]) == 0) {
                return new BigInteger[] {
                        commonSquareFactorRoot.multiply(candidateA),
                        commonSquareFactorRoot.multiply(candidateB[0])
                };
            }
            candidateA = candidateA.add(ONE);
        }
        return new BigInteger[]{ZERO, ZERO};
    }

    private static boolean isOdd(int val) {
        return val % 2 == 1;
    }

    private static int trimOdd(int val) {
        return val - (val % 2);
    }


    private static void printValue(Map<Integer, Long> factorizedResult) {
        Optional<BigInteger> x = factorizedResult.entrySet().stream()
                .map(e -> BigInteger.valueOf(e.getKey()).pow(e.getValue().intValue()))
                .reduce(BigInteger::multiply);
        System.out.println(x.orElseThrow());
    }

    public static void main(String[] args) {
//        List<Integer> integers = Arrays.asList(205, 500, 397, 832, 58, 145, 584, 260, 157, 554, 89, 545, 170, 49, 1025, 565, 36, 641);
//        for(Integer i : integers) {
//            System.out.println(i + " -  " + Arrays.asList(calculateSolution(Map.of(i, 1L))));
//        }

        BigInteger[] solution = solve(3, 14, 4, 22, 6, 19, 16, 24, 3, 7, 1, 12, 10, 22, 2, 16, 6, 11, 5, 23, 5, 8, 4, 23, 1, 13, 0, 7, 1, 32, 6, 23, 0, 6, 4, 25);

        System.out.println("SOLUTION:  " + solution[0] + " * " + solution[1]);
    }

}
