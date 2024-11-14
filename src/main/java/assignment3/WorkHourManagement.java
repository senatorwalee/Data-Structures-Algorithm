package assignment3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class WorkHourManagement extends Application {
    private TextField nameField, roleField, hoursField;
    private Label errorLabel;
    // Olawale: declaring a text field area variable
    private TextArea displayArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Input fields
        nameField = new TextField();
        nameField.setPromptText("Enter Name");

        roleField = new TextField();
        roleField.setPromptText("Enter Role (Professor, TA, Student)");

        hoursField = new TextField();
        hoursField.setPromptText("Enter Total Hours Worked");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                // Jesse: Create file object for opening file to be written on
                String filePath = "./work_hours.txt";
                File outputFile = new File(filePath);
                handleSubmit(filePath);
            } catch (IOException exc) {
                errorLabel.setText("Error: Unable to access the file. Please check if it exists.");
                System.exit(0);
            }
        });

        // Error Label
        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        // Olawale: New TextArea for displaying file content, it is declared in this
        // start() so
        // it loads automatically when the app launches
        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefHeight(400);
        // olawale: calling the method responsible for reading the file, adjust string
        // path as required
        readHoursFile("assignment3/src/main/java/assignment3/TextFiles/work_hours.txt");

        // Layout
        VBox vbox = new VBox(10, nameField, roleField, hoursField, submitButton, errorLabel);
        vbox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(vbox);
        // Olawale adding Text Area to the bottom of the border pane
        root.setCenter(displayArea);

        // Set scene and show
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Work Hours Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSubmit(String filePath) throws IOException {
        // Get input
        String name = nameField.getText().trim();
        String role = roleField.getText().trim();
        String hoursText = hoursField.getText().trim();

        // Validation
        if (name.isEmpty() || role.isEmpty() || hoursText.isEmpty()) {
            errorLabel.setText("\nError: All fields must be filled in.");
            // displayArea.appendText("\nError: All fields must be filled in.");
            return;
        }

        // Validate role as Professor, TA or Student only
        if (!role.equalsIgnoreCase("Professor") && !role.equalsIgnoreCase("TA") && !role.equalsIgnoreCase("Student")) {
            errorLabel.setText("\nError: Role must be either Professor, TA, or Student.");
            return;
        }

        int hours;
        try {
            hours = Integer.parseInt(hoursText);

            // Validation for non-negative hours
            if (hours <= 0) {
                errorLabel.setText("\nError: Hours must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("\nError: Hours must be an integer.");
            return;
        }

        // If validation succeeds, append new entry
        Person person = new Person(name, role, hours);

        // Jesse: Add new entry to work_hours.txt
        writeToFile(person, filePath);

        // Olawale: reloads when there is a new entry in the file and displays the
        // updated content into the TextArea
        readHoursFile(filePath);

        errorLabel.setText("");

        // Clear input fields
        nameField.clear();
        roleField.clear();
        hoursField.clear();

    }

    // Olawale: Method to read the content of the file and display it in the
    // TextArea
    private void readHoursFile(String file) {
        // clearing existing text in the text area
        displayArea.clear();
        try {
            // creating a file object from the File class where the path of the file to be
            // read from will be saved
            // read from will be saved
            File file1 = new File(file);
            // creaing a scanner object to read file content from the file object(file1)
            // that was created previously
            Scanner readInput = new Scanner(file1);

            // using the hasNextLine() of the File class to read through every line of the
            // file
            while (readInput.hasNextLine()) {
                String data = readInput.nextLine();
                // Olawale: using the appendText() of the TextArea class to append each line to
                // the text area
                displayArea.appendText(data + "\n");

            }
            // closing scanner after use
            readInput.close();

        }
        // utilizing file not found exception from the FileNotFoundException class to
        // log errors that may arise when trying to read from file
        catch (FileNotFoundException e) {
            // updating the errorLabel object with a user-friendly msg
            errorLabel.setText("Error: File not found.");

        }

    }

    // Jesse: Create method for new entry to be written to file
    private void writeToFile(Person person, String filePath) throws IOException {

        try (FileWriter fw = new FileWriter(filePath, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter output = new PrintWriter(bw)) {
            output.println(person.getRole() + "," + person.getName() + "," + person.getHours());
        } catch (IOException e) {
            errorLabel.setText("Cannot write to file");
            throw new IOException(e.getMessage());
        }

    }
}
