import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */ 

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine a collagePicture as a 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels   
    // A pixel has three components (red, green, and blue) that define the color 
    // of the pixel on the screen.
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage (String filename) {

        //1
        this.collageDimension = 4;
        this.tileDimension = 150;

        //2 
        originalPicture = new Picture(filename);

        //3
        // make collage to be a black height by height pixel picture
        int height = collageDimension * tileDimension;
        collagePicture = new Picture(height, height);

        //4
        //squish original picture into a black height by height pixel collage
        scale(height, height, originalPicture, collagePicture);
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */    
    public Collage (String filename, int td, int cd) {

    // set default values of collageDimension to cd and tileDimension to td
    this.tileDimension = td;
    this.collageDimension = cd;
    
    // initializes originalPicture with the filename image
    this.originalPicture = new Picture(filename);
    
    // initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension,
    // where each pixel is black (see all constructors for the Picture class)
    this.collagePicture = new Picture(tileDimension * collageDimension, tileDimension * collageDimension);
    for (int x = 0; x < collagePicture.width(); x++) {
        for (int y = 0; y < collagePicture.height(); y++) {
            collagePicture.set(x, y, new Color(0, 0, 0));
        }
    }
    
    // update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
    Picture scaledPicture = this.originalPicture.scale(tileDimension * collageDimension, tileDimension * collageDimension);
    for (int x = 0; x < scaledPicture.width(); x++) {
        for (int y = 0; y < scaledPicture.height(); y++) {
            Color c = scaledPicture.get(x, y);
            this.collagePicture.set(x, y, c);
        }
    }
    }


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
     * @param source is the image to be scaled.
     * @param target is the 
     */
    public static void scale (Picture source, Picture target) {

        int sourceWidth = source.width();
        int sourceHeight = source.height();
        int targetWidth = target.width();
        int targetHeight = target.height();
    
        double xFactor = (double)sourceWidth / targetWidth;
        double yFactor = (double)sourceHeight / targetHeight;
    
        for (int y = 0; y < targetHeight; y++) {
            for (int x = 0; x < targetWidth; x++) {
                int sourceX = (int) (x * xFactor);
                int sourceY = (int) (y * yFactor);
                Color c = source.get(sourceX, sourceY);
                target.set(x, y, c);
            }
        }
    }

     /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    public void makeCollage () {

        int tileWidth = originalPicture.width() / collageDimension;
        int tileHeight = originalPicture.height() / collageDimension;
        for (int i = 0; i < collageDimension; i++) {
            for (int j = 0; j < collageDimension; j++) {
                Picture tile = originalPicture.crop(j * tileWidth, i * tileHeight, tileWidth, tileHeight);
                scale(tile, tilePicture);
                collagePicture.copy(tilePicture, j * tileDimension, i * tileDimension);
            }
        }
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {

    // Extract the tile from the collagePicture
    int tileX = collageCol * tileDimension;
    int tileY = collageRow * tileDimension;
    Picture tile = collagePicture.crop(tileX, tileY, tileDimension, tileDimension);
    
    // Loop through each pixel in the tile and apply the color separation algorithm
    for (int x = 0; x < tileDimension; x++) {
        for (int y = 0; y < tileDimension; y++) {
            Color pixel = tile.get(x, y);
            int red = pixel.getRed();
            int green = pixel.getGreen();
            int blue = pixel.getBlue();
            int gray = (red + green + blue) / 3;

            if (component.equals("red")) {
                tile.set(x, y, new Color(red, gray, gray));
            } else if (component.equals("green")) {
                tile.set(x, y, new Color(gray, green, gray));
            } else if (component.equals("blue")) {
                tile.set(x, y, new Color(gray, gray, blue));
            }
        }
    }
    
    // Set the pixels of the tile to the new colorized values
    for (int x = 0; x < tileDimension; x++) {
        for (int y = 0; y < tileDimension; y++) {
            collagePicture.set(tileX + x, tileY + y, tile.get(x, y));
        }
    }
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {

    // Load the image from the file
    Picture newTile = new Picture(filename);
    
    // Calculate the dimensions of a tile in the collage
    int tileWidth = collagePicture.width() / collageDimension;
    int tileHeight = collagePicture.height() / collageDimension;
    
    // Calculate the pixel coordinates of the top-left corner of the tile to replace
    int startX = collageCol * tileWidth;
    int startY = collageRow * tileHeight;
    
    // Copy the pixels from the new tile to the appropriate location in the collage
    for (int x = 0; x < tileWidth; x++) {
        for (int y = 0; y < tileHeight; y++) {
            int sourceX = x * newTile.width() / tileWidth;
            int sourceY = y * newTile.height() / tileHeight;
            Color color = newTile.get(sourceX, sourceY);
            collagePicture.set(startX + x, startY + y, color);
        }
    }
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void grayscaleTile (int collageCol, int collageRow) {

    // Get the starting x and y position of the tile in collagePicture
    int startX = collageCol * tileDimension;
    int startY = collageRow * tileDimension;
    
    // Iterate through each pixel in the tile and make it grayscale
    for (int x = startX; x < startX + tileDimension; x++) {
        for (int y = startY; y < startY + tileDimension; y++) {
            Color color = collagePicture.get(x, y);
            int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
            Color grayscale = new Color(avg, avg, avg);
            collagePicture.set(x, y, grayscale);
        }
    }
    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return a grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}
