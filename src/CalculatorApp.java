import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

class RomanCalculator {
    protected static final Map<Character, Integer> ROMAN_VALUES = new HashMap<>();
        static {
            ROMAN_VALUES.put('I', 1);
            ROMAN_VALUES.put('V', 5);
            ROMAN_VALUES.put('X', 10);
            ROMAN_VALUES.put('L', 50);
            ROMAN_VALUES.put('C', 100);
            ROMAN_VALUES.put('D', 500);
            ROMAN_VALUES.put('M', 1000);
        }
    public static int parseRoman(String roman) throws Exception {
        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = ROMAN_VALUES.getOrDefault(roman.charAt(i), -1);
            if (value == -1) {
                throw new Exception("Invalid Roman numeral: " + roman);
            }
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
                prevValue = value;
            }
        }
        return result;
    }
}

public class CalculatorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите арифметическое выражение:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("Выход")) {
                System.out.println("Выход.");
                break;
            }

            try {
                String result = calculate(input);
                System.out.println("Результат: " + result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    public static String calculate(String input) throws Exception {
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            throw new Exception("Неверный формат ввода.");
        }

        String leftStr = parts[0];
        String operator = parts[1];
        String rightStr = parts[2];

        int left = parseNumber(leftStr);
        int right = parseNumber(rightStr);

        int result;
        switch (operator) {
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                if (right == 0) {
                    throw new Exception("Деление на ноль.");
                }
                result = left / right;
                break;
            default:
                throw new Exception("Неверный оператор: " + operator);
        }

        if (result < 0) {
            throw new Exception("Результат отрицательный или нулевой: " + result);
        }

        if (isRoman(leftStr) && isRoman(rightStr)) {
            return toRoman(result);
        } else if (!isRoman(leftStr) && !isRoman(rightStr)) {
            return Integer.toString(result);
        } else {
            throw new Exception("Смешанные системы счисления не допускаются.");
        }
    }

    private static boolean isRoman(String str) {
        return str.matches("[IVXLCDM]+");
    }

    private static int parseNumber(String numStr) throws Exception {
        if (isRoman(numStr)) {
            return RomanCalculator.parseRoman(numStr);
        } else {
            int num = Integer.parseInt(numStr);
            if (num < 1 || num > 10) {
                throw new Exception("Номер вне диапазона (1-10): " + num);
            }
            return num;
        }
    }

    private static String toRoman(int num){
            StringBuilder roman = new StringBuilder();
            for (Map.Entry<Character, Integer> entry : RomanCalculator.ROMAN_VALUES.entrySet()) {
                char symbol = entry.getKey();
                int value = entry.getValue();
                while (num >= value) {
                    roman.append(symbol);
                    num -= value;
                }
            }
        return roman.toString();
    }
}
