package com.braggbay103.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ItemDTO {

	private Integer itemId;

	private String title;

	private String description;

	private double startingPrice;

	private double currentPrice;

	private Date endTime;






}
