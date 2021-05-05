package com.sanvic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sanvic.model.DeathCasesStats;
import com.sanvic.model.LocationStats;

import lombok.Data;

@Data
@Service
public class CoronaVirusDeathDataService {

	private List<DeathCasesStats> allDeathStats = new ArrayList<>();
	
	public Long getTotalDeathCases(List<DeathCasesStats> data)
	{
		return data.stream().mapToLong(s -> s.getLatestTotalDeath()).sum();
		
	}
	
	public Long getTotalNewDeathCasesSinceLastDay(List<DeathCasesStats> data)
	{
		return data.stream().mapToLong(s -> s.getDeathDiffFromPrevDay()).sum();
		
	}
}
