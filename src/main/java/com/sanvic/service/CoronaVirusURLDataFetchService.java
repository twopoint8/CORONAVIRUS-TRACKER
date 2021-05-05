package com.sanvic.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sanvic.model.LocationStats;

import lombok.Data;

@Service
@Data
public class CoronaVirusURLDataFetchService {

	public Iterable<CSVRecord> fetchDataFromURL(String URL) {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		Iterable<CSVRecord> records = null;
		try {
			HttpGet request = new HttpGet(URL);
			CloseableHttpResponse response = httpClient.execute(request);
			 HttpEntity entity = response.getEntity();
			 if (entity != null) 
			 {
			 BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent())); 
			 records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(br);
			 return records;
			 
			 }
		}
		catch(Exception e) {
			
		}
	
		return null;
}	

}
