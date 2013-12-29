package com.stopwatch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * main클래스. 스탑워치 GUI를 제공한다.
 * @author wangtai
 *
 */

public class MainPanel {

	private static final String PROGRAM_NAME = "Stop Watch";
	private static final String START = "Start";
	private static final String STOP = "Stop";
	private static final String RESET = "Reset";
	private static final int FRAME_X = 400;
	private static final int FRAME_Y = 200;
	
	public static Logger logger;
	
	private	JFrame jFrame;
	private JPanel jPanelVertical;
	private JPanel jPanelHorizontal;
	private	JButton startJButton;
	private	JButton stopJButton;
	private	JButton resetJButton;
	
	private TimeComponent timeComponent;
	private Runnable printTimeJob;
	private Thread thread;
	
	private StopWatch stopWatch;

	/**
	 * 표시되는 시간값을 변경해주는 클래스
	 */
	public class PrintTime implements Runnable {

		@Override
		public void run() {
			
			try {
				while( true ) {
					printTime();
					Thread.sleep(10);
				}
			} catch(InterruptedException e) {
				logger.info("thread interrupted");
			}
		}
		
		public void printTime() throws InterruptedException {
			timeComponent.setText(stopWatch.toString());
			timeComponent.repaint();
		}
		
	}
	
	public static void main(String[] args) {
		MainPanel mainPanel = new MainPanel();
		mainPanel.initLogger();
		mainPanel.initStopWatch();
		mainPanel.initUI();
		
	}
	
	/**
	 * 로거를 초기화 한다
	 */
	public void initLogger() {
		logger = Logger.getLogger(MainPanel.class.getSimpleName());
	}
	
	/**
	 * StopWatch 객체를 쵸기화 한다
	 */
	public void initStopWatch() {
		
		stopWatch = new StopWatch();
	}
	
	/**
	 * UI를 초기화 한다
	 */
	public void initUI() {
		jFrame = new JFrame(PROGRAM_NAME);
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

		timeComponent = new TimeComponent();
		jPanelVertical.add(timeComponent);
		jPanelVertical.add(jPanelHorizontal);
		
		jFrame.getContentPane().add(jPanelVertical);
		jFrame.setSize(FRAME_X, FRAME_Y);
		jFrame.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) { 
                System.exit(0);
	    	}
		});
		jFrame.setVisible(true);
		
	}	// end initUI
	
	/**
	 * Start 버튼  리스너
	 */
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
	
	/**
	 * Stop 버튼 리스너
	 */
	private ActionListener stopAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("stopAction");
			
			thread.interrupt();
			stopWatch.suspend();
			
			startJButton.setEnabled(true);
			stopJButton.setEnabled(false);
			resetJButton.setEnabled(true);
			
		}
	};	// end stopAction 
	
	/**
	 * Reset 버튼 리스너
	 */
	private ActionListener resetAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("resetAction");
			stopWatch.reset();
			if( thread != null ) {
				thread.interrupt();
			}
			
			timeComponent.setText("");
			timeComponent.repaint();
			
			startJButton.setEnabled(true);
			stopJButton.setEnabled(false);
			resetJButton.setEnabled(true);
		}
	}; 	// end resetAction
	
}
