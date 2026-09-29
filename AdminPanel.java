
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AdminPanel extends JFrame {

    private static final Path FILE = Paths.get("results.csv");

    private static final String[] COLUMNS = {
        "Date", "Name", "Roll Number", "Subject",
        "Correct", "Wrong", "Unanswered",
        "Score", "Maximum", "Percentage", "Result"
    };

    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField searchField;
    private JComboBox<String> subjectBox;

    private JLabel totalLabel;
    private JLabel passLabel;
    private JLabel failLabel;

    public AdminPanel() {

        setTitle("SmartExam - Admin Panel");
        setSize(1150, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createGUI();
        loadResults();
    }

    private void createGUI() {

        setLayout(new BorderLayout(15, 15));

        JPanel top = new JPanel(
            new GridLayout(3, 1, 10, 10)
        );

        top.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 10, 20)
        );

        JLabel heading = new JLabel(
            "SMARTEXAM ADMIN PANEL",
            SwingConstants.CENTER
        );

        heading.setFont(
            new Font("SansSerif", Font.BOLD, 24)
        );

        top.add(heading);

        JPanel summary = new JPanel(
            new GridLayout(1, 3, 15, 10)
        );

        totalLabel = new JLabel(
            "Total Attempts: 0",
            SwingConstants.CENTER
        );

        passLabel = new JLabel(
            "Passed: 0",
            SwingConstants.CENTER
        );

        failLabel = new JLabel(
            "Failed: 0",
            SwingConstants.CENTER
        );

        summary.add(totalLabel);
        summary.add(passLabel);
        summary.add(failLabel);

        top.add(summary);

        JPanel filters = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 15, 5)
        );

        filters.add(new JLabel("Roll Number:"));

        searchField = new JTextField(15);
        filters.add(searchField);

        filters.add(new JLabel("Subject:"));

        subjectBox = new JComboBox<>(
            new String[]{"All", "Java", "Python", "SQL"}
        );

        filters.add(subjectBox);

        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");
        JButton refreshButton = new JButton("Refresh");

        filters.add(searchButton);
        filters.add(clearButton);
        filters.add(refreshButton);

        top.add(filters);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(COLUMNS, 0) {

            public boolean isCellEditable(
                int row, int column
            ) {
                return false;
            }
        };

        table = new JTable(model);

        table.setRowHeight(27);
        table.setAutoResizeMode(
            JTable.AUTO_RESIZE_OFF
        );

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(
            BorderFactory.createEmptyBorder(0, 20, 10, 20)
        );

        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> applyFilters());

        searchField.addActionListener(e -> applyFilters());

        subjectBox.addActionListener(e -> applyFilters());

        clearButton.addActionListener(e -> {

            searchField.setText("");
            subjectBox.setSelectedItem("All");

            applyFilters();
        });

        refreshButton.addActionListener(e -> loadResults());

        JLabel footer = new JLabel(
            "Results are loaded from results.csv",
            SwingConstants.CENTER
        );

        footer.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 15, 5)
        );

        add(footer, BorderLayout.SOUTH);
    }

    private String[] readRow(String line) {

        ArrayList<String> values = new ArrayList<>();

        StringBuilder value = new StringBuilder();

        boolean quoted = false;

        for (int i = 0; i < line.length(); i++) {

            char ch = line.charAt(i);

            if (ch == '"') {

                if (quoted
                    && i + 1 < line.length()
                    && line.charAt(i + 1) == '"') {

                    value.append('"');
                    i++;

                } else {

                    quoted = !quoted;
                }

            } else if (ch == ',' && !quoted) {

                values.add(value.toString());
                value.setLength(0);

            } else {

                value.append(ch);
            }
        }

        values.add(value.toString());

        return values.toArray(new String[0]);
    }

    private void loadResults() {

        model.setRowCount(0);

        if (!Files.exists(FILE)) {

            updateSummary();

            JOptionPane.showMessageDialog(
                this,
                "No results found yet.\n"
                + "Complete an exam to create results.csv."
            );

            return;
        }

        try (BufferedReader reader =
                Files.newBufferedReader(
                    FILE,
                    StandardCharsets.UTF_8
                )) {

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = readRow(line);

                if (data.length == COLUMNS.length) {

                    model.addRow(data);
                }
            }

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Could not load results: "
                    + ex.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        applyFilters();
    }

    private void applyFilters() {

        String roll = searchField.getText().trim();

        String subject =
            (String) subjectBox.getSelectedItem();

        ArrayList<RowFilter<Object, Object>> filters =
            new ArrayList<>();

        if (!roll.isEmpty()) {

            filters.add(
                RowFilter.regexFilter(
                    "(?i)" + Pattern.quote(roll),
                    2
                )
            );
        }

        if (!"All".equals(subject)) {

            filters.add(
                RowFilter.regexFilter(
                    "^" + Pattern.quote(subject) + "$",
                    3
                )
            );
        }

        if (filters.isEmpty()) {

            sorter.setRowFilter(null);

        } else {

            sorter.setRowFilter(
                RowFilter.andFilter(filters)
            );
        }

        updateSummary();
    }

    private void updateSummary() {

        int total = table.getRowCount();

        int passed = 0;
        int failed = 0;

        for (int i = 0; i < total; i++) {

            String result = table.getValueAt(
                i, 10
            ).toString();

            if (result.equalsIgnoreCase("PASS")) {

                passed++;

            } else if (result.equalsIgnoreCase("FAIL")) {

                failed++;
            }
        }

        totalLabel.setText(
            "Total Attempts: " + total
        );

        passLabel.setText(
            "Passed: " + passed
        );

        failLabel.setText(
            "Failed: " + failed
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JPasswordField passwordField =
                new JPasswordField();

            int choice = JOptionPane.showConfirmDialog(
                null,
                passwordField,
                "SmartExam - Admin Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (choice != JOptionPane.OK_OPTION) {
                return;
            }

            char[] entered = passwordField.getPassword();

            char[] expected = {
                'S', 'm', 'a', 'r', 't', '@', '1', '2', '3'
            };

            boolean valid = java.util.Arrays.equals(
                entered, expected
            );

            java.util.Arrays.fill(entered, '\0');

            if (valid) {

                new AdminPanel().setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                    null,
                    "Incorrect admin password.",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}