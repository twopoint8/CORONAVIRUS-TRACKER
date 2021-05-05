package com.sanvic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sanvic.model.DeathCasesStats;
import com.sanvic.model.LocationStats;
import com.sanvic.properties.AppProperties;

@Service
public class ScheduledFetchData {

	CoronaVirusReportedCaseDataService reportedCaseService;
	CoronaVirusDeathDataService deathCaseService;
	CoronaVirusURLDataFetchService fetchFromUrlService;
	AppProperties properties;

	@Autowired
	public ScheduledFetchData(CoronaVirusReportedCaseDataService reportedCaseService,
			CoronaVirusDeathDataService deathCaseService, CoronaVirusURLDataFetchService fetchFromUrlService,
			AppProperties properties) {
		this.reportedCaseService = reportedCaseService;
		this.deathCaseService = deathCaseService;
		this.fetchFromUrlService = fetchFromUrlService;
		this.properties = properties;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchAndStore() {
		System.out.println("Yha Aaya");
		List<LocationStats> newReportedStats = new ArrayList<>();
		List<DeathCasesStats> newDeathStats = new ArrayList<>();
		Map<String, String> url = properties.getUrl();
		Iterable<CSVRecord> reportedCaseRecords = fetchFromUrlService.fetchDataFromURL(url.get("reported_cases_url"));
		Iterable<CSVRecord> deathCaseRecords = fetchFromUrlService.fetchDataFromURL(url.get("death_cases_url"));

		if (reportedCaseRecords == null) {
			System.out.println("Nul Mila");
			System.exit(0);
		}

		for (CSVRecord record : reportedCaseRecords) {
			LocationStats locationStats = new LocationStats();
			locationStats.setState(record.get("Province/State"));
			locationStats.setCountry(record.get("Country/Region"));
			Long latestCases = Long.parseLong(record.get(record.size() - 1));
			Long prevDayCases = Long.parseLong(record.get(record.size() - 2));
			locationStats.setLatestTotalCases(latestCases);
			locationStats.setCasesDiffFromPrevDay(latestCases - prevDayCases);
			newReportedStats.add(locationStats);
		}

		for (CSVRecord record : deathCaseRecords) {
			//System.out.println(record.get("Country/Region"));
			DeathCasesStats  deathCaseStats = new DeathCasesStats();
			Long latestDeathCases = Long.parseLong(record.get(record.size() - 1));
			Long prevDayDeathCases = Long.parseLong(record.get(record.size() - 2));
			deathCaseStats.setLatestTotalDeath(latestDeathCases);
			deathCaseStats.setDeathDiffFromPrevDay(latestDeathCases - prevDayDeathCases);
			newDeathStats.add(deathCaseStats);
		}

		
		reportedCaseService.setAllReportedStats(newReportedStats);
		deathCaseService.setAllDeathStats(newDeathStats);
	}
}
