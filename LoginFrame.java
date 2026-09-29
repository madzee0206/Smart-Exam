
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final Color NAVY = new Color(18, 32, 58);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT_BG = new Color(244, 247, 251);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(45, 55, 72);

    private JTextField nameField;
    private JTextField rollField;

    public LoginFrame() {

        setTitle("SmartExam - Online Examination System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createGUI();
    }

    private void createGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(LIGHT_BG);
        mainPanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(NAVY);
        headerPanel.setLayout(
                new BoxLayout(headerPanel, BoxLayout.Y_AXIS)
        );

        JLabel logoLabel = new JLabel("SmartExam");
        logoLabel.setFont(
                new Font("SansSerif", Font.BOLD, 34)
        );
        logoLabel.setForeground(WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(
                "ONLINE EXAMINATION SYSTEM"
        );
        subtitleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 15)
        );
        subtitleLabel.setForeground(
                new Color(190, 210, 240)
        );
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(Box.createVerticalStrut(30));
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalStrut(30));

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(LIGHT_BG);
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel(
                "Welcome! Enter your details to begin.",
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 16)
        );
        welcomeLabel.setForeground(TEXT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(welcomeLabel, gbc);

        JLabel nameLabel = new JLabel("Student Name");
        nameLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );
        nameLabel.setForeground(TEXT);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(280, 38));
        nameField.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(nameField, gbc);

        JLabel rollLabel = new JLabel("Roll Number");
        rollLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );
        rollLabel.setForeground(TEXT);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(rollLabel, gbc);

        rollField = new JTextField();
        rollField.setPreferredSize(new Dimension(280, 38));
        rollField.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(rollField, gbc);

        JButton startButton = new JButton("START EXAM");
        startButton.setFont(
                new Font("SansSerif", Font.BOLD, 15)
        );
        startButton.setBackground(BLUE);
        startButton.setForeground(WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(
                new Dimension(220, 45)
        );

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(startButton, gbc);

        startButton.addActionListener(e -> startExam());

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(LIGHT_BG);

        JLabel footerLabel = new JLabel(
                "Secure  •  Simple  •  Smart"
        );
        footerLabel.setFont(
                new Font("SansSerif", Font.ITALIC, 13)
        );
        footerLabel.setForeground(
                new Color(100, 110, 125)
        );

        footerPanel.add(footerLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void startExam() {

        String studentName = nameField.getText().trim();
        String rollNumber = rollField.getText().trim();

        if (studentName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your name.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            nameField.requestFocus();
            return;
        }

        if (rollNumber.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your roll number.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            rollField.requestFocus();
            return;
        }

        SubjectSelection selection = new SubjectSelection(
                studentName,
                rollNumber
        );

        selection.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);

        });
    }
}