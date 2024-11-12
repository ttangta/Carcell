package com.example.CarSellProject.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StopWatch {
	private LocalDateTime startTime=null;
	private LocalDateTime stopTime=null;
	
	// 생성자
	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getStopTime() {
		return stopTime;
	}

	public void setStopTime(LocalDateTime stopTime) {
		this.stopTime = stopTime;
	}
	
	// 시작시간을 지정하는 메소드
	public void start() {
		this.startTime = LocalDateTime.now();
	}	
	// 종료시간을 지정하는 메소드
	public void stop() {
		this.stopTime = LocalDateTime.now();
	}

	// 총 소요시간 계산
	public String getElapsedTime() {
		if(startTime == null || stopTime == null) {
			return "N/A";
		}
		Duration duration = Duration.between(startTime, stopTime);
		long seconds = duration.getSeconds();
		long minutes = seconds / 60;
		seconds = seconds % 60;
		long hours = minutes / 60;
		minutes = minutes % 60;
		return String.format("%02d:%02d:%02d",hours,minutes,seconds);
	}
	
	// 시작 시간을 가져오는 메서드
	public String getStartTime_str() {
		if(startTime == null) {
			return "N/A";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return startTime.format(formatter);
	}
	
	// 종료 시간을 가져오는 메서드
	public String getStopTime_str() {
		if(stopTime == null) {
			return "N/A";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return stopTime.format(formatter);
	}
}
