// import java.awt.Graphics2D;
// import java.awt.Point;
// import java.awt.Rectangle;
// import java.awt.Color;

// public class BezierEnemy extends Enemy {

//     private double t;
//     private double incr = 0.02;
//     private Point p0, p1, p2;
//     private boolean movingBack = false;

//     public BezierEnemy(int startX, int startY) {
//         super(startX, startY);
//         // Define control points for the U-shaped Bezier curve
//         p0 = new Point(startX, startY);
//         p1 = new Point(startX - 100, startY + 200); // Adjust for curve shape
//         p2 = new Point(startX, startY + 400); // End of the U
//         t = 0;
//     }

//     @Override
//     public void update() {
//         if (!movingBack) {
//             t += incr;
//             if (t > 1) {
//                 t = 1;
//                 movingBack = true;
//             }
//         } else {
//             t -= incr;
//             if (t < 0) {
//                 t = 0;
//                 movingBack = false;
//             }
//         }

//         int newX = (int) ((1 - t) * (1 - t) * p0.x + 2 * (1 - t) * t * p1.x + t * t * p2.x);
//         int newY = (int) ((1 - t) * (1 - t) * p0.y + 2 * (1 - t) * t * p1.y + t * t * p2.y);

//         //Move the enemy to the new position
//         setX(newX);
//         setY(newY);

//     }

//     @Override
//     public void draw(Graphics2D g2) {
//         g2.setColor(Color.RED); // Change color to differentiate
//         g2.fillRect(getX(), getY(), getWidth(), getHeight());
//     }

//     // Add setters for x and y to allow updating from the bezier calculation.
//     // public void setX(int x) {
//     //     this.x = x;
//     // }

//     // public void setY(int y) {
//     //     this.y = y;
//     // }

//     @Override
//     public Rectangle getBounds() {
//         return new Rectangle(getX(), getY(), getWidth(), getHeight());
//     }

// }



import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;

// public class BezierEnemy extends Enemy {

//     private double t;
//     private double incr = 0.02;
//     private Point p0, p1, p2;
//     private boolean movingBack = false;
//     private int verticalSpeed = 2; // Constant downward speed

//     public BezierEnemy(int startX, int startY) {
//         super(startX, startY);
//         // Define control points for the side-to-side U-shaped Bezier curve
//         p0 = new Point(startX, startY);
//         p1 = new Point(startX + 200, startY - 100); // Adjust for curve shape
//         p2 = new Point(startX + 400, startY-200); // End of the U
//         t = 0;
//     }

//     @Override
//     public void update() {
//         if (!movingBack) {
//             t += incr;
//             if (t > 1) {
//                 t = 1;
//                 movingBack = true;
//             }
//         } else {
//             t -= incr;
//             if (t < 0) {
//                 t = 0;
//                 movingBack = false;
//             }
//         }

//         // Update control points for downward motion
//         // p0.y += verticalSpeed;
//         // p1.y += verticalSpeed;
//         // p2.y += verticalSpeed;

//         int newX = (int) ((1 - t) * (1 - t) * p0.x + 2 * (1 - t) * t * p1.x + t * t * p2.x);
//         int newY = (int) ((1 - t) * (1 - t) * p0.y + 2 * (1 - t) * t * p1.y + t * t * p2.y);

//         //Move the enemy to the new position
//         setX(newX);
//         setY(newY);
//     }

//     @Override
//     public void draw(Graphics2D g2) {
//         g2.setColor(Color.RED); // Change color to differentiate
//         g2.fillRect(getX(), getY(), getWidth(), getHeight());
//     }

//     @Override
//     public Rectangle getBounds() {
//         return new Rectangle(getX(), getY(), getWidth(), getHeight());
//     }
// }


public class BezierEnemy extends Enemy {

    private double t;
    private double incr = 0.02;
    private Point p0, p1, p2;
    private boolean movingBack = false;

    public BezierEnemy(int startX, int startY, Level level) {
        super(startX, startY, level);

        int screenWidth = 600; // Fixed screen width
        int centerX = screenWidth / 2;

        // Define start and end points
        p0 = new Point(startX, startY);
        p2 = new Point(screenWidth - startX, startY); // Mirrored horizontally

        // Adjust curve depth for a deeper U
        double distanceFromCenter = Math.abs(startX - centerX);
        double maxCurveDepth = 200; // Increased max depth
        double curveDepth = maxCurveDepth * (distanceFromCenter / centerX) + 50; // Ensures min depth

        // Midpoint control, now lower for deeper U
        p1 = new Point((p0.x + p2.x) / 2, startY + (int) curveDepth * 3);

        t = 0;
    }

    @Override
    public void update() {
        if (!movingBack) {
            t += incr;
            if (t > 1) {
                t = 1;
                movingBack = true;
            }
        } else {
            t -= incr;
            if (t < 0) {
                t = 0;
                movingBack = false;
            }
        }

        int newX = (int) ((1 - t) * (1 - t) * p0.x + 2 * (1 - t) * t * p1.x + t * t * p2.x);
        int newY = (int) ((1 - t) * (1 - t) * p0.y + 2 * (1 - t) * t * p1.y + t * t * p2.y);

        setX(newX);
        setY(newY);
        shoot();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}



