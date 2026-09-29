
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class OnlineExam extends JFrame {

    private ArrayList<Question> questions = new ArrayList<>();
    private int[] answers = new int[10];

    private int current = 0;
    private int timeLeft = 60;
    private boolean submitted = false;

    private JLabel questionLabel;
    private JLabel timerLabel;
    private JLabel numberLabel;

    private JRadioButton[] options = new JRadioButton[4];
    private ButtonGroup group;

    private JButton previousButton;
    private JButton nextButton;
    private JButton submitButton;

    private javax.swing.Timer timer;

    private String studentName;
    private String rollNumber;
    private String subject;

    public OnlineExam(String name, String roll, String selectedSubject) {

        studentName = name;
        rollNumber = roll;
        subject = selectedSubject;

        setTitle("SmartExam - " + subject);
        setSize(850, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Arrays.fill(answers, -1);

        addQuestions();
        createGUI();
        displayQuestion();
        startTimer();
    }

    private void addQuestions() {

        ArrayList<Question> bank = new ArrayList<>();

        if (subject.equalsIgnoreCase("Java")) {

            bank.add(new Question(
                "Which keyword declares a class in Java?",
                new String[]{"class", "new", "struct", "define"}, 0));

            bank.add(new Question(
                "Which method is the entry point of a Java application?",
                new String[]{"start()", "main()", "run()", "init()"}, 1));

            bank.add(new Question(
                "Which keyword is used for class inheritance?",
                new String[]{"implements", "inherits", "extends", "super"}, 2));

            bank.add(new Question(
                "Which collection allows dynamic resizing?",
                new String[]{"Array", "ArrayList", "String", "Integer"}, 1));

            bank.add(new Question(
                "Which Swing component creates a clickable button?",
                new String[]{"JLabel", "JPanel", "JButton", "JFrame"}, 2));

            bank.add(new Question(
                "Which keyword creates an object?",
                new String[]{"new", "this", "static", "void"}, 0));

            bank.add(new Question(
                "Which data type stores true or false?",
                new String[]{"int", "String", "boolean", "double"}, 2));

            bank.add(new Question(
                "Which keyword prevents a class from being inherited?",
                new String[]{"private", "final", "static", "public"}, 1));

            bank.add(new Question(
                "Which method makes a JFrame visible?",
                new String[]{"showFrame()", "display()", "setVisible()", "open()"}, 2));

            bank.add(new Question(
                "Which package contains Java Swing components?",
                new String[]{"java.io", "java.util", "java.awt", "javax.swing"}, 3));

            bank.add(new Question(
                "What is the default value of an int instance variable?",
                new String[]{"null", "0", "1", "undefined"}, 1));

            bank.add(new Question(
                "Which method compares the contents of two Strings?",
                new String[]{"==", "compare()", "equals()", "matches()"}, 2));

            bank.add(new Question(
                "Which block executes whether or not an exception occurs?",
                new String[]{"catch", "throw", "finally", "throws"}, 2));

            bank.add(new Question(
                "Which interface must a class implement to define natural ordering?",
                new String[]{"Comparator", "Comparable", "Iterable", "Cloneable"}, 1));

            bank.add(new Question(
                "What happens when an unchecked exception is not handled?",
                new String[]{
                    "It is ignored",
                    "The current thread terminates",
                    "The program always compiles with an error",
                    "The JVM automatically retries the statement"
                }, 1));

        } else if (subject.equalsIgnoreCase("Python")) {

            bank.add(new Question(
                "Which keyword defines a function in Python?",
                new String[]{"function", "def", "func", "define"}, 1));

            bank.add(new Question(
                "Which data type is immutable?",
                new String[]{"list", "set", "dictionary", "tuple"}, 3));

            bank.add(new Question(
                "What is the result of 7 // 2?",
                new String[]{"3.5", "4", "3", "2"}, 2));

            bank.add(new Question(
                "Which method adds an item to the end of a list?",
                new String[]{"add()", "append()", "insertEnd()", "push()"}, 1));

            bank.add(new Question(
                "What does len('Python') return?",
                new String[]{"5", "6", "7", "Error"}, 1));

            bank.add(new Question(
                "Which keyword is used to handle an exception?",
                new String[]{"catch", "except", "error", "handle"}, 1));

            bank.add(new Question(
                "What is the result of bool([])?",
                new String[]{"True", "False", "None", "Error"}, 1));

            bank.add(new Question(
                "Which expression creates an empty dictionary?",
                new String[]{"[]", "()", "{}", "set()"}, 2));

            bank.add(new Question(
                "What is the output of 2 ** 3?",
                new String[]{"6", "8", "9", "5"}, 1));

            bank.add(new Question(
                "Which keyword creates an anonymous function?",
                new String[]{"lambda", "def", "anonymous", "function"}, 0));

            bank.add(new Question(
                "What is the result of [1, 2, 3][-1]?",
                new String[]{"1", "2", "3", "IndexError"}, 2));

            bank.add(new Question(
                "What does the is operator compare?",
                new String[]{
                    "Only numeric values",
                    "Object identity",
                    "String lengths",
                    "Object types only"
                }, 1));

            bank.add(new Question(
                "What does range(2, 8, 2) generate?",
                new String[]{
                    "2, 4, 6",
                    "2, 4, 6, 8",
                    "2, 3, 4, 5, 6, 7",
                    "4, 6, 8"
                }, 0));

            bank.add(new Question(
                "What is the output of list(set([1, 1, 2])) sorted?",
                new String[]{
                    "[1, 1, 2]",
                    "[1, 2]",
                    "[2, 1, 1]",
                    "Error"
                }, 1));

            bank.add(new Question(
                "Which statement about Python generators is correct?",
                new String[]{
                    "They always store all results in memory",
                    "They cannot be used in loops",
                    "They can produce values lazily using yield",
                    "They must return a list"
                }, 2));

        } else if (subject.equalsIgnoreCase("SQL")) {

            bank.add(new Question(
                "Which SQL command retrieves data from a table?",
                new String[]{"GET", "SELECT", "FETCH ALL", "OPEN"}, 1));

            bank.add(new Question(
                "Which clause filters rows before grouping?",
                new String[]{"HAVING", "ORDER BY", "WHERE", "GROUP BY"}, 2));

            bank.add(new Question(
                "Which command adds a new row to a table?",
                new String[]{"INSERT INTO", "UPDATE", "ALTER", "CREATE"}, 0));

            bank.add(new Question(
                "Which clause sorts query results?",
                new String[]{"SORT BY", "ORDER BY", "GROUP BY", "ARRANGE"}, 1));

            bank.add(new Question(
                "Which function counts non-NULL values in a column?",
                new String[]{"SUM()", "COUNT(column)", "TOTAL()", "SIZE()"}, 1));

            bank.add(new Question(
                "Which keyword removes duplicate rows from query results?",
                new String[]{"UNIQUE", "DISTINCT", "DIFFERENT", "REMOVE"}, 1));

            bank.add(new Question(
                "Which constraint uniquely identifies each row?",
                new String[]{"FOREIGN KEY", "NOT NULL", "PRIMARY KEY", "CHECK"}, 2));

            bank.add(new Question(
                "Which JOIN returns only matching rows from both tables?",
                new String[]{"LEFT JOIN", "FULL JOIN", "INNER JOIN", "CROSS JOIN"}, 2));

            bank.add(new Question(
                "Which clause filters grouped results?",
                new String[]{"WHERE", "HAVING", "ORDER BY", "LIMIT"}, 1));

            bank.add(new Question(
                "Which statement changes existing rows?",
                new String[]{"ALTER", "INSERT", "UPDATE", "CREATE"}, 2));

            bank.add(new Question(
                "How do you test whether a column contains NULL?",
                new String[]{"= NULL", "IS NULL", "== NULL", "EQUALS NULL"}, 1));

            bank.add(new Question(
                "Which aggregate function returns the highest value?",
                new String[]{"TOP()", "HIGH()", "MAX()", "UPPER()"}, 2));

            bank.add(new Question(
                "What does a LEFT JOIN return?",
                new String[]{
                    "Only rows matching in both tables",
                    "All right-table rows",
                    "All left-table rows and matching right-table rows",
                    "Every possible pair of rows"
                }, 2));

            bank.add(new Question(
                "Which SQL command removes a table and its definition?",
                new String[]{"DELETE", "TRUNCATE", "DROP TABLE", "CLEAR"}, 2));

            bank.add(new Question(
                "Which condition correctly finds salaries between 30000 and 50000, inclusive?",
                new String[]{
                    "salary BETWEEN 30000 AND 50000",
                    "salary IN 30000 TO 50000",
                    "salary RANGE 30000, 50000",
                    "salary FROM 30000 TO 50000"
                }, 0));

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Invalid subject: " + subject,
                "Subject Error",
                JOptionPane.ERROR_MESSAGE
            );

            throw new IllegalArgumentException(
                "Unknown subject: " + subject
            );
        }

        Collections.shuffle(bank);

        for (int i = 0; i < 10; i++) {
            questions.add(bank.get(i));
        }
    }

    private void createGUI() {

        setLayout(new BorderLayout(15, 15));

        JPanel top = new JPanel(new GridLayout(2, 1, 5, 5));

        JLabel studentLabel = new JLabel(
            "Student: " + studentName
            + "     Roll No: " + rollNumber
            + "     Subject: " + subject,
            SwingConstants.CENTER
        );

        studentLabel.setFont(
            new Font("SansSerif", Font.BOLD, 16)
        );

        timerLabel = new JLabel(
            "Time Left: 60 seconds",
            SwingConstants.CENTER
        );

        timerLabel.setFont(
            new Font("SansSerif", Font.BOLD, 18)
        );

        top.add(studentLabel);
        top.add(timerLabel);

        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel();

        center.setLayout(
            new BoxLayout(center, BoxLayout.Y_AXIS)
        );

        center.setBorder(
            BorderFactory.createEmptyBorder(25, 40, 20, 40)
        );

        numberLabel = new JLabel();

        numberLabel.setFont(
            new Font("SansSerif", Font.BOLD, 16)
        );

        questionLabel = new JLabel();

        questionLabel.setFont(
            new Font("SansSerif", Font.BOLD, 17)
        );

        center.add(numberLabel);
        center.add(Box.createVerticalStrut(20));
        center.add(questionLabel);
        center.add(Box.createVerticalStrut(20));

        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {

            options[i] = new JRadioButton();

            options[i].setFont(
                new Font("SansSerif", Font.PLAIN, 15)
            );

            group.add(options[i]);

            center.add(options[i]);
            center.add(Box.createVerticalStrut(12));
        }

        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 20, 15)
        );

        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");

        bottom.add(previousButton);
        bottom.add(nextButton);
        bottom.add(submitButton);

        add(bottom, BorderLayout.SOUTH);

        previousButton.addActionListener(e -> {

            saveAnswer();

            if (current > 0) {
                current--;
                displayQuestion();
            }
        });

        nextButton.addActionListener(e -> {

            saveAnswer();

            if (current < questions.size() - 1) {
                current++;
                displayQuestion();
            }
        });

        submitButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to submit?",
                "Confirm Submission",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                submitExam();
            }
        });
    }

    private void displayQuestion() {

        Question q = questions.get(current);

        numberLabel.setText(
            "Question " + (current + 1)
            + " of " + questions.size()
        );

        questionLabel.setText(q.getQuestion());

        group.clearSelection();

        for (int i = 0; i < 4; i++) {
            options[i].setText(q.getOptions()[i]);
        }

        if (answers[current] != -1) {
            options[answers[current]].setSelected(true);
        }

        previousButton.setEnabled(current > 0);

        nextButton.setEnabled(
            current < questions.size() - 1
        );
    }

    private void saveAnswer() {

        for (int i = 0; i < 4; i++) {

            if (options[i].isSelected()) {
                answers[current] = i;
                return;
            }
        }

        answers[current] = -1;
    }

    private void startTimer() {

        timer = new javax.swing.Timer(1000, e -> {

            timeLeft--;

            timerLabel.setText(
                "Time Left: " + timeLeft + " seconds"
            );

            if (timeLeft <= 0) {

                timer.stop();

                submitExam();

                JOptionPane.showMessageDialog(
                    this,
                    "Time is over! Your exam has been submitted."
                );
            }
        });

        timer.start();
    }

    private void submitExam() {

        if (submitted) {
            return;
        }

        submitted = true;
        timer.stop();
        saveAnswer();

        int correct = 0;
        int wrong = 0;
        int unanswered = 0;

        for (int i = 0; i < questions.size(); i++) {

            if (answers[i] == -1) {
                unanswered++;

            } else if (
                answers[i] == questions.get(i).getCorrectAnswer()
            ) {
                correct++;

            } else {
                wrong++;
            }
        }

        double correctMarks = correct * 2.0;
        double negativeMarks = wrong * 0.5;

        double marks = correctMarks - negativeMarks;
        double maximumMarks = questions.size() * 2.0;

        double percentage = (marks / maximumMarks) * 100.0;

        String result = percentage >= 50 ? "PASS" : "FAIL";

        // STEP 4: SAVE RESULT HISTORY

        try {

            ResultHistory.save(
                studentName,
                rollNumber,
                subject,
                correct,
                wrong,
                unanswered,
                marks,
                maximumMarks,
                percentage,
                result
            );

        } catch (java.io.IOException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Could not save result history: "
                    + ex.getMessage(),
                "History Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        // DISPLAY RESULT

        JOptionPane.showMessageDialog(
            this,
            "SMART EXAM RESULT\n\n"
            + "Name: " + studentName + "\n"
            + "Roll Number: " + rollNumber + "\n"
            + "Subject: " + subject + "\n\n"
            + "Total Questions: " + questions.size() + "\n"
            + "Correct Answers: " + correct + "\n"
            + "Wrong Answers: " + wrong + "\n"
            + "Unanswered: " + unanswered + "\n\n"
            + "Correct Answer Marks: +"
            + String.format("%.2f", correctMarks) + "\n"
            + "Negative Marks: -"
            + String.format("%.2f", negativeMarks) + "\n"
            + "Final Score: "
            + String.format("%.2f", marks)
            + " / " + String.format("%.2f", maximumMarks) + "\n"
            + "Percentage: "
            + String.format("%.2f", percentage) + "%\n"
            + "Result: " + result,
            "Examination Result",
            JOptionPane.INFORMATION_MESSAGE
        );

        // GENERATE QR CODE

        try {

            String qrPath = QRResult.generate(
                studentName,
                rollNumber,
                correct,
                questions.size(),
                percentage,
                result
            );

            ImageIcon qrImage = new ImageIcon(qrPath);

            JOptionPane.showMessageDialog(
                this,
                "Your SmartExam result QR code!\n"
                + "Scan it to view your result.",
                "SmartExam QR Code",
                JOptionPane.INFORMATION_MESSAGE,
                qrImage
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                this,
                "QR Code Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        dispose();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
            new OnlineExam(
                "Test Student",
                "TEST001",
                "Java"
            ).setVisible(true)
        );
    }
}