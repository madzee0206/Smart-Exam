
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ResultHistory extends JFrame {

    private static final Path FILE = Paths.get("results.csv");

    private static final String[] COLUMNS = {
        "Date", "Name", "Roll Number", "Subject",
        "Correct", "Wrong", "Unanswered",
        "Score", "Maximum", "Percentage", "Result"
    };

    public static void save(
        String name,
        String roll,
        String subject,
        int correct,
        int wrong,
        int unanswered,
        double score,
        double maximum,
        double percentage,
        String result
    ) throws IOException {

        boolean newFile =
            Files.notExists(FILE) || Files.size(FILE) == 0;

        try (BufferedWriter writer = Files.newBufferedWriter(
                FILE,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            if (newFile) {
                writer.write(String.join(",", COLUMNS));
                writer.newLine();
            }

            String date = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            );

            String[] data = {
                date, name, roll, subject,
                String.valueOf(correct),
                String.valueOf(wrong),
                String.valueOf(unanswered),
                String.format(java.util.Locale.US, "%.2f", score),
                String.format(java.util.Locale.US, "%.2f", maximum),
                String.format(java.util.Locale.US, "%.2f", percentage),
                result
            };

            for (int i = 0; i < data.length; i++) {
                if (i > 0) {
                    writer.write(",");
                }

                writer.write("\"" +
                    data[i].replace("\"", "\"\"") + "\"");
            }

            writer.newLine();
        }
    }

    private static String[] readRow(String line) {

        ArrayList<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean quoted = false;

        for (int i = 0; i < line.length(); i++) {

            char ch = line.charAt(i);

            if (ch == '"') {

                if (quoted && i + 1 < line.length()
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

    public ResultHistory(String studentName, String rollNumber) {

        setTitle("SmartExam - Result History");
        setSize(1050, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel(
            "Previous Results - " + studentName,
            SwingConstants.CENTER
        );

        heading.setFont(
            new Font("SansSerif", Font.BOLD, 20)
        );

        add(heading, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
            COLUMNS, 0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(26);

        add(new JScrollPane(table), BorderLayout.CENTER);

        if (Files.exists(FILE)) {

            try (BufferedReader reader = Files.newBufferedReader(
                    FILE, StandardCharsets.UTF_8)) {

                reader.readLine();

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] data = readRow(line);

                    if (data.length == COLUMNS.length
                            && data[2].equalsIgnoreCase(rollNumber)) {

                        model.addRow(data);
                    }
                }

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "Could not read result history: "
                        + ex.getMessage()
                );
            }
        }

        JLabel footer = new JLabel(
            model.getRowCount() + " previous attempt(s)",
            SwingConstants.CENTER
        );

        add(footer, BorderLayout.SOUTH);
    }
}