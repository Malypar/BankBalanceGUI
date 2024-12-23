import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankBalanceGUI extends JFrame {
    private final BankAccount account;
    private final JLabel balanceLabel;
    private final JTextField amountField;

    public BankBalanceGUI() {
        account = new BankAccount(0.0);

        setTitle("Bank Account Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel balancePanel = new JPanel();
        balancePanel.setBorder(BorderFactory.createTitledBorder("Current Balance"));
        balanceLabel = new JLabel("Balance: $0.00");
        balancePanel.add(balanceLabel);

        JPanel actionPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        JLabel amountLabel = new JLabel("Enter An Amount to Deposit or Withdraw:");
        amountField = new JTextField(10);

        JButton depositButton = new JButton("Deposit Funds");
        JButton withdrawButton = new JButton("Withdraw Funds");
        JButton exitButton = new JButton("Exit Menu");

        actionPanel.add(amountLabel);
        actionPanel.add(amountField);
        actionPanel.add(depositButton);
        actionPanel.add(withdrawButton);
        actionPanel.add(exitButton);

        add(balancePanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);

        depositButton.addActionListener(new DepositListener());
        withdrawButton.addActionListener(new WithdrawListener());
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Final Balance: $" + String.format("%,.2f", account.getBalance())
            );
            System.exit(0);
        });

        setVisible(true);
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + String.format("%,.2f", account.getBalance()));
        amountField.setText("");
    }

    private class DepositListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                account.deposit(amount);
                updateBalance();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(BankBalanceGUI.this, "Invaliud input. Please enter a valid number.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(BankBalanceGUI.this, ex.getMessage());
            }
        }
    }

    private class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                account.withdraw(amount);
                updateBalance();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(BankBalanceGUI.this, "Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(BankBalanceGUI.this, ex.getMessage());
            }
        }
    }
}
