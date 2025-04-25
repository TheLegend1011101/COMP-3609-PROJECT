import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * The StripAnimation class creates an animation from a strip file.
 * Enhanced version with more flexibility for different strips.
 */
public class StripAnimation {
    private Animation animation;
    private int x;          // x position of animation
    private int y;          // y position of animation
    private int width;      // display width
    private int height;     // display height
    private int dx;         // x-axis movement increment
    private int dy;         // y-axis movement increment
    private boolean active; // whether animation is active

    /**
     * Constructor for StripAnimation
     * @param stripPath Path to the strip image file
     * @param frameCount Number of frames in the strip
     * @param frameDelay Delay between frames in milliseconds
     * @param displayWidth Width to display the animation
     * @param displayHeight Height to display the animation
     * @param loop Whether the animation should loop
     */
    public StripAnimation(String stripPath, int frameCount, int frameDelay, 
                         int displayWidth, int displayHeight, boolean loop) {
        this.animation = new Animation(loop);
        this.width = displayWidth;
        this.height = displayHeight;
        this.dx = 0;
        this.dy = 0;
        this.active = false;

        // Load images from strip file
        Image stripImage = ImageManager.loadImage(stripPath);
        int frameWidth = stripImage.getWidth(null) / frameCount;
        int frameHeight = stripImage.getHeight(null);

        for (int i = 0; i < frameCount; i++) {
            BufferedImage frameImage = new BufferedImage(frameWidth, frameHeight, 
                                                        BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) frameImage.getGraphics();
            
            g.drawImage(stripImage, 
                        0, 0, frameWidth, frameHeight,
                        i * frameWidth, 0, (i * frameWidth) + frameWidth, frameHeight,
                        null);
            
            animation.addFrame(frameImage, frameDelay);
        }
    }

    /**
     * Start the animation at specified position
     * @param x Starting x position
     * @param y Starting y position
     */
    public void start(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = true;
        animation.start();
    }

    /**
     * Set the movement vector
     * @param dx x-axis movement increment
     * @param dy y-axis movement increment
     */
    public void setMovement(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Update the animation position and frame
     */
    public void update() {
        if (!isActive()) return;
        
        animation.update();
        x += dx;
        y += dy;
        
        if (!animation.isStillActive()) {
            active = false;
        }
    }

    /**
     * Draw the current animation frame
     * @param g2 Graphics2D context to draw on
     */
    public void draw(Graphics2D g2) {
        if (!isActive()) return;
        g2.drawImage(animation.getImage(), x, y, width, height, null);
    }

    /**
     * Check if animation is active
     * @return true if animation is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Get current x position
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get current y position
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Set display dimensions
     * @param width new display width
     * @param height new display height
     */
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
}