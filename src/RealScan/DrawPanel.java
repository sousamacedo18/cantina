package RealScan;



import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JPanel;

public class DrawPanel extends Canvas {

	private static final long serialVersionUID = 1L;
	
	public boolean WINDOW = true;
	
	public Image main_img = null;
	
	boolean drawFlag = false;

	public DrawPanel()
	{
            setVisible(true);
	}
	
        @Override
	public void update(Graphics g)
	{
            Point p = getLocation();
            Dimension size = this.getSize();
            if (main_img != null) {
                g.drawImage( main_img, p.x + 1, p.y + 1, size.width - 1,
                             size.height - 1, this);
                g.dispose();
                repaint();
            }
	}
}
