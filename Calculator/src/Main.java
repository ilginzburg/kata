import java.util.Scanner;

public class Main {

    private final static String ERROR_MSG = "ошибка ввода";
    private final static String START_MSG = "введите строку действия: ";
    private final static int NUMBER_OF_OPERANDS = 2;
    private final static int MIN_OPERAND = 1;
    private final static int MAX_OPERAND = 10;
    private final static int MIN_ROMAN = 1;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(START_MSG);
            String input = scanner.nextLine();
            System.out.println(calc(input));
        }
    }

    public static String calc(String input) throws Exception {
        boolean isArabic = Character.isDigit(input.charAt(0));
        int[] operands = extractOperands(input);
        if (operands.length != NUMBER_OF_OPERANDS)
            throw new Exception(ERROR_MSG);
        int result = performOperation(operands[0], operands[1], detectOperation(input));
        return isArabic ? Integer.toString(result) : convertArabicNumToRoman(result);
    }

    private static int[] extractOperands(String input) throws Exception {
        String[] stringOperands = input.split("[*+-/]", NUMBER_OF_OPERANDS);
        int[] numericOperands = new int[NUMBER_OF_OPERANDS];
        if (!areOperandsSameType(stringOperands))
            throw new Exception(ERROR_MSG);
        for (int i = 0; i < stringOperands.length; i++)
            numericOperands[i] = convertOperandToNumeric(stringOperands[i]);
        return numericOperands;
    }

    private static boolean areOperandsSameType(String[] operands) {
        return (((Character.isDigit(operands[0].charAt(0))) || (!Character.isDigit(operands[1].charAt(0))))
                && ((!Character.isDigit(operands[0].charAt(0))) || (Character.isDigit(operands[1].charAt(0)))));
    }

    private static char detectOperation(String input) throws Exception {
        String operators = input.replaceAll("[^*+-/]", "");
        if (operators.length() != NUMBER_OF_OPERANDS - 1)
            throw new Exception(ERROR_MSG);
        return operators.charAt(0);
    }

    private static int convertOperandToNumeric(String operand) throws NumberFormatException {
        int number;
        if (!Character.isDigit(operand.charAt(0))) {
            if (!checkRoman(operand))
                throw new NumberFormatException(ERROR_MSG);
            number = convertRomanToArabic(operand);
        } else
            number = Integer.parseInt(operand);
        if ((number < MIN_OPERAND) || (number > MAX_OPERAND))
            throw new NumberFormatException(ERROR_MSG);
        return number;
    }

    private static int performOperation(int leftOperand, int rightOperand, char operator) throws Exception {
        return switch (operator) {
            case '+' -> leftOperand + rightOperand;
            case '-' -> leftOperand - rightOperand;
            case '*' -> leftOperand * rightOperand;
            case '/' -> leftOperand / rightOperand;
            default -> throw new Exception(ERROR_MSG);
        };
    }

    private static boolean checkRoman(String operand) {
        return operand.matches("I?V?|V?I{0,3}|I?X?");
    }

    private static int convertRomanToArabic(String romanNumber) {
        int result = convertRomanSymbolToDigit(romanNumber.charAt(0));
        if (romanNumber.length() == 1)
            return result;
        for (int i = 0; i < romanNumber.length() - 1; i++) {
            int currentDigit = convertRomanSymbolToDigit(romanNumber.charAt(i));
            int nextDigit = convertRomanSymbolToDigit(romanNumber.charAt(i + 1));
            result = (nextDigit > currentDigit) ? nextDigit - currentDigit : result + nextDigit;
        }
        return result;
    }

    private static int convertRomanSymbolToDigit(char romanSymbol) {
        return switch (romanSymbol) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            default -> 0;
        };
    }

    private static String convertArabicNumToRoman(int arabicNum) throws NumberFormatException {
        if (arabicNum < MIN_ROMAN)
            throw new NumberFormatException(ERROR_MSG);
        StringBuilder result = new StringBuilder();
        while (arabicNum >= 100) {
            result.append("C");
            arabicNum -= 100;
        }
        while (arabicNum >= 90) {
            result.append("XC");
            arabicNum -= 90;
        }
        while (arabicNum >= 50) {
            result.append("L");
            arabicNum -= 50;
        }
        while (arabicNum >= 40) {
            result.append("XL");
            arabicNum -= 40;
        }
        while (arabicNum >= 10) {
            result.append("X");
            arabicNum -= 10;
        }
        while (arabicNum >= 9) {
            result.append("IX");
            arabicNum -= 9;
        }
        while (arabicNum >= 5) {
            result.append("V");
            arabicNum -= 5;
        }
        while (arabicNum >= 4) {
            result.append("IV");
            arabicNum -= 4;
        }
        while (arabicNum >= 1) {
            result.append("I");
            arabicNum -= 1;
        }
        return result.toString();
    }
}