// import java.awt.*;
// import java.awt.event.*;
// import javax.swing.*;
// import java.util.ArrayList;
// import java.util.List;
// import java.awt.image.BufferedImage;
// import java.util.Iterator;
// public class GamePanel extends JPanel implements Runnable {
//     private Thread gameThread;
//     private boolean isRunning;
// 	private BufferedImage image;
//     private Player player;
//     private List<Bullet> bullets;
//     private List<Enemy> enemies;
// 	private boolean leftPressed, rightPressed, upPressed, downPressed, shootPressed;

//     public GamePanel() {
// 		leftPressed = false;
// 		rightPressed = false;
// 		upPressed = false;
// 		downPressed = false;
// 		shootPressed = false;
// 		image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
//         isRunning = false;
//         player = new Player();
//         bullets = new ArrayList<>();
//         enemies = new ArrayList<>();
//         // Spawn some enemies
//         for (int i = 0; i < 5; i++) {
//             for (int j = 0; j < 3; j++) {
//                 enemies.add(new Enemy(50 + i * 100, 50 + j * 50));
//             }
//         }
//     }

//     public void startGame() {
//         if (gameThread == null || !isRunning) {
//             isRunning = true;
//             gameThread = new Thread(this);
//             gameThread.start();
//         }
//     }

//     public void run() {
//         try {
//             while (isRunning) {
//                 gameUpdate();
//                 gameRender();
//                 Thread.sleep(30);  // Control frame rate
//             }
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }

//     public void gameUpdate() {
// 		if (leftPressed && upPressed) {
// 			moveLeft();
// 			moveUp();
// 		}
// 		else if (leftPressed && downPressed) {
// 			moveLeft();
// 			moveDown();
// 		}
// 		else if (rightPressed && upPressed) {
// 			moveRight();
// 			moveUp();
// 		}
// 		else if (rightPressed && downPressed) {
// 			moveRight();
// 			moveDown();
// 		}
// 		else {
// 			if (leftPressed) {
// 				moveLeft();
// 			}
// 			if (rightPressed) {
// 				moveRight();
// 			}
// 			if (upPressed) {
// 				moveUp();
// 			}
// 			if (downPressed) {
// 				moveDown();
// 			}
// 		}
// 		if (shootPressed) shoot();
// 		List<Bullet> bulletsToRemove = new ArrayList<>();
// 		List<Enemy> enemiesToRemove = new ArrayList<>();

// 		// Update and check bullets
// 		for (Bullet bullet : bullets) {
// 			bullet.update();
// 			if (bullet.isOffScreen()) {
// 				bulletsToRemove.add(bullet);  // Bullet goes off-screen, mark for removal
// 			}
// 		}

// 		for (Enemy enemy : enemies) {
// 			enemy.update();  // Ensure enemies are moving
// 			if (enemy.isOffScreen()) {
// 				enemiesToRemove.add(enemy);  // Mark enemies that are off-screen for removal
// 			}
// 		}

// 		// Check for collisions between bullets and enemies
// 		for (Bullet bullet : bullets) {
// 			for (Enemy enemy : enemies) {
// 				if (bullet.getBounds().intersects(enemy.getBounds())) {  // Check for collision
// 					bulletsToRemove.add(bullet);   // Remove the bullet
// 					enemiesToRemove.add(enemy);   // Remove the enemy
// 					// You can add any other logic here, like scoring or playing a sound effect
// 				}
// 			}
// 		}

// 		// Remove bullets and enemies that collided or went off-screen
// 		bullets.removeAll(bulletsToRemove);
// 		enemies.removeAll(enemiesToRemove);
// 	}

//     public void gameRender() {
// 		// Draw everything to the off-screen buffer first
// 		Graphics2D imageContext = (Graphics2D) image.getGraphics();
// 		imageContext.clearRect(0, 0, image.getWidth(), image.getHeight());  // Clear the buffer

// 		// Draw the player
// 		imageContext.setColor(Color.BLUE);
// 		imageContext.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());

// 		// Draw the bullets
// 		imageContext.setColor(Color.RED);
// 		for (Bullet bullet : bullets) {
// 			imageContext.fillRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
// 		}

// 		// Draw the enemies
// 		imageContext.setColor(Color.GREEN);
// 		for (Enemy enemy : enemies) {
// 			imageContext.fillRect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
// 		}

// 		// After drawing to the buffer, now draw the buffer to the screen
// 		Graphics2D g2d = (Graphics2D) getGraphics();
// 		g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);  // Copy the buffer to the screen

// 		g2d.dispose();  // Release the graphics context
// 	}

//     public void moveLeft() {
//         player.moveLeft();
//     }

//     public void moveRight() {
//         player.moveRight();
//     }

// 	public void moveUp() {
// 		player.moveUp();
// 	}

// 	public void moveDown() {
// 		player.moveDown();
// 	}
//     public void shoot() {
//         bullets.add(player.shoot());
//     }

// 	public void setLeftPressed(boolean pressed) {
// 		leftPressed = pressed;
// 		// if (pressed) moveLeft();
// 	}
// 	public void setRightPressed(boolean pressed) {
// 		rightPressed = pressed;
// 		// if (pressed) moveRight();
// 	}
// 	public void setUpPressed(boolean pressed) {
// 		upPressed = pressed;
// 		// if (pressed) moveUp();
// 	}
// 	public void setDownPressed(boolean pressed) {
// 		downPressed = pressed;
// 		// if (pressed) moveDown();
// 	}
// 	public void setShootPressed(boolean pressed){
// 		shootPressed = pressed;
// 		// if (pressed) shoot();
// 	}
// }

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private Level currentLevel;
    private boolean isRunning;
    private Thread gameThread;
    private int level = 1;

    public GamePanel() {
        isRunning = false;
        // currentLevel = new SpaceLevel(level, this);  // Start with level 1
        // currentLevel = new PlatformLevel(1);  // Start with level 1
        // currentLevel = new testtLevel();  // Start with level 1
        // currentLevel = new testtLevel(600, 500, 20);
        // currentLevel = new PlatformerLevel();
    }

    public void run() {
        try {
            isRunning = true;
            while (isRunning) {
                currentLevel.update(); // Update the current level
                gameRender();
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // @Override
    // protected void paintComponent(Graphics g) {
    // super.paintComponent(g);
    // currentLevel.draw(g); // Draw the current level
    // }

    public void gameRender() {
        currentLevel.draw((Graphics2D) getGraphics());
    }

    public void startGame() {
        if (gameThread == null) {
            currentLevel = new SpaceLevel(1, this); // Start with level 1
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    // public void moveLeft() {
    // currentLevel.getPlayer().moveLeft();
    // }

    // public void moveRight() {
    // currentLevel.getPlayer().moveRight();
    // }

    // public void shoot() {
    // Bullet bullet = currentLevel.getPlayer().shoot();
    // currentLevel.addBullet(bullet);
    // }

    public void handleKeyPress(KeyEvent e) {
        currentLevel.handleKeyPress(e); // Send input to Level
    }

    public void handleKeyRelease(KeyEvent e) {
        currentLevel.handleKeyRelease(e); // Send input to Level
    }

    public void nextLevel() {
        currentLevel = new SpaceLevel(2, this); // Get the next level
        if (currentLevel != null) {
            // currentLevel.start(); // Start the new level
        } else {
            System.out.println("No more levels available.");
        }
    }
}
