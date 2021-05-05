package com.sanvic.model;

import lombok.Data;

@Data
public class LocationStats {

	private String state;
	private String country;
	private Long latestTotalCases;
	private Long casesDiffFromPrevDay;
	
	
}
