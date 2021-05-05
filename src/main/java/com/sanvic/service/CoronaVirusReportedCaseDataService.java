package com.sanvic.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sanvic.model.LocationStats;
import com.sanvic.properties.AppProperties;

import lombok.Data;

@Data
@Service
public class CoronaVirusReportedCaseDataService {


	
	private List<LocationStats> allReportedStats = new ArrayList<>();
	
	
	public Long getTotalReportedCases(List<LocationStats> data)
	{
		return data.stream().mapToLong(s -> s.getLatestTotalCases()).sum();
		
	}
	
	public Long getTotalNewReportedCasesSinceLastDay(List<LocationStats> data)
	{
		return data.stream().mapToLong(s -> s.getCasesDiffFromPrevDay()).sum();
		
	}
}
