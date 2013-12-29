package test;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

/**
 * 
 * @author wangtai
 *
 */

public class MyCanvas extends JComponent {

	private String text = "";
	
	public void setText(String text) {
		MainPanel.logger.info("setText()");
		this.text = text;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		MainPanel.logger.info("paintComponent()");
		if(g instanceof Graphics2D) {
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	        					RenderingHints.VALUE_ANTIALIAS_ON);

	        g2.drawString(text,70,20);
		}
	}
}