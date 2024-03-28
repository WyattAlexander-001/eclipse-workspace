import java.io.File;

public class Image_Type {

    private static String imageFullPath;
    private static final String IMAGE_EXT_JPG = "jpg";
    private static final String IMAGE_EXT_JPEG = "jpeg";
    private static final String IMAGE_EXT_PNG = "png";
    private static final String IMAGE_EXT_GIF = "gif";

    // constant string to hold allowed image types
    public static final String IMAGE_ALLOW_TYPES = "Image types allowed - " + IMAGE_EXT_JPG + IMAGE_EXT_JPEG
            + IMAGE_EXT_PNG + IMAGE_EXT_GIF;

    // constructor to set the full path of the image
    public Image_Type(String imageFullPath){
        this.imageFullPath = imageFullPath;
    }

    // method to check if the image file is of the allowed type
    public boolean isFileAValidImage() {
        // validate if imageFullPath is not null or empty
        if (imageFullPath== null) {
            throw new NullPointerException("Image full path cannot be null or empty");
        }
        // get the file using the path
        File imageFile = new File(imageFullPath);
        // get the extension of the image file, using helper method
        String ext = getFileExtension(imageFile);
        // check if the extension is one of the allowed types
        if (IMAGE_EXT_GIF.equalsIgnoreCase(ext) || IMAGE_EXT_JPEG.equalsIgnoreCase(ext)
                || IMAGE_EXT_JPG.equalsIgnoreCase(ext) || IMAGE_EXT_PNG.equalsIgnoreCase(ext)) {
            return true;
        }
        return false;
    }

    // helper method to get the extension of the file
    private static String getFileExtension(File imageFile) {
        // validate if imageFile is not null
        if (imageFile == null) {
            throw new NullPointerException("Image file cannot be null");
        }
        // get the name of the file
        String name = imageFile.getName();
        // find the last dot in the name to extract the extension
        int lastDotIndex = name.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < (name.length() - 1)) {
            // return the extension in lowercase
            return name.substring(lastDotIndex + 1).toLowerCase();
        }
        // return an empty string if no extension is found
        return "";
    }

}