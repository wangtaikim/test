package com.stopwatch;

import java.text.DecimalFormat;

/**
 * StopWatch 클래스
 * @author wangtai
 *
 */
public class StopWatch {
	// 밀리세컨드로부터 시, 분, 초를 얻기위한 상수 선언
	private static final long ONE_SECOND = 1000L;
	private static final long ONE_MINUTE = ONE_SECOND * 60L;
	private static final long ONE_HOUR = ONE_MINUTE * 60L;

	private long currentMillisecond = 0;
	private long startTime = 0;
	private long elapsedTime = 0;
	
	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public void resume() {
		startTime = System.currentTimeMillis() - elapsedTime;
	}
	
	public void suspend() {
		elapsedTime = System.currentTimeMillis() - startTime;
	}
	
	public void reset() {
		currentMillisecond = 0;
		startTime = 0;
		elapsedTime = 0;		
	}
	
	public long getTime() {
		return currentMillisecond;
	}

	public int getHour() {
		return (int) (currentMillisecond / ONE_HOUR % 24L);
	}

	public int getMinute() {
		return (int) (currentMillisecond / ONE_MINUTE % 60L);
	}

	public int getSecond() {
		return (int) (currentMillisecond / ONE_SECOND % 60L);
	}

	public int getMillisecond() {
		return (int) (currentMillisecond % 1000L);
	}

	public String toString() {
		currentMillisecond = System.currentTimeMillis() - startTime;
		
		StringBuffer sb = new StringBuffer();
		DecimalFormat formatter2 = new DecimalFormat("00");
		DecimalFormat formatter3 = new DecimalFormat("000");

		sb.append(formatter2.format(getHour())).append(":")
				.append(formatter2.format(getMinute())).append(":")
				.append(formatter2.format(getSecond())).append(".")
				.append(formatter3.format(getMillisecond()));

		return sb.toString();
	}
}
