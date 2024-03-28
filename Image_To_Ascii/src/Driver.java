public class Driver {
    public static void main(String[] args) {

        // Create an object of ImageToAscii class and pass the image path as an argument
        ImageToAscii asciiImage = new ImageToAscii(args[0]);

        // Call the printAsciiMatrix method to print the ASCII art
        asciiImage.printAsciiMatrix();
    }
}

/*
javac ImageToAscii.java
javac Image_Type.java
javac ImageProcessor.java
javac Driver.java

java Driver <path_to_image>

*/