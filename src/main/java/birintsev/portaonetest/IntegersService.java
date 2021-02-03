package birintsev.portaonetest;

import java.util.List;

public interface IntegersService {

    Double findMedian(List<Integer> integers);

    List<Integer> findLongestIncreasingSequence(List<Integer> integers);

    List<Integer> findLongestDecreasingSequence(List<Integer> integers);

    Integer findMax(List<Integer> integers);

    Integer findMin(List<Integer> integers);

    Double findArithmeticalMean(List<Integer> integers);
}
