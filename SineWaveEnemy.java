public class SineWaveEnemy extends Enemy {
    private static final int AMPLITUDE = 40;
    private static final double FREQUENCY = 0.1;
    private static final int SPEED = 2;

    private double phase;
    private int baseX;
    // (these are: 75 = 150/2, 225 = 150 + 150/2, etc.)
    public SineWaveEnemy(int startX, int startY, Level level) {
        super(startX, startY, level);
        this.baseX = startX;
        this.phase = 0;
        image = ImageManager.loadBufferedImage("images/enemy-small.png");
        health = 20;
    }

    @Override
    public void update() {
        // x = baseX + (int)(AMPLITUDE * Math.sin(phase));

        int newX = baseX + (int)(AMPLITUDE * Math.sin(phase));

        // Ensure the enemy doesn't go off screen by adjusting left and right boundaries
        // Check left and right edge within bounds (0 <= x <= 600)
        if (newX < 0) {
            newX = 0;
        } else if (newX + 40 > 600) {
            newX = 600 - 40;  // Ensure the right edge doesn't exceed screen width
        }

        x = newX;  // Update the horizontal position
        y += SPEED;
        phase += FREQUENCY;

        shoot();
    }
    // public void draw(Graphics2D g2) {
    //     // Draw the enemy using the loaded image
    //     if (image != null) {
    //         g2.drawImage(image, x, y, width, height, null);
    //     } else {
    //         g2.setColor(Color.RED); // Fallback color if image is not loaded
    //         g2.fillRect(x, y, width, height); // Draw a rectangle as a placeholder
    //     }
    // }

}
