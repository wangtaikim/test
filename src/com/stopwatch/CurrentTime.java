package com.stopwatch;

import java.text.DecimalFormat;

public class CurrentTime {
	// 밀리세컨드로부터 시, 분, 초를 얻기위한 상수 선언
	private static final long ONE_SECOND = 1000L;
	private static final long ONE_MINUTE = ONE_SECOND * 60L;
	private static final long ONE_HOUR = ONE_MINUTE * 60L;

	private long currentMillisecond;

	public void setCurrentTime(long currentTime) {
		currentMillisecond = currentTime;
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
