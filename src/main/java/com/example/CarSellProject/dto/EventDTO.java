package com.example.CarSellProject.dto;

import java.util.Date;

import com.example.CarSellProject.entity.Event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EventDTO {
	private int seq;
    private String eventid;
    private String eventname;
    private String eventsubject;
    private String eventsubject2;
    private String eventcontent;
    private String eventlong;
    private String eventimg;
    private int eventhit;
    private Date logtime; 
    
    public Event toEntity() {
    	return new Event(seq, eventid, eventname, eventsubject, eventsubject2, eventcontent, eventlong, eventimg, eventhit, logtime);
    }
}
