// ImageToAscii class to convert an image to ASCII characters

import java.awt.Color;

public class ImageToAscii {

    // ImageProcessor object to process the image
    private ImageProcessor processor;
    // String of ASCII characters to use for converting image to ASCII art
//    private static final String ASCIICHARS = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
    private static final String ASCIICHARS  = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

    // Constructor that takes the full path of the image as input
    public ImageToAscii(String imageFullPath){
        processor = new ImageProcessor(imageFullPath);
    }

    // Method to print the ASCII art
//    public void printAsciiMatrix() {
//        char[][] pixels = setAsciiMatrix(processor.getBrightnessMatrix());
//        // Iterate over each row of the ASCII matrix
//        for (int i = 0; i < pixels.length; i++) {
//            // Iterate over each column of the ASCII matrix
//            for (int j = 0; j < pixels[0].length; j++) {
//                // Print the ASCII character
//                System.out.print(pixels[i][j]);
//            }
//            // Move to the next line
//            System.out.println();
//        }
//    }
    
    public void printAsciiMatrix() {
        char[][] asciiMatrix = setAsciiMatrix(processor.getBrightnessMatrix());
        for (int i = 0; i < asciiMatrix.length; i++) {
            for (int j = 0; j < asciiMatrix[0].length; j++) {
                System.out.print(AsciiColor.getColor(Color.YELLOW) + asciiMatrix[i][j] + AsciiColor.ANSI_RESET);
            }
            System.out.println();
        }
    }

    // Method to set the ASCII matrix using the brightness matrix
    private static char[][] setAsciiMatrix(int[][] brightnessMatrix) {
        char[][] asciiMatrix = new char[brightnessMatrix.length][];
        int rowCount = 0;
        // Iterate over each row of the brightness matrix
        for (int[] row : brightnessMatrix) {
            char[] asciiRow = new char[row.length];
            int cellCount = 0;
            // Iterate over each cell of the brightness matrix
            for (int cell : row) {
                asciiRow[cellCount] = convertToAscii(cell);
                cellCount++;
            }
            asciiMatrix[rowCount] = asciiRow;
            rowCount++;
        }
        return asciiMatrix;
    }

    // Method to convert the brightness value to an ASCII character
//    private static char convertToAscii(int brightnessValue) {
//        char asciiValue;
//        int asciiIndex;
//        // Map the brightness value to an index in the ASCII characters string
//        asciiIndex = (int) ((ASCIICHARS.length() - 1) * (brightnessValue / 255.0));
//        // Get the ASCII character from the string using the index
//        asciiValue = ASCIICHARS.charAt(asciiIndex);
//        return asciiValue;
//    }
    
    private static char convertToAscii(int brightnessValue)
    {
        char asciiValue;
        int asciiIndex;
        asciiIndex = (int) ((ASCIICHARS.length() / 2) * (brightnessValue / 255.0));
        asciiValue = ASCIICHARS.charAt(asciiIndex);
        return asciiValue;
    }
}