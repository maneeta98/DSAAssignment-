package com.example.dsaassignmentindv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class BasicCalculatorGUI extends JFrame {
    private JTextField inputField;
    private JButton[] numberButtons;
    private JButton decimalButton, equalsButton, addButton, subtractButton, multiplyButton, divideButton;
    private JButton clearButton, sqrtButton, changeSignButton, percentButton, memoryButton;
    private JLabel resultLabel;
    private double memory = 0;

    public BasicCalculatorGUI() {
        setTitle("Basic Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.setEditable(false);
        inputField.setFont(new Font("Arial", Font.PLAIN, 24));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        initializeButtons();

        // Add buttons to the panel
        for (int i = 1; i <= 9; i++) {
            buttonPanel.add(numberButtons[i]);
        }
        buttonPanel.add(decimalButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(equalsButton);
        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(sqrtButton);
        buttonPanel.add(changeSignButton);
        buttonPanel.add(percentButton);
        buttonPanel.add(memoryButton);

        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resultLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        add(inputField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        pack();
    }

    private void initializeButtons() {
        // Number buttons 0-9
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createButton(String.valueOf(i), e -> inputField.setText(inputField.getText() + e.getActionCommand()));
        }

        decimalButton = createButton(".", e -> inputField.setText(inputField.getText() + "."));
        equalsButton = createButton("=", e -> calculateResult());
        addButton = createButton("+", e -> inputField.setText(inputField.getText() + " + "));
        subtractButton = createButton("-", e -> inputField.setText(inputField.getText() + " - "));
        multiplyButton = createButton("*", e -> inputField.setText(inputField.getText() + " * "));
        divideButton = createButton("/", e -> inputField.setText(inputField.getText() + " / "));
        clearButton = createButton("C", e -> inputField.setText(""));
        sqrtButton = createButton("√", e -> calculateSquareRoot());
        changeSignButton = createButton("±", e -> changeSign());
        percentButton = createButton("%", e -> inputField.setText(inputField.getText() + " % "));
        memoryButton = createButton("M", e -> handleMemory());
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.addActionListener(listener);
        return button;
    }

    private void calculateResult() {
        try {
            String expression = inputField.getText();
            int result = evaluateExpression(expression);
            resultLabel.setText("Result: " + result);
            inputField.setText(String.valueOf(result));
        } catch (Exception ex) {
            resultLabel.setText("Error: Invalid Expression");
        }
    }

    private void calculateSquareRoot() {
        try {
            double value = Double.parseDouble(inputField.getText());
            double result = Math.sqrt(value);
            resultLabel.setText("Result: " + result);
            inputField.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Error: Invalid Input");
        }
    }

    private void changeSign() {
        try {
            double value = Double.parseDouble(inputField.getText());
            value = -value;
            inputField.setText(String.valueOf(value));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Error: Invalid Input");
        }
    }

    private void handleMemory() {
        try {
            double currentValue = Double.parseDouble(inputField.getText());
            memory = currentValue;
            inputField.setText("");
            resultLabel.setText("Memory: " + memory);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Error: Invalid Input");
        }
    }

    private int evaluateExpression(String s) {
        int len = s.length();
        Stack<Integer> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();
        int num = 0;
        char operation = '+';

        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
            }
            if (ch == '(') {
                int j = i, count = 0;
                while (i < len) {
                    if (s.charAt(i) == '(') count++;
                    if (s.charAt(i) == ')') count--;
                    if (count == 0) break;
                    i++;
                }
                num = evaluateExpression(s.substring(j + 1, i));
            }
            if (!Character.isDigit(ch) && ch != ' ' || i == len - 1) {
                if (operation == '+') numbers.push(num);
                if (operation == '-') numbers.push(-num);
                if (operation == '*') numbers.push(numbers.pop() * num);
                if (operation == '/') numbers.push(numbers.pop() / num);
                operation = ch;
                num = 0;
            }
        }

        int result = 0;
        while (!numbers.isEmpty()) {
            result += numbers.pop();
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BasicCalculatorGUI calculator = new BasicCalculatorGUI();
            calculator.setVisible(true);
        });
    }
}
