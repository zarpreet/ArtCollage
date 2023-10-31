import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */ 

public class Collage {

    // The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
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
        original = new Picture(filename);

        //3
        // make collage to be a black height by height pixel picture
        int height = collageDimension * tileDimension;
        collage = new Picture(height, height);

        //4
        //squish original picture into a black height by height pixel collage
        scale(height, height, original, collage);
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

        this.collageDimension = cd;
        this.tileDimension = td;
        original = new Picture(filename);
        int widthHeight = tileDimension * collageDimension;
        collage = new Picture(widthHeight, widthHeight);
        scale(widthHeight, widthHeight, original, collage);
    }


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
     * @param source is the image to be scaled.
     * @param target is the 
     */
    public static void scale(int w, int h, Picture source, Picture target) {

        for (int tcol = 0; tcol < w; tcol++) {
            for (int trow = 0; trow < h; trow++)
            {
                int scol = tcol * source.width() / w;
                int srow = trow * source.height() / h;
                Color color = source.get(scol, srow);
                target.set(tcol, trow, color);
            }
        }
    }

    public static void copy(int row, int col, Picture source, Picture target) {
        for (int i = 0; i < source.height(); i++) {
            for (int j = 0; j < source.height(); j++) {

                // bug
                // Color color = source.get(i+col, j+row);
                Color color = source.get(i, j);
                target.set(i+col, j+row, color);
            }
        }   
    }

    public static double lum(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();   
        // int gray = (int)Math.round(.299*r + .587*g + .114*b); 
        // return gray;
        return .299*r + .587*g + .114*b;
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
        return original;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collage;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        original.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collage.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    public void makeCollage () {

        //scaledOriginal = the mini version of the original picture
        Picture scaledOriginal = new Picture(tileDimension, tileDimension);
        scale(tileDimension, tileDimension, original, scaledOriginal);
                            
        // copy the mini version of original into the collage a bunch of times
        for (int i = 0; i < collage.height()/tileDimension; i++) {
            for (int j = 0; j < collage.height()/tileDimension; j++) {
                    //i * 600 = i * collage.height()
                copy(i*tileDimension, j*tileDimension, scaledOriginal, collage);
                //collage.show();
                // System.out.print(i*target.height());
                // System.out.print(", ");
                // System.out.print(j*target.height());
            }

            // System.out.println();
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

        for( int i = 0; i < collage.height()/tileDimension; i++) {
            for (int j = 0; j < collage.height()/tileDimension; j++) {
                //System.out.println(j);
                 // go through each row and column to check if each tile args is the same as the input and call the setRGB method to change each 
                if ( j == collageCol && i == collageRow ) {
                    //Picture pictureTile = new Picture(i,j);

                    for (int pixelRow = 0; pixelRow < tileDimension ; pixelRow++) {

                        for (int pixelCol = 0; pixelCol < tileDimension; pixelCol++) {
                            // will go through each individual pixel and iterate through until the tile dimension has been reached and change the color 
                            //System.out.println(pixelCol);
                            Color color = collage.get(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i));
                            // need to figure out a way to get the color components of a pixel
                            int r = color.getRed();
                            int g = color.getGreen();
                            int b = color.getBlue();
                                if(component.equals("red")) {
                                    collage.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(r, 0, 0));
                                }
                                else if(component.equals("green")) {
                                    collage.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(0, g, 0));
                                }
                                else if(component.equals("blue")){
                                    collage.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), new Color(0, 0, b));
                                }
                        }
                    }
                }
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

        Picture replacement = new Picture(filename); // will create a replacement picture from the argument given from the command line
        Picture scaledReplacement = new Picture( tileDimension, tileDimension );
        // creates the scaled image from the new image to fit the tile existing 

        scale(tileDimension, tileDimension, replacement, scaledReplacement);
        // scales the image to the tile dimension u want using the scale method

        for(int i = 0; i< collage.height()/tileDimension; i++) {
            for(int j = 0; j< collage.height()/tileDimension; j++) {
                if ( j == collageCol && i == collageRow ) {
                    //copy the image and replace it into this tile
                    copy (i*tileDimension, j*tileDimension, scaledReplacement, collage);
                }
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

        // same logic of capturing each individual color from each thing/ pixel and getting the components and then calling lum on every pixel
        for( int i = 0; i < collage.height()/tileDimension; i++) {
            for (int j = 0; j < collage.height()/tileDimension; j++) {
                if ( j == collageCol && i == collageRow ) {
                    //Picture pictureTile = new Picture(i,j);
                    for (int pixelRow = 0; pixelRow < tileDimension ; pixelRow++) {

                        for (int pixelCol = 0; pixelCol < tileDimension; pixelCol++) {
                            // get the components of color of each pixel
                            Color color = collage.get(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i));
                            int y = (int) Math.round(lum(color));
                            Color gray = new Color(y, y, y);
                            collage.set(pixelCol + (tileDimension * j), pixelRow + (tileDimension * i), gray);
                        }
                    }
                }
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
        if ( original != null ) {
            original.closeWindow();
        }
        if ( collage != null ) {
            collage.closeWindow();
        }
    }
}
