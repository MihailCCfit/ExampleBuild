import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestClass {
    private static Stream<int[]> numbers() {
        int[][] a = {{1,2},{3,4}, {-5,5}};
        return Stream.of(
                a
        );
    }

    @ParameterizedTest
    @MethodSource("numbers")
    void testSum(int[] ar){
        Assertions.assertEquals(SomeClass.sum(ar[0],ar[1]), ar[0]+ar[1]);
    }
}
