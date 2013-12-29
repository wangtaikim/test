package com.stopwatch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
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
	
	private MyCanvas myCanvas;
	private Runnable printTimeJob;
	private Thread thread;
	
	private CurrentTime currentTime;
	private long startTime = 0;
	private long elapsedTime = 0;

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
			long time = System.currentTimeMillis() - startTime;
			currentTime.setCurrentTime(time);
			myCanvas.setText(currentTime.toString());
			myCanvas.repaint();
		}
		
	}
	
	public static void main(String[] args) {
		MainPanel mainPanel = new MainPanel();
		mainPanel.initLogger();
		mainPanel.initCurrentTime();
		mainPanel.initUI();
		
	}
	
	/**
	 * 로거를 초기화 한다
	 */
	public void initLogger() {
		logger = Logger.getLogger(MainPanel.class.getSimpleName());
	}
	
	/**
	 * CurrentTime 객체를 쵸기화 한다
	 */
	public void initCurrentTime() {
		
		currentTime = new CurrentTime();
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

		myCanvas = new MyCanvas();
		jPanelVertical.add(myCanvas);
		jPanelVertical.add(jPanelHorizontal);
		
		jFrame.getContentPane().add(jPanelVertical);
		jFrame.setSize(FRAME_X, FRAME_Y);
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
			
			if(startTime != 0) {
				startTime = System.currentTimeMillis() - elapsedTime;
			} else {
				startTime = System.currentTimeMillis();
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
			elapsedTime = System.currentTimeMillis() - startTime;
			
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
			startTime = 0;
			elapsedTime = 0;
			if( thread != null ) {
				thread.interrupt();
			}
			
			myCanvas.setText("");
			myCanvas.repaint();
			
			startJButton.setEnabled(true);
			stopJButton.setEnabled(false);
			resetJButton.setEnabled(true);
		}
	}; 	// end resetAction
	
}
