package com.example.CarSellProject.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "EVENT_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "EVENT_SEQUENCE_GENERATOR",
					sequenceName = "seq_event", 
					initialValue = 1, allocationSize = 1)
	private int seq;
    private String eventid;
    private String eventname;
    private String eventsubject;
    private String eventsubject2;
    private String eventcontent;
    private String eventlong;
    private String eventimg;
    private int eventhit;
    @Temporal(TemporalType.DATE)
    private Date logtime;
}
