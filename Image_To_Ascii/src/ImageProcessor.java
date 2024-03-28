import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;

public class ImageProcessor {

    private Image_Type imageType;
    private String imagePath;

    /**
     * Constructor which sets the image path and creates an instance of Image_Type
     * @param imageFullPath the full path of the image
     */
    public ImageProcessor(String imageFullPath){
        imagePath = imageFullPath;
        imageType = new Image_Type(imagePath);
    }

    /**
     * Get the brightness matrix of the image
     * @return 2D array containing the brightness of each pixel
     */
    public int[][] getBrightnessMatrix(){
        BufferedImage image = getImage(imagePath);
        int[][] pixels = getPixelsFromImage(image);
        return setBrightnessMatrix(pixels);
    }

    /**
     * Get the BufferedImage from the given path
     * @param imageFullPath the full path of the image
     * @return BufferedImage object
     */
    private BufferedImage getImage(String imageFullPath){
        BufferedImage bufferedImage = null;
        try {
            if (imageFullPath == null) {
                throw new NullPointerException("Image full path cannot be null or empty");
            }
            boolean isImage = imageType.isFileAValidImage();
            if (!isImage) {
                throw new ImagingOpException(Image_Type.IMAGE_ALLOW_TYPES);
            }
            String tempImagePath = imageFullPath;
            bufferedImage = ImageIO.read(new File(tempImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    /**
     * Get the pixels of the image as a 2D int array
     * @param bufferedImage the BufferedImage object
     * @return 2D int array of pixels
     */
    private static int[][] getPixelsFromImage(BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            throw new IllegalArgumentException();
        }
        int h = bufferedImage.getHeight();
        int w = bufferedImage.getWidth();
        int[][] pixels = new int[h][w];
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                pixels[j][i] = bufferedImage.getRGB(i, j);
            }
        }
        return pixels;
    }

    /**
     * Calculate the brightness matrix from the given 2D int array of pixels
     * @param pixels 2D int array of pixels
     * @return 2D int array of brightness values
     */
     private static int[][] setBrightnessMatrix(int[][] pixels) {
        int r, g, b;
        int average;
        int[][] avg = new int[pixels.length][pixels[0].length];

        // Iterate through the 2D int array of pixels
        for (int i = 0; i <pixels.length; ++i) {
              for (int j = 0; j < pixels[0].length; ++j) {
            // Extract the red, green and blue values of the current pixel using bit shifting and bit masking
                r = (pixels[i][j]>>16) & 0xff ;
                 g = (pixels[i][j]>>8) & 0xff ;
                 b = (pixels[i][j]) & 0xff ;
            // Calculate the average brightness of the current pixel
                average=(r+g+b)/3;
            // Store the average brightness value in the brightness matrix
              avg[i][j]=average;
            }
      }
    return avg;
  }

}