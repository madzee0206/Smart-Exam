import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QRResult {

    public static String generate(
            String name,
            String roll,
            int correct,
            int total,
            double percentage,
            String result) throws Exception {

        String details =
                "SMARTEXAM RESULT\n"
                + "Name: " + name + "\n"
                + "Roll Number: " + roll + "\n"
                + "Correct Answers: " + correct + "\n"
                + "Total Questions: " + total + "\n"
                + "Percentage: " + String.format("%.2f", percentage) + "%\n"
                + "Result: " + result;

        BitMatrix matrix = new MultiFormatWriter().encode(
                details,
                BarcodeFormat.QR_CODE,
                300,
                300
        );

        Path folder = Paths.get("qr_images");
        Files.createDirectories(folder);

        Path file = folder.resolve("result_qr.png");

        MatrixToImageWriter.writeToPath(
                matrix,
                "PNG",
                file
        );

        return file.toAbsolutePath().toString();
    }
}