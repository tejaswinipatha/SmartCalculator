import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmartCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput = new StringBuilder();
    private double result = 0;
    private char lastOperator = ' ';

    public SmartCalculator() {
        setTitle("Smart Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));

        for (int i = 1; i <= 9; i++) {
            addButton(buttonPanel, String.valueOf(i));
        }

        addButton(buttonPanel, "0");
        addButton(buttonPanel, ".");
        addButton(buttonPanel, "C");
        addButton(buttonPanel, "B");
        addButton(buttonPanel, "+");
        addButton(buttonPanel, "-");
        addButton(buttonPanel, "*");
        addButton(buttonPanel, "/");
        addButton(buttonPanel, "=");

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.addActionListener(this);
        panel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "C":
                clear();
                break;
            case "B":
                backspace();
                break;
            case "=":
                calculate();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                applyOperator(command.charAt(0));
                break;
            default:
                currentInput.append(command);
                display.setText(currentInput.toString());
        }
    }

    private void clear() {
        currentInput.setLength(0);
        result = 0;
        lastOperator = ' ';
        display.setText("");
    }

    private void backspace() {
        int length = currentInput.length();
        if (length > 0) {
            currentInput.deleteCharAt(length - 1);
            display.setText(currentInput.toString());
        }
    }

    private void calculate() {
        try {
            double number = Double.parseDouble(currentInput.toString());
            if (lastOperator != ' ') {
                switch (lastOperator) {
                    case '+': result += number; break;
                    case '-': result -= number; break;
                    case '*': result *= number; break;
                    case '/': 
                        if (number == 0) {
                            display.setText("Error: Division by zero");
                            clear();
                            return;
                        }
                        result /= number;
                        break;
                }
            } else {
                result = number;
            }
            display.setText(String.valueOf(result));
            currentInput.setLength(0);
            lastOperator = ' ';
        } catch (NumberFormatException ex) {
            display.setText("Error: Invalid Input");
            clear();
        }
    }

    private void applyOperator(char operator) {
        try {
            double number = Double.parseDouble(currentInput.toString());
            if (lastOperator != ' ') {
                calculate();
            } else {
                result = number;
            }
            currentInput.setLength(0);
            lastOperator = operator;
            display.setText(result + " " + operator);
        } catch (NumberFormatException ex) {
            display.setText("Error: Invalid Input");
            clear();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SmartCalculator calculator = new SmartCalculator();
            calculator.setVisible(true);
        });
    }
}
