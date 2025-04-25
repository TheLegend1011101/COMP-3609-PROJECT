// import java.awt.Graphics2D;
// import java.awt.Dimension;
// import java.awt.Image;
// import javax.swing.JPanel;
// import javax.swing.ImageIcon;

// public class Background {
//   	private Image bgImage;
//   	private int bgImageWidth;      		// width of the background (>= panel Width)

// 	private Dimension dimension;

//  	private int bgX;
// 	private int backgroundX;
// 	private int backgroundX2;
// 	private int bgDX;			// size of the background move (in pixels)


// 	public Background(JPanel panel, String imageFile, int bgDX) {

//     		this.bgImage = loadImage(imageFile);
//     		bgImageWidth = bgImage.getWidth(null);	// get width of the background

// 		System.out.println ("bgImageWidth = " + bgImageWidth);

// 		dimension = panel.getSize();

// 		if (bgImageWidth < dimension.width)
//       			System.out.println("Background width < panel width");

//     		this.bgDX = bgDX;

//   	}


//   	public void moveRight() {

// 		if (bgX == 0) {
// 			backgroundX = 0;
// 			backgroundX2 = bgImageWidth;			
// 		}

// 		bgX = bgX - bgDX;

// 		backgroundX = backgroundX - bgDX;
// 		backgroundX2 = backgroundX2 - bgDX;

// 		if ((bgX + bgImageWidth) % bgImageWidth == 0) {
// 			System.out.println ("Background change: bgX = " + bgX); 
// 			backgroundX = 0;
// 			backgroundX2 = bgImageWidth;
// 		}

//   	}


//   	public void moveLeft() {
	
// 		if (bgX == 0) {
// 			backgroundX = bgImageWidth * -1;
// 			backgroundX2 = 0;			
// 		}

// 		bgX = bgX + bgDX;
				
// 		backgroundX = backgroundX + bgDX;	
// 		backgroundX2 = backgroundX2 + bgDX;

// 		if ((bgX + bgImageWidth) % bgImageWidth == 0) {
// 			//System.out.println ("Background change: bgX = " + bgX); 
// 			backgroundX = bgImageWidth * -1;
// 			backgroundX2 = 0;
// 		}			
//    	}
 

//   	public void draw (Graphics2D g2) {
// 		g2.drawImage(bgImage, backgroundX, 0, null);
// 		g2.drawImage(bgImage, backgroundX2, 0, null);
//   	}


//   	public Image loadImage (String fileName) {
// 		return new ImageIcon(fileName).getImage();
//   	}

// }


import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Background {
    private Image bgImage;
    private int bgImageHeight;       // height of the background (>= panel height)

    private Dimension dimension;

    private int bgY;
    private int backgroundY;
    private int backgroundY2;
    private int bgDY;                // size of the background move (in pixels)

    public Background(JPanel panel, String imageFile, int bgDY) {
        this.bgImage = loadImage(imageFile);
        bgImageHeight = bgImage.getHeight(null);   // get height of the background

        System.out.println("bgImageHeight = " + bgImageHeight);

        dimension = panel.getSize();

        if (bgImageHeight < dimension.height)
            System.out.println("Background height < panel height");

        this.bgDY = bgDY;
    }

    public void moveDown() {
        if (bgY == 0) {
            backgroundY = 0;
            backgroundY2 = -bgImageHeight;   // Start the second image just above the first one
        }

        bgY = bgY + bgDY;

        backgroundY = backgroundY + bgDY;
        backgroundY2 = backgroundY2 + bgDY;

        if ((bgY) % bgImageHeight == 0) {
            System.out.println("Background change: bgY = " + bgY);
            backgroundY = 0;
            backgroundY2 = -bgImageHeight;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(bgImage, 0, backgroundY, null);
        g2.drawImage(bgImage, 0, backgroundY2, null);
    }

    public Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }
}
