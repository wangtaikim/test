package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

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
	
	private Logger logger;
	
	public static void main(String[] args) {
		MainPanel mainPanel = new MainPanel();
		mainPanel.initLogger();
		mainPanel.go();
	}
	
	public void initLogger() {
		logger = Logger.getLogger(MainPanel.class.getName());
	}
	
	public void go() {
		initPanel();
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
		
		startJButton.addActionListener(startAction);
		stopJButton.addActionListener(stopAction);
		resetJButton.addActionListener(resetAction);
		
		jPanelHorizontal.add(startJButton);
		jPanelHorizontal.add(stopJButton);
		jPanelHorizontal.add(resetJButton);
		
		jPanelVertical.add(jPanelHorizontal);
		
		jFrame.getContentPane().add(jPanelVertical);
		jFrame.setSize(500, 300);
		jFrame.setVisible(true);
		
	}	// end initPanel
	
	private ActionListener startAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("startAction");
			startJButton.setEnabled(false);
			stopJButton.setEnabled(true);
			resetJButton.setEnabled(true);
			
		}
	};	// end startAction 
	
	private ActionListener stopAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("stopAction");
			startJButton.setEnabled(true);
			stopJButton.setEnabled(false);
			resetJButton.setEnabled(true);
			
		}
	};	// end stopAction 
	
	private ActionListener resetAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("resetAction");
			startJButton.setEnabled(true);
			stopJButton.setEnabled(false);
			resetJButton.setEnabled(true);
		}
	}; 	// end resetAction
	

	
}
