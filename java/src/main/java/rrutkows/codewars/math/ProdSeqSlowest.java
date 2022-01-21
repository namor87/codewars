package rrutkows.codewars.math;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ProdSeqSlowest {
    public static BigInteger[] solve(int[] arr) {
        BigInteger result = calculateFormula(arr);
        return calculateSolution(result);
    }

    private static BigInteger calculateFormula(int[] arr) {
        Collection<List<Integer>> indexPartition = IntStream.range(0, arr.length)
                .boxed()
                .collect(Collectors.groupingBy(val -> val / 2))
                .values();

        return indexPartition.stream()
                .parallel()
                .map(L -> powSum(arr[L.get(0)], arr[L.get(1)]))
                .reduce(BigInteger::multiply)
                .orElse(BigInteger.ONE);
    }

    private static BigInteger powSum(int a, int b) {
        return BigInteger.valueOf(a).pow(2).add(BigInteger.valueOf(b).pow(2));
    }

    private static BigInteger[] calculateSolution(BigInteger result) {
        BigInteger candidateA = BigInteger.ZERO;
        BigInteger limit = result.sqrt();

        while (candidateA.compareTo(limit) == -1) {
            BigInteger squareA = candidateA.pow(2);
            BigInteger candidateB = result.subtract(squareA).sqrt();
            if (squareA.add(candidateB.pow(2)).compareTo(result) == 0) {
                return new BigInteger[]{candidateA, candidateB};
            }
            candidateA = candidateA.add(BigInteger.ONE);
        }
        return new BigInteger[]{BigInteger.ZERO, BigInteger.ZERO};
    }

}
