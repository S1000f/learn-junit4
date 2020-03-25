public class CalculatorTest {
    public static void main(String[] args) {
        Calculator cal = new Calculator();
        double result = cal.add(10, 50);
        if (result != 60) {
            System.out.println("Bad result: " + result);
        }
    }
}
