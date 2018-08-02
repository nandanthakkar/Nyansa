package com.nyansa;
/*
 * @author Nandan Thakkar
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TimeZone;
import static java.util.Collections.reverseOrder;


public class DailyURLHits {

	final static int MICROSECONDS_IN_SECONDS = 1000;
	final static int SECONDS_IN_MINUTE = 60;
	final static int MINUTES_IN_HOUR = 60;
	final static int HOURS_IN_DAY = 24;
	
	/*
	 * Returns date in long variable. It converts timestamp (unix epoch in seconds) to date.
	 * 
	 * @param epoch - String representing timestamp (unix epoch in seconds).
	 * @return day - Long representing date (unix epoch in date). 
	 */
	public static Long epochToDate(String epoch) {
		Long time = Long.parseLong(epoch);	//Parse timestamp to Long
		Long day = Math.floorDiv(time, (SECONDS_IN_MINUTE*MINUTES_IN_HOUR*HOURS_IN_DAY));		// divide timestamp by SECONDS_IN_MINUTE*MINUTES_IN_HOUR*HOURS_IN_DAY to get the date.
		return day;
	}
	
	/*
	 * Sorts by URL hits and then prints by day in ascending order and URL Hits in descending order.
	 * 
	 * @param hitCount Map which stores URLhits by day and count.
	 * 
	 */
	public static void sortAndPrint(Map<Long, Map<String, Integer>> hitCount) {
		Iterator<Entry<Long, Map<String, Integer>>>  iterator = hitCount.entrySet().stream().sorted(Map.Entry.comparingByKey()).iterator();
		while(iterator.hasNext()) {
			Entry<Long, Map<String, Integer>> HitCountPerDay = iterator.next();
			Date date = new Date(HitCountPerDay.getKey()*MICROSECONDS_IN_SECONDS*SECONDS_IN_MINUTE*MINUTES_IN_HOUR*HOURS_IN_DAY);
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy z");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			System.out.println(df.format(date));
			
			HitCountPerDay.getValue().entrySet().stream()
				.sorted(reverseOrder(Map.Entry.comparingByValue()))
				.forEach((e1) -> {System.out.println(e1.getKey() + " " + e1.getValue());});
		}
		
	}
	
	/*
	 * Computes Daily URL Hit Report from the existing file and stores report to map.
	 * 
	 * @param hitCount - Map to store daily URL Hit report 
	 * @param file - file containing date and URL
	 */
	public static void computeDailyURLHitReport(File file, Map<Long, Map<String, Integer>> hitCount) {
		
		try (BufferedReader reader = Files.newBufferedReader(file.toPath());){
			String line = null;
			
		    while ((line = reader.readLine()) != null) {
		        String[] split = line.split("\\|");
		        Long day = epochToDate(split[0]);
		        
		        if(!hitCount.containsKey(day)) {
		        		hitCount.put(day, new HashMap<String, Integer>());
		        }
		        
		        if(hitCount.get(day).containsKey(split[1])) {
		        		hitCount.get(day).put(split[1], hitCount.get(day).get(split[1]) + 1);
		        }
		        else {
		        		hitCount.get(day).put(split[1], 1);
		        }
		        
		    }
		    
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		sortAndPrint(hitCount);
		
	}
	
	/*
	 * Creates Map to store daily URL Hit report, checks existence of file and calls computeDailyURLHitReport
	 * 
	 * @param args containing file path.
	 */
	public static void main(String[] args) {
		
		Map<Long, Map<String, Integer>> hitCount = new HashMap<Long,Map<String,Integer>>(); 	// to store daily URL Hit report 
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter File path on console!");
		String path = sc.nextLine();
		while(path == null || path.equals("")) {
			System.out.println("You did not enter file path.");
			System.out.println("Please enter File path on console!");
			path = sc.nextLine();
		}
		File file = new File(path); 	// get filename from runtime variable.
		if(!file.exists()) {		// check for existence of the file.
			System.out.println("FILE is not found");
			return;
		}
		
		computeDailyURLHitReport(file, hitCount);
		
	}

}
