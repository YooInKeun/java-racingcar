package com.nextstep.racingcar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RaceTest {
    private static final Random RANDOM = new Random();
    private final List<CarName> carNames = Arrays.asList(new CarName("test1"), new CarName("test2"), new CarName("test3"));

    @DisplayName("0-3만 나오는 경우와 4-9만 나오는 경우 position의 변화 체크")
    @ParameterizedTest
    @MethodSource
    public void moveAndGet(Supplier<Integer> numberGenerator, int moveCount, int expected) {
        Race race = new Race(carNames, numberGenerator);

        List<Car> carList = null;

        for (int ix = 0 ; ix < moveCount ; ix ++) {
            carList = race.moveAndGet();
        }
        assertThat(carList).isNotNull();

        for (Car car : carList) {
            assertThat(car.getPosition()).isEqualTo(expected);
        }
    }

    private static Stream<Arguments> moveAndGet() {
        Supplier<Integer> lessThanGenerator = () -> RANDOM.nextInt(4);
        Supplier<Integer> greaterThanGenerator = () -> RANDOM.nextInt(6) + 4;
        return Stream.of(
                Arguments.of(lessThanGenerator, 5, 0),
                Arguments.of(greaterThanGenerator, 5, 5)
        );
    }

    @DisplayName("우승 한 car의 이름들을 가져오는지 확인")
    @ParameterizedTest
    @MethodSource
    public void getWinnerNames(Supplier<Integer> numberGenerator, int moveCount, String expected) {
        Race race = new Race(carNames, numberGenerator);

        for (int ix = 0 ; ix < moveCount ; ix ++) {
            race.moveAndGet();
        }
        assertThat(race.getWinnerNames()).isEqualTo(expected);
    }

    private static Stream<Arguments> getWinnerNames() {
        return Stream.of(
                Arguments.of(new SpecificGenerator(5, 1, 1), 5, "test1"),
                Arguments.of(new SpecificGenerator(1, 5, 1), 5, "test2"),
                Arguments.of(new SpecificGenerator(1, 1, 5), 5, "test3"),
                Arguments.of(new SpecificGenerator(1, 5, 5), 5, "test2,test3"),
                Arguments.of(new SpecificGenerator(5, 5, 5), 5, "test1,test2,test3")
        );
    }

    private static class SpecificGenerator implements  Supplier<Integer> {
        private int[] values;
        private int idx = 0;

        public SpecificGenerator(int ... values) {
            this.values = values;
        }

        @Override
        public Integer get() {
            if (idx >= values.length) {
                idx = 0;
            }
            return values[idx++];
        }
    }
}