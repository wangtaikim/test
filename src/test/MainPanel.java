package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.time.StopWatch;

public class MainPanel {

	private static final String START = "Start";
	private static final String STOP = "Stop";
	private static final String RESET = "Reset";
	
	private Logger logger;
	
	private	JFrame jFrame;
	private JPanel jPanelVertical;
	private JPanel jPanelHorizontal;
	private	JButton startJButton;
	private	JButton stopJButton;
	private	JButton resetJButton;
	
	private JTextField timeField;
	private Runnable printTimeJob;
	private Thread thread;
	
	private StopWatch stopWatch;
	
	public class PrintTime implements Runnable {

		@Override
		public void run() {
			
			try {
				while( !Thread.currentThread().isInterrupted() ) {
					printTime();
					Thread.sleep(10);
				}
			} catch(InterruptedException e) {
				logger.info("thread interrupted");
			}
		}
		
		public void printTime() throws InterruptedException {
			timeField.setText(stopWatch.toString());
		}
	}
	
	public static void main(String[] args) {
		MainPanel mainPanel = new MainPanel();
		mainPanel.initLogger();
		mainPanel.initStopWatch();
		mainPanel.go();
	}
	
	public void initLogger() {
		logger = Logger.getLogger(MainPanel.class.getName());
	}
	
	public void initStopWatch() {
		
		if( stopWatch == null ) {
			stopWatch = new StopWatch();
		}
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
		stopJButton.setEnabled(false);
		resetJButton = new JButton(RESET);
		
		startJButton.addActionListener(startAction);
		stopJButton.addActionListener(stopAction);
		resetJButton.addActionListener(resetAction);
		
		jPanelHorizontal.add(startJButton);
		jPanelHorizontal.add(stopJButton);
		jPanelHorizontal.add(resetJButton);

		timeField = new JTextField();
		jPanelVertical.add(timeField);
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
			
			if(stopWatch.getTime() != 0) {
				stopWatch.resume();
			} else {
				stopWatch.start();
			}
			
			printTimeJob = new PrintTime();
			thread = new Thread(printTimeJob);
			thread.start();
			
		}
	};	// end startAction 
	
	private ActionListener stopAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("stopAction");
			stopWatch.suspend();
			thread.interrupt();
			
			startJButton.setEnabled(true);
			stopJButton.setEnabled(false);
			resetJButton.setEnabled(true);
			
			logger.info("toString : " + stopWatch.toString());
		}
	};	// end stopAction 
	
	private ActionListener resetAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("resetAction");
			stopWatch.reset();
			if( thread != null ) {
				thread.interrupt();
			}
			
			startJButton.setEnabled(true);
			stopJButton.setEnabled(false);
			resetJButton.setEnabled(true);
			
			timeField.setText("");
		}
	}; 	// end resetAction
	

	
}
