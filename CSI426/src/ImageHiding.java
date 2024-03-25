import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
 
import java.io.*;
 
import javax.imageio.ImageIO;
 
import javax.swing.*;
 
public class ImageHiding extends JFrame implements ActionListener
{
 
 
 
 //Declaration of BufferedImage For the TWO images
 BufferedImage hostImage;
 BufferedImage secretImage;
 
 //GUI Stuff:
 JPanel controlPanel;
 JPanel imagePanel;
 JTextField encodeBitsText;
 JButton encodeBitsPlus;
 JButton encodeBitsMinus;
 JTextField nBitsText;
 JButton nBitsPlus;
 JButton nBitsMinus;
 ImageCanvas hostCanvas;
 ImageCanvas secretCanvas;
 
 // Radio buttons for operation mode selection
 JRadioButton MSBtoLSB;
 JRadioButton MSBtoMSB;
 JRadioButton LSBtoLSB;
 JRadioButton LSBtoMSB;
 ButtonGroup buttons;
 
 //Steganohraphy Object For Encoding/Decoding Image:
 Steganography s;
 
 //Method to load and return the host image from a file
 public BufferedImage getHostImage()
 {
  BufferedImage img = null;
 
  //Atemppt to read the host image file
  try
  {
   img = ImageIO.read(new File("host_image.jpg"));
  }
  catch (IOException ioe) { ioe.printStackTrace(); }
 
  return img;
 }
 
 
  //Method to load and return the secret image from a file
 public BufferedImage getSecretImage()
 {
 
  BufferedImage img = null;
  try
  {
   img = ImageIO.read(new File("secret_image.jpg"));
  }
  catch (IOException ioe) { ioe.printStackTrace(); }
 
  return img;
 }
 
//Returns the number of bits specified by the user for encoding in text box
 public int getBits()
 {
  return Integer.parseInt(encodeBitsText.getText());
 }
 
 
  //Handles actions performed on GUI components, such as button clicks
// public void actionPerformed(ActionEvent event)
// {
//  Object source = event.getSource();
// 
//  //when + button is tapped
//  if (source == encodeBitsPlus)
//  {
// 
//   //increments bits by 1 each time
//   int bits = this.getBits() + 1;
// 
//   //Bits are bounded below 8
//   if (bits > 8) { bits = 8; }
// 
//   //Updates the value of bits in the text field
//   encodeBitsText.setText(Integer.toString(bits));
// 
//   //New Steganography object contained the host image
//   s = new Steganography(this.getHostImage());
// 
// 
//   s.MSBtoLSB_Encode(this.getSecretImage(), bits);
// 
//   hostCanvas.setImage(s.getImage());
//   hostCanvas.repaint();
// 
//   s = new Steganography(this.getSecretImage());
//   s.MSBtoLSB_Mask(bits);
// 
//   secretCanvas.setImage(s.getImage());
//   secretCanvas.repaint();
//  }
//  else if (source == encodeBitsMinus)
//  {
//   int bits = this.getBits() - 1;
// 
//   if (bits < 0) { bits = 0; }
// 
//   encodeBitsText.setText(Integer.toString(bits));
// 
//   s = new Steganography(this.getHostImage());
//   s.MSBtoLSB_Encode(this.getSecretImage(), bits);
// 
//   hostCanvas.setImage(s.getImage());
//   hostCanvas.repaint();
// 
//   s = new Steganography(this.getSecretImage());
//   s.MSBtoLSB_Mask(bits);
// 
//   secretCanvas.setImage(s.getImage());
//   secretCanvas.repaint();
//  }
// }
// 
 
 //Updated Interface 
 public void actionPerformed(ActionEvent event) {
	    Object source = event.getSource();

	    // Determine the current encoding and masking methods based on the selected radio button
	    String encodeMethod = "";
	    String maskMethod = "";
	    
	    if (MSBtoLSB.isSelected()) {
	        encodeMethod = "MSBtoLSB_Encode";
	        maskMethod = "MSBtoLSB_Mask";
	    } else if (MSBtoMSB.isSelected()) {
	        encodeMethod = "MSBtoMSB_Encode";
	        maskMethod = "MSBtoMSB_Mask";
	    } else if (LSBtoLSB.isSelected()) {
	        encodeMethod = "LSBtoLSB_Encode";
	        maskMethod = "LSBtoLSB_Mask";
	    } else if (LSBtoMSB.isSelected()) {
	        encodeMethod = "LSBtoMSB_Encode";
	        maskMethod = "LSBtoMSB_Mask";
	    }

	    // Increment or decrement bits
	    int bits = this.getBits();
	    if (source == encodeBitsPlus) {
	        bits = Math.min(bits + 1, 8); // Ensure bits do not exceed 8
	    } else if (source == encodeBitsMinus) {
	        bits = Math.max(bits - 1, 0); // Ensure bits do not go below 0
	    }
	    
	    // Update bits text field
	    encodeBitsText.setText(Integer.toString(bits));
	    
	    // Encode and mask images based on the selected method
	    s = new Steganography(this.getHostImage());
	    
	    switch (encodeMethod) {
	        case "MSBtoLSB_Encode":
	            s.MSBtoLSB_Encode(this.getSecretImage(), bits);
	            break;
	        case "MSBtoMSB_Encode":
	            s.MSBtoMSB_Encode(this.getSecretImage(), bits);
	            break;
	        case "LSBtoLSB_Encode":
	            s.LSBtoLSB_Encode(this.getSecretImage(), bits);
	            break;
	        case "LSBtoMSB_Encode":
	            s.LSBtoMSB_Encode(this.getSecretImage(), bits);
	            break;
	    }
	    
	    hostCanvas.setImage(s.getImage());
	    hostCanvas.repaint();
	    
	    s = new Steganography(this.getSecretImage());
	    
	    switch (maskMethod) {
	        case "MSBtoLSB_Mask":
	            s.MSBtoLSB_Mask(bits);
	            break;
	        case "MSBtoMSB_Mask":
	            s.MSBtoMSB_Mask(bits);
	            break;
	        case "LSBtoLSB_Mask":
	            s.LSBtoLSB_Mask(bits);
	            break;
	        case "LSBtoMSB_Mask":
	            s.LSBtoMSB_Mask(bits);
	            break;
	    }
	    
	    secretCanvas.setImage(s.getImage());
	    secretCanvas.repaint();
	}
 
 
 
 
 
 
 public ImageHiding()// Constructor to set up the GUI
 {
  GridBagLayout layout = new GridBagLayout();
  GridBagConstraints gbc = new GridBagConstraints();
  this.setTitle("Image Hiding Demo");
 
  Container container = this.getContentPane();
 
  this.setLayout(layout);
 
  this.add(new JLabel("Bits to encode into host image:"));
 
  encodeBitsText = new JTextField("0", 5);
  encodeBitsText.setEditable(false);
 
  gbc.weightx = -1.0;
  layout.setConstraints(encodeBitsText, gbc);
  this.add(encodeBitsText);
 
  encodeBitsPlus = new JButton("+");
  encodeBitsPlus.addActionListener(this);
 
  encodeBitsMinus = new JButton("-");
  encodeBitsMinus.addActionListener(this);
 
  gbc.weightx = 1.0;
  layout.setConstraints(encodeBitsPlus, gbc);
  this.add(encodeBitsPlus);
 
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(encodeBitsMinus, gbc);
  this.add(encodeBitsMinus);
 
//  gbc.gridwidth = GridBagConstraints.REMAINDER;
//  layout.setConstraints(encodeBitsMinus, gbc);
//  this.add(encodeBitsMinus);
 
 
  gbc = new GridBagConstraints(); // Reset to default constraints
  gbc.gridwidth = GridBagConstraints.REMAINDER; // Each radio button takes up the whole line
  gbc.anchor = GridBagConstraints.WEST; // Align to the left (west)
 
  imagePanel = new JPanel();
  MSBtoLSB = new JRadioButton("MSB to LSB");
  MSBtoLSB.addActionListener(this);
  MSBtoLSB.setSelected(true);
 
  MSBtoMSB = new JRadioButton("MSB to MSB");
  MSBtoMSB.addActionListener(this);
 
  LSBtoLSB = new JRadioButton("LSB to LSB");
  LSBtoLSB.addActionListener(this);
 
  LSBtoMSB = new JRadioButton("LSB to MSB");
  LSBtoMSB.addActionListener(this);
 
  buttons = new ButtonGroup();
  buttons.add(MSBtoLSB);
  buttons.add(MSBtoMSB);
  buttons.add(LSBtoLSB);
  buttons.add(LSBtoMSB);
 
 
 
  gbc.weightx = 1.0;
  layout.setConstraints(MSBtoLSB, gbc);
  this.add(MSBtoLSB);
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(MSBtoMSB, gbc);
  this.add(MSBtoMSB);
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(LSBtoLSB, gbc);
  this.add(LSBtoLSB);
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(LSBtoMSB, gbc);
  this.add(LSBtoMSB);
 
  GridBagLayout imageGridbag = new GridBagLayout();
  GridBagConstraints imageGBC = new GridBagConstraints();
 
//  imagePanel = new JPanel();
  imagePanel.setLayout(imageGridbag);
 
  JLabel hostImageLabel = new JLabel("Host image:");
  JLabel secretImageLabel = new JLabel("Secret image:");
 
  imagePanel.add(hostImageLabel);
 
  imageGBC.gridwidth = GridBagConstraints.REMAINDER;
  imageGridbag.setConstraints(secretImageLabel, imageGBC);
  imagePanel.add(secretImageLabel);
 
  hostCanvas = new ImageCanvas(this.getHostImage());  
  secretCanvas = new ImageCanvas(this.getSecretImage());
 
  imagePanel.add(hostCanvas);
  imagePanel.add(secretCanvas);
 
  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(imagePanel, gbc);
  this.add(imagePanel);
 
  Steganography host = new Steganography(this.getHostImage());
  host.MSBtoLSB_Encode(this.getSecretImage(), this.getBits());
  hostCanvas.setImage(host.getImage());
 
  Steganography secret = new Steganography(this.getSecretImage());
  secret.MSBtoLSB_Mask(this.getBits());
  secretCanvas.setImage(secret.getImage());
 
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.pack();
 
  this.setVisible(true);
 }
 
//Main method to run the application
 public static void main(String[] args)
 {
  ImageHiding frame = new ImageHiding();
  frame.setVisible(true);
 }
 
 // Inner class for custom painting on JPanel. Handles displaying images.
 public class ImageCanvas extends JPanel
 { 
  Image img;
 
  public void paintComponent(Graphics g)
  {
   g.drawImage(img, 0, 0, this);
  }
 
  public void setImage(Image img)
  {
   this.img = img;
  }
 
  public ImageCanvas(Image img)
  {
   this.img = img;
 
   this.setPreferredSize(new Dimension(img.getWidth(this), img.getHeight(this)));
  }
 }
}
 
//Where the magic happens
class Steganography
{
 
  // Constructor to initialize Steganography with a host image	
  public Steganography(BufferedImage image){
	  this.image = image;
  }
 
 BufferedImage image;
 
 //Applies mask to host image to prepare it for embedding the secret image's bit
 //Makes image on the left, removing data from the HOST (left image)
 
 
 
//MSB to LSB Minor change to major change
 public void MSBtoLSB_Mask(int bits) //MSB to LSB
 {
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));
 
  //Creates Mask Makes 7 in binary 0000-0111 to 256 in binary 1110-0000
  int maskBits = (int)(Math.pow(2, bits)) - 1 << (8 - bits); // calculates the value to keep the most significant bits up bits
  int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits; //replicates pattern across color channels
  // 1110-0000 1110-0000 1110-0000 1110-0000
 
  for (int i = 0; i < imageRGB.length; i++) //Image processed pixel by pixel applying mask to each
  {
   imageRGB[i] = imageRGB[i] & mask; // xxx0-0000 xxx0-0000 xxx0-0000 xxx0-0000
  }
 
  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }
 
 //Embeds the secret image ('encodeImage') into HOST image, is a compliment to getMaskedImage, if this is 3, then getMaskedImage is 5
 public void MSBtoLSB_Encode(BufferedImage encodeImage, int encodeBits) 
 {
 
  // Retrieve all the pixel RGB values from the secret image and store them in an array.
  int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
 
  // Retrieve all the pixel RGB values from the host image and store them in a separate array.
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));
  // Calculate a mask to isolate the number of bits (encodeBits) from the secret image's pixels.
  
  int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;
 
  // Calculate a complementary mask for the host image that will be used to clear out space
  // ~ Isolate the 5 MSB
  int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;
 
  for (int i = 0; i < imageRGB.length; i++)
  {
   int encodeData = (encodeRGB[i] & encodeMask) >>> (8 - encodeBits);
   imageRGB[i] = (imageRGB[i] & hostMask) | (encodeData & ~hostMask);
  }
 
  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }
 
 
public void LSBtoMSB_Mask(int bits) {
    int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

    // The mask will keep the least significant bits, different from the MSB methods
    int maskBits = (1 << bits) - 1; // This directly gives us a mask for LSBs
    int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;

    for (int i = 0; i < imageRGB.length; i++) {
        imageRGB[i] = imageRGB[i] & mask; // Applies mask to keep LSBs
    }

    image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
}



 
public void LSBtoMSB_Encode(BufferedImage encodeImage, int encodeBits) {
    int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
    int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

    for (int i = 0; i < imageRGB.length; i++) {
        int encodedPixel = 0;
        for (int shift = 24; shift >= 0; shift -= 8) {
            // Extract 'encodeBits' LSBs from the secret image for each color channel
            int colorChannel = (encodeRGB[i] >> shift) & 0xFF;
            int secretBits = (colorChannel & ((1 << encodeBits) - 1)) << (8 - encodeBits);

            // Clear 'encodeBits' MSBs in the host image's corresponding color channel
            int hostChannel = (imageRGB[i] >> shift) & 0xFF;
            int clearMSBs = hostChannel & ((1 << (8 - encodeBits)) - 1);

            // Combine the modified host channel with the secret's shifted bits
            encodedPixel |= ((clearMSBs | secretBits) << shift);
        }
        imageRGB[i] = encodedPixel;
    }

    image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
}



 public void LSBtoLSB_Mask(int bits) {
	    int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

	    // The mask will keep the least significant bits, different from the MSB methods
	    int maskBits = (1 << bits) - 1; // This directly gives us a mask for LSBs
	    int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;

	    for (int i = 0; i < imageRGB.length; i++) {
	        imageRGB[i] = imageRGB[i] & mask; // Applies mask to keep LSBs
	    }

	    image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
	}

 
 public void LSBtoLSB_Encode(BufferedImage encodeImage, int encodeBits) {
	    int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
	    int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

	    // Masks for extracting and preserving bits
	    int preserveMask = ~((1 << encodeBits) - 1); // Preserve all but the last 'encodeBits' bits
	    int extractMask = (1 << encodeBits) - 1; // Extract only the last 'encodeBits' bits

	    for (int i = 0; i < imageRGB.length; i++) {
	        // Extract the LSBs from the secret image
	        int secretLSBs = encodeRGB[i] & extractMask;
	        // Clear the LSBs of the host image and preserve the rest
	        int preservedHost = imageRGB[i] & preserveMask;
	        // Combine the preserved host bits with the secret's LSBs
	        imageRGB[i] = preservedHost | secretLSBs;
	    }

	    image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
	}



 
 public void MSBtoMSB_Mask(int bits) {
	    int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

	    // This time, the mask is designed to keep the most significant bits
	    // It remains largely the same as in MSBtoLSB, since we are still dealing with MSBs
	    int maskBits = (int)(Math.pow(2, bits)) - 1 << (8 - bits);
	    int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;

	    for (int i = 0; i < imageRGB.length; i++) {
	        imageRGB[i] = imageRGB[i] & mask; // Keeps the MSB, effectively the same operation as MSBtoLSB
	    }

	    image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
	}

 
 public void MSBtoMSB_Encode(BufferedImage encodeImage, int encodeBits) {
	    int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
	    int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

	    int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
	    int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

	    // For MSB to MSB, the mask to clear out space in the host image is different.
	    // Here, we directly use the encodeByteMask without shifting.
	    int hostMask = ~encodeMask;

	    for (int i = 0; i < imageRGB.length; i++) {
	        int encodeData = encodeRGB[i] & encodeMask;
	        imageRGB[i] = (imageRGB[i] & hostMask) | encodeData;
	    }

	    image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
	}

 
 // Gets images
 public Image getImage()
 {
  return image;
 }
 
 
}