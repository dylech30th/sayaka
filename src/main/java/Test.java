import java.util.function.Supplier;

public class Test {
    public static void waitConditional(Supplier<Boolean> condition) {
        while (!condition.get()) {
        }
    }

    public static void main(String[] args) {
        waitConditional(() -> false);
        System.out.println();
    }
}
