
import javax.swing.*;
import java.awt.*;

public class SubjectSelection extends JFrame {

    private String studentName;
    private String rollNumber;

    public SubjectSelection(String name, String roll) {

        studentName = name;
        rollNumber = roll;

        setTitle("SmartExam - Subject Selection");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 15));
        panel.setBorder(
            BorderFactory.createEmptyBorder(25, 60, 25, 60)
        );

        JLabel welcome = new JLabel(
            "Welcome, " + studentName,
            SwingConstants.CENTER
        );

        welcome.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel instruction = new JLabel(
            "Choose your examination subject",
            SwingConstants.CENTER
        );

        String[] subjects = {"Java", "SQL", "Python"};

        JComboBox<String> subjectBox = new JComboBox<>(subjects);

        JButton startButton = new JButton("START EXAM");

        startButton.addActionListener(e -> {

            String subject =
                (String) subjectBox.getSelectedItem();

            OnlineExam exam = new OnlineExam(
                  studentName,
                rollNumber,
                subject
            );

            exam.setVisible(true);
            dispose();
        });

        panel.add(welcome);
        panel.add(instruction);
        panel.add(subjectBox);
        panel.add(startButton);

        add(panel);
    }
}