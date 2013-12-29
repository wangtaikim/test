package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.lang3.time.StopWatch;

/**
 * 
 * @author wangtai
 *
 */

public class MainPanel {

	private static final String START = "Start";
	private static final String STOP = "Stop";
	private static final String RESET = "Reset";
	
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
	
	private StopWatch stopWatch;

	/**
	 * 시간이 표시되는 화면을 갱신하는 클래스 
	 */
	public class PrintTime implements Runnable {

		@Override
		public void run() {
			
			try {
				while( true ) {
					printTime();
					Thread.sleep(50);
				}
			} catch(InterruptedException e) {
				logger.info("thread interrupted");
			}
		}
		
		public void printTime() throws InterruptedException {
			myCanvas.setText(stopWatch.toString());
			myCanvas.repaint();
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
	 * StopWatch 객체를 초기화 한다
	 */
	public void initStopWatch() {
		
		if( stopWatch == null ) {
			stopWatch = new StopWatch();
		}
	}
	
	/**
	 * UI를 초기화 한다
	 */
	public void initUI() {
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

		myCanvas = new MyCanvas();
		jPanelVertical.add(myCanvas);
		jPanelVertical.add(jPanelHorizontal);
		
		jFrame.getContentPane().add(jPanelVertical);
		jFrame.setSize(500, 300);
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
			stopWatch.suspend();
			thread.interrupt();
			
			startJButton.setEnabled(true);
			stopJButton.setEnabled(false);
			resetJButton.setEnabled(true);
			
			logger.info("toString : " + stopWatch.toString());
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
			
			myCanvas.setText("");
			myCanvas.repaint();
			
			startJButton.setEnabled(true);
			stopJButton.setEnabled(false);
			resetJButton.setEnabled(true);
		}
	}; 	// end resetAction
	
}
