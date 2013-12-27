package test;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel {

	private static final String START = "Start";
	private static final String STOP = "Stop";
	private static final String RESET = "Reset";
	
	private	JFrame jFrame;
	private JPanel jPanelVertical;
	private JPanel jPanelHorizontal;
	private	JButton startJButton;
	private	JButton stopJButton;
	private	JButton resetJButton;
	
	public static void main(String[] args) {
		MainPanel mainPanel = new MainPanel();
		mainPanel.initPanel();
	}
	
	public void initPanel() {
		jFrame = new JFrame();
		jPanelVertical = new JPanel();
		jPanelVertical.setLayout(new BoxLayout(jPanelVertical, BoxLayout.Y_AXIS));
		jPanelHorizontal = new JPanel();
		jPanelHorizontal.setLayout(new BoxLayout(jPanelHorizontal, BoxLayout.X_AXIS));
		
		startJButton = new JButton(START);
		stopJButton = new JButton(STOP);
		resetJButton = new JButton(RESET);
		
		jPanelHorizontal.add(startJButton);
		jPanelHorizontal.add(stopJButton);
		jPanelHorizontal.add(resetJButton);
		
		jPanelVertical.add(jPanelHorizontal);
		
		jFrame.getContentPane().add(jPanelVertical);
		jFrame.setSize(500, 300);
		jFrame.setVisible(true);
	}
	
}
