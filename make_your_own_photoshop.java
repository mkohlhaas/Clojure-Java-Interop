// Great! You came to edit a photo! The challenge is to make a grayscale version of the pic.png (see in the side menu).
//
// SETUP:
//
// 1. Select the FORK button (at the top).
//
// 2. Select pic.png (in the side menu). Select Main.java to get back to the code.
//
// 3. Select the RUN button (at the top).
//
// 4. Notice a new file was created in the side menu. Click to open it.
//
// 5. Notice it looks the same as the original.
//
// CODING:
//
// 1. In the code below, scroll down to  // START YOUR CODE HERE.
//
// 2. Change the red, green and blue subpixels to turn the image grayscale.
//
// 3. Run again and open new file again.
//
// Is it grayscale? It might take a few tries.
//
// HINT:
// What do all gray pixels have in common? In a gray pixel, what is the most dominant subpixel: red, green, blue, or none of them?

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {

    File f = new File("pic.png");

    // Create a BufferedImage (2D array) of the pic.
    BufferedImage image = ImageIO.read(f);

    // Loop through image and update every pixel.
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++){

        Color old = new Color(image.getRGB(x, y));

        //////////////////////////////////////
        //  START YOUR CODE HERE

        int red   = old.getRed();      // update the red
        int green = old.getGreen();  // update the green
        int blue  = old.getBlue();    // update the blue

        // END OF YOUR CODE
        ///////////////////////////////////////

        // Create a new Color
        Color newColor = new Color(red, green, blue);

        // Update the BufferedImage with the new pixel
        image.setRGB(x, y, newColor.getRGB());
      }
    }

    // Save updated image to a file
    File fo = new File(f.getName().replace(".png", "_new.png"));
    ImageIO.write(image, "PNG", fo);
    System.out.println("New file at " + fo.getAbsolutePath());
  }
}
