package com.sanvic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sanvic.model.DeathCasesStats;
import com.sanvic.model.LocationStats;
import com.sanvic.service.CoronaVirusDeathDataService;
import com.sanvic.service.CoronaVirusReportedCaseDataService;
import com.sanvic.service.CoronaVirusURLDataFetchService;

@Controller
public class HomeController {

	@Autowired
	private CoronaVirusReportedCaseDataService reportCasesService;
	private CoronaVirusDeathDataService deathCasesService;
	
	public HomeController(CoronaVirusReportedCaseDataService service, CoronaVirusDeathDataService deathCasesService) {
		this.reportCasesService = reportCasesService;
		this.deathCasesService = deathCasesService;
	}
	
	@GetMapping("/")
	public String loadHomePage(Model model) {
		
		List<LocationStats> allReportedCaseStats = reportCasesService.getAllReportedStats();
		Long totalReportedCases = reportCasesService.getTotalReportedCases(allReportedCaseStats);
		Long totalNewReportedCasesSinceLastDay = reportCasesService.getTotalNewReportedCasesSinceLastDay(allReportedCaseStats);
		
		List<DeathCasesStats> allDeathCaseStats = deathCasesService.getAllDeathStats();
		Long totalDeathCases = deathCasesService.getTotalDeathCases(allDeathCaseStats);
		Long totalNewDeathCasesSinceLastDay = deathCasesService.getTotalNewDeathCasesSinceLastDay(allDeathCaseStats);
		
		model.addAttribute("allDeathCases",allDeathCaseStats);
		model.addAttribute("totalDeathCases",totalDeathCases);
		model.addAttribute("totalNewDeathCases",totalNewDeathCasesSinceLastDay);

		
		model.addAttribute("locationStats", allReportedCaseStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalNewCases", totalNewReportedCasesSinceLastDay);
		
				return "homepage";
	}
}
