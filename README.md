
 SmartExam – Online Examination System

SmartExam is a Java Swing-based desktop application developed to conduct multiple-choice examinations. It provides a simple interface for students to take examinations, view their results and track previous attempts.

The application also includes an admin panel to view and filter student examination results.

 Features

- Student login using name and roll number
- Subject selection: Java, Python and SQL
- 15 questions available for each subject
- Random selection of 10 questions per examination
- Multiple-choice questions using JRadioButton
- Previous and Next buttons for navigation
- 60-second countdown timer
- Automatic submission when time expires
- Negative marking system
- Immediate result calculation
- QR-code generation for examination results
- Student result history
- Admin panel with search and subject filters

 Marking Scheme

| Answer | Marks |
|--------|-------|
| Correct | +2 |
| Incorrect | -0.5 |
| Unanswered | 0 |

Maximum Marks: 20

Passing Percentage: 50%

Technologies Used

- Java
- Java Swing
- Java AWT
- ArrayList
- ZXing Library for QR-code generation
- CSV for storing examination results

 Project Structure

```text
Smart-Exam/
|
|-- src/
|   |-- LoginFrame.java
|   |-- SubjectSelection.java
|   |-- Question.java
|   |-- OnlineExam.java
|   |-- QRResult.java
|   |-- ResultHistory.java
|   |-- AdminPanel.java
|
|-- lib/
|   |-- core-3.5.3.jar
|   |-- javase-3.5.3.jar
|
|-- screenshots/
|
|-- README.md
```

How to Run

 Requirements

- Java Development Kit (JDK)
- Windows Command Prompt or PowerShell
- ZXing JAR files in the lib folder

Compile the Project

Open PowerShell in the project folder and run:

```powershell
javac -cp "lib/*" -d src src\*.java
```

 Run the Student Application

```powershell
java -cp "src;lib/*" LoginFrame
```

 Run the Admin Panel

```powershell
java -cp "src;lib/*" AdminPanel
```

Application Workflow

1. The student enters their name and roll number.
2. The student selects Java, Python or SQL.
3. Ten questions are randomly selected from the chosen subject.
4. The student answers the questions within 60 seconds.
5. The examination is submitted manually or automatically.
6. The system calculates the score using negative marking.
7. The result and its QR code are displayed.
8. The result is saved in a CSV file for future reference.
9. The admin panel can display and filter saved results.

Output Screenshots

Screenshots of the application are available in the screenshots folder.

- Student Login
- Subject Selection
- Examination Window
- Examination Result
- QR Code
- Student Result History
- Admin Panel

 Future Enhancements

- Secure student and administrator authentication
- Database integration
- Additional examination subjects
- Customizable examination duration
- Question management through the admin panel
- Detailed student performance analysis

Conclusion

SmartExam demonstrates the development of a desktop-based online examination system using Java Swing. It combines graphical user interfaces, object-oriented programming, collections, event handling, timers, file handling and QR-code generation in a single application.

Developed By

Madhumitha R
B.S. (Hons.) Computer Science 
Sri Sathya Sai Institute of Higher Learning
Anantapur Campus
