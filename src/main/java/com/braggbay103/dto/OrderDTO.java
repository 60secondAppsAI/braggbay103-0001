package com.braggbay103.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class OrderDTO {

	private Integer orderId;

	private double totalAmount;

	private Date orderDate;






}
