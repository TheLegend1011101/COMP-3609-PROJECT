// import java.awt.Graphics2D;
// import java.awt.Rectangle;
// import java.util.ArrayList;
// import java.util.List;
// import java.awt.Image;
// import java.awt.Color;

// public class AnimatedEnemy extends Enemy {
//     private Animation alienAnimation;
//     private int width = 40;
//     private int height = 40;

//     public AnimatedEnemy(int startX, int startY, Level level) {
//         super(startX, startY, level);
//         loadAnimation();
//     }

//     public AnimatedEnemy(int startX, int startY) {
//         super(startX, startY);
//         loadAnimation();
//     }

//     private void loadAnimation() {
//         alienAnimation = new Animation(true); // Loop the animation

//         // Load the two alien PNG images
//         Image alien1 = ImageManager.loadImage("images/space__0000_A1.png");
//         Image alien2 = ImageManager.loadImage("images/space__0001_A2.png");

//         // Add frames to the animation with a certain duration (milliseconds per frame)
//         long frameDuration = 150; // Adjust for animation speed

//         if (alien1 != null && alien2 != null) {
//             alienAnimation.addFrame(alien1, frameDuration);
//             alienAnimation.addFrame(alien2, frameDuration);
//             alienAnimation.start(); // Start the animation
//         } else {
//             System.err.println("Error loading alien animation frames!");
//         }
//     }

//     @Override
//     public void update() {
//         super.update(); // Keep the basic enemy update (movement, shooting)
//         alienAnimation.update(); // Update the animation frame
//     }

//     @Override
//     public void draw(Graphics2D g2) {
//         Image currentFrame = alienAnimation.getImage();
//         if (currentFrame != null) {
//             g2.drawImage(currentFrame, x, y, width, height, null);
//         } else {
//             // Fallback drawing if animation fails to load
//             g2.setColor(Color.MAGENTA);
//             g2.fillRect(x, y, width, height);
//         }
//     }

//     @Override
//     public int getWidth() {
//         return width; // Use the defined width for collision
//     }

//     @Override
//     public int getHeight() {
//         return height; // Use the defined height for collision
//     }
// }