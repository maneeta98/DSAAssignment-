package com.example.dsaassignmentindv;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class BasicCalculatorGUI extends JFrame {

    private final JTextField inputField;

    public BasicCalculatorGUI() {
        // Set up the frame
        setTitle("Basic Calculator GUI");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input field for the mathematical expression
        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setEditable(false);
        add(inputField, BorderLayout.NORTH);

        // Panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        // Create buttons for digits and operations
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "(", ")", "C", "CE"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    // Evaluate the expression using a custom algorithm
    private int evaluateExpression(String expression) {
        Stack<Integer> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int num = 0;
        int sign = 1;
        boolean numBuffer = false;

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
                numBuffer = true;
            } else if (ch == '+') {
                if (numBuffer) {
                    numbers.push(sign * num);
                    numBuffer = false;
                    num = 0;
                }
                sign = 1;
            } else if (ch == '-') {
                if (numBuffer) {
                    numbers.push(sign * num);
                    numBuffer = false;
                    num = 0;
                }
                sign = -1;
            } else if (ch == '(') {
                operators.push(ch);
                if (numBuffer) {
                    numbers.push(sign * num);
                    numBuffer = false;
                    num = 0;
                }
                numbers.push(sign);
                sign = 1;
            } else if (ch == ')') {
                if (numBuffer) {
                    numbers.push(sign * num);
                    numBuffer = false;
                    num = 0;
                }
                sign = 1;
                int sum = 0;
                while (!operators.isEmpty() && operators.peek() != '(') {
                    sum += numbers.pop();
                }
                operators.pop(); // Pop the '()'
                sum += numbers.pop() * numbers.pop();
                numbers.push(sum);
            } else if (ch == '*' || ch == '/') {
                if (numBuffer) {
                    numbers.push(sign * num);
                    numBuffer = false;
                    num = 0;
                }
                operators.push(ch);
                sign = 1;
            }
        }
        if (numBuffer) {
            numbers.push(sign * num);
        }

        int result = 0;
        while (!numbers.isEmpty()) {
            result += numbers.pop();
        }
        return result;
    }

    // Action listener for the buttons
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "=" -> {
                    try {
                        String expression = inputField.getText();
                        int result = evaluateExpression(expression);
                        inputField.setText(String.valueOf(result));
                    } catch (Exception ex) {
                        inputField.setText("Error");
                    }
                }
                case "C" -> inputField.setText("");
                case "CE" -> {
                    String text = inputField.getText();
                    if (!text.isEmpty()) {
                        inputField.setText(text.substring(0, text.length() - 1));
                    }
                }
                default -> inputField.setText(inputField.getText() + command);
            }
        }
    }

    public static void main(String[] args) {
        // Create and display the calculator GUI
        SwingUtilities.invokeLater(() -> {
            BasicCalculatorGUI calculator = new BasicCalculatorGUI();
            calculator.setVisible(true);
        });
    }
}
