package birintsev.portaonetest;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
public class IntegerServiceImpl implements IntegersService {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    @Override
    public Integer findMedian(List<Integer> integers) {
        if (integers.isEmpty()) {
            return null;
        }
        return isEven(integers.size())
               ? findMedianEven(integers)
               : findMedianOdd(integers);
    }

    @Override
    public List<Integer> findLongestIncreasingSequence(List<Integer> integers) {
        // inclusive indexes
        int longestStart = 0;
        int longestEnd = 0;
        int currentStart = 0;
        int currentEnd = 0;
        if (integers.isEmpty()) {
            return Collections.emptyList();
        }
        for (int i = 0; i < integers.size() - 1; i++) {
            if (integers.get(i) < integers.get(i + 1)) {
                currentEnd++;
            } else {
                if ((currentEnd - currentStart) > (longestEnd - longestStart)) {
                    longestStart = currentStart;
                    longestEnd = currentEnd;
                }
                currentStart = i + 1;
                currentEnd = i + 1;
            }
        }
        if ((currentEnd - currentStart) > (longestEnd - longestStart)) {
            longestStart = currentStart;
            longestEnd = currentEnd;
        }
        return integers.subList(longestStart, longestEnd + 1);
    }

    @Override
    public List<Integer> findLongestDecreasingSequence(List<Integer> integers) {
        // inclusive indexes
        int longestStart = 0;
        int longestEnd = 0;
        int currentStart = 0;
        int currentEnd = 0;
        if (integers.isEmpty()) {
            return Collections.emptyList();
        }
        for (int i = 0; i < integers.size() - 1; i++) {
            if (integers.get(i) > integers.get(i + 1)) {
                currentEnd++;
            } else {
                if ((currentEnd - currentStart) > (longestEnd - longestStart)) {
                    longestStart = currentStart;
                    longestEnd = currentEnd;
                }
                currentStart = i + 1;
                currentEnd = i + 1;
            }
        }
        if ((currentEnd - currentStart) > (longestEnd - longestStart)) {
            longestStart = currentStart;
            longestEnd = currentEnd;
        }
        return integers.subList(longestStart, longestEnd + 1);
    }

    @Override
    public Integer findMax(List<Integer> integers) {
        int max;
        if (integers.isEmpty()) {
            return null;
        }
        max = integers.get(0);
        for (int i : integers) {
            max = Math.max(i, max);
        }
        return max;
    }

    @Override
    public Integer findMin(List<Integer> integers) {
        int min;
        if (integers.isEmpty()) {
            return null;
        }
        min = integers.get(0);
        for (int i : integers) {
            min = Math.min(i, min);
        }
        return min;
    }

    @Override
    public Double findArithmeticalMean(List<Integer> integers) {
        double sum = 0;
        if (integers.isEmpty()) {
            return null;
        }
        for (int i : integers) {
            sum += i;
        }
        return sum / integers.size();
    }

    private Integer findMedianOdd(List<Integer> integers) {
        return findKthGreatest(integers, (integers.size() / 2) + 1);
    }

    private Integer findMedianEven(List<Integer> integers) {
        int leftMedian = findKthGreatest(
            integers,
            integers.size() / 2
        );
        int rightMedian = findKthGreatest(
            integers,
            (integers.size() / 2) + 1
        );
        return (leftMedian + rightMedian) / 2;
    }

    private boolean isEven(int i) {
        return i % 2 == 0;
    }

    private Integer findKthGreatest(List<Integer> integers, int k) {
        List<Integer> lessOrEqual = new ArrayList<>(
            integers.size() / 2
        );
        List<Integer> greater = new ArrayList<>(
            integers.size() / 2
        );
        final int pivot = getRandomElement(integers);
        for (int i : integers) {
            if (i > pivot) {
                greater.add(i);
            } else {
                lessOrEqual.add(i);
            }
        }
        if (greater.size() == (k - 1)) {
            return pivot;
        }
        if (greater.size() >= k) {
            return findKthGreatest(greater, k);
        } else {
            if (greater.size() == 0 && new HashSet<>(lessOrEqual).size() == 1) {
                return pivot;
            } else {
                return findKthGreatest(lessOrEqual, k - greater.size());
            }
        }
    }

    private int getRandomElement(List<Integer> integers) {
        int randomIndex = RANDOM.nextInt(integers.size());
        return integers.get(randomIndex);
    }
}
