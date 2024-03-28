import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Driver {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        // Create the main window (JFrame)
        JFrame frame = new JFrame("ASCII Art Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);

        // Create a panel to hold components
        JPanel panel = new JPanel();
        frame.add(panel);

        // Create a button to open the file chooser
        JButton openButton = new JButton("Select Image");
        panel.add(openButton);

        // Add action listener to the button
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select an Image");
                // Filter for image files
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                    // Process the selected image and print ASCII art to console
                    ImageToAscii asciiImage = new ImageToAscii(selectedFile.getAbsolutePath());
                    asciiImage.printAsciiMatrix();
                }
            }
        });

        // Display the window
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }
}


/*
javac ImageToAscii.java
javac Image_Type.java
javac ImageProcessor.java
javac Driver.java

java Driver <path_to_image>

*/