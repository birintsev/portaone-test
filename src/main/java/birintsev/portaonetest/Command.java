package birintsev.portaonetest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "start")
@RequiredArgsConstructor
public class Command implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Command.class);

    @CommandLine.Option(names = {"-f", "-file"}, required = true)
    private File file;

    private final IntegersService integersService;

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long duration;

        List<Integer> ints = parse(file);

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> max =
            executorService
                .submit(
                    () -> integersService.findMax(ints)
                );
        Future<Integer> min =
            executorService
                .submit(
                    () -> integersService.findMin(ints)
                );
        Future<Double> arithmeticalMean =
            executorService
                .submit(
                    () -> integersService.findArithmeticalMean(ints)
                );
        Future<Double> median =
            executorService.submit(
                () -> integersService.findMedian(ints)
            );
        Future<List<Integer>> longestIncreasingSubsequence =
            executorService.submit(
                () -> integersService.findLongestIncreasingSequence(ints)
            );
        Future<List<Integer>> longestDecreasingSubsequence =
            executorService
                .submit(
                    () -> integersService.findLongestDecreasingSequence(ints)
                );

        duration = System.currentTimeMillis() - startTime;

        try {
            LOGGER.info(
                "Max = " + max.get()
                    + ", min = " + min.get()
                    + ", arithmetical mean = " + arithmeticalMean.get()
                    + ", median = " + median.get()
                    + ", longest increasing subsequence: "
                    + longestIncreasingSubsequence.get()
                    + ", longest decreasing subsequence: "
                    + longestDecreasingSubsequence.get()
                    + " (duration is " + duration + " ms)"
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        executorService.shutdown();
    }

    private List<Integer> parse(File file) {
        List<Integer> parsedIntegers = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String rawInt = scanner.nextLine();
                try {
                    parsedIntegers.add(Integer.parseInt(rawInt));
                } catch (NumberFormatException e) {
                    LOGGER.error(rawInt + " is not an integer. Ignoring it.");
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return parsedIntegers;
    }
}
