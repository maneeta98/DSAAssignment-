package com.example.dsaassignmentindv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class BasicCalculatorGUI extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;
    private JPanel buttonPanel;
    private double memoryValue = 0; // Declare and initialize memoryValue

    public BasicCalculatorGUI() {
        setTitle("Basic Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);  // Center the window
        setResizable(true);  // Allow resizing

        // Create a panel for the display (inputField and resultLabel)
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(2, 1));
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 30));  // Bigger font for the display
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        displayPanel.add(inputField);

        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 24));  // Larger font for result
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.WHITE);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.add(resultLabel);

        add(displayPanel, BorderLayout.NORTH);  // Add display panel at the top

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Add padding around the buttons
        addButtons(buttonPanel);
        add(buttonPanel, BorderLayout.CENTER);  // Add button panel in the center

        setVisible(true);
    }

    private void addButtons(JPanel panel) {
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "(", ")", "+",
                "C", "Del", "=", ".",
                "M+", "M-", "MR", "MC"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "=":
                    String expression = inputField.getText();
                    try {
                        int result = evaluateExpression(expression);
                        resultLabel.setText("Result: " + result);
                    } catch (ArithmeticException ex) {
                        resultLabel.setText("Error: " + ex.getMessage());
                    } catch (Exception ex) {
                        resultLabel.setText("Error: Invalid Expression");
                    }
                    break;
                case "C":
                    inputField.setText("");
                    resultLabel.setText("Result: ");
                    break;
                case "Del":
                    String currentText = inputField.getText();
                    if (!currentText.isEmpty()) {
                        inputField.setText(currentText.substring(0, currentText.length() - 1));
                    }
                    break;
                case "M+":
                    memoryValue += getResultAsDouble();
                    break;
                case "M-":
                    memoryValue -= getResultAsDouble();
                    break;
                case "MR":
                    inputField.setText(inputField.getText() + memoryValue);
                    break;
                case "MC":
                    memoryValue = 0;
                    break;
                default:
                    inputField.setText(inputField.getText() + command);
                    break;
            }
        }

        private double getResultAsDouble() {
            String resultText = resultLabel.getText().replace("Result: ", "").trim();
            try {
                return Double.parseDouble(resultText);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
    }

    private int evaluateExpression(String expression) {
        return evaluate(expression.replaceAll(" ", ""));
    }

    private int evaluate(String expression) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                int value = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    value = value * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                values.push(value);
                i--;
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private int applyOperator(char operator, int b, int a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new ArithmeticException("Division by zero");
                return a / b;
        }
        return 0;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BasicCalculatorGUI());
    }
}
