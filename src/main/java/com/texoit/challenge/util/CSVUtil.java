package com.texoit.challenge.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class CSVUtil {
	public static final int COLUMN_YEAR=0,COLUMN_TITLE=1,COLUMN_STUDIOS=2,COLUMN_PRODUCERS=3,COLUMN_WINNER=4;
	public static final int COLUMN_NUMBER_IN_A_FILLED_ROW=5;
	public static final int COLUMN_NUMBER_IN_A_INCOMPLETE_ROW=4;
	public static final String COLUMN_DELIMITER=";";
	public static final String LIST_DELIMITER=",|\\ and ";
	public static final String WORD_FOR_TRUE="yes";
	public static final String DEFAULT_MOVIELIST_FILE="movielist.csv";
	
	public static List<List<String>> toRows(InputStream file){
		boolean isFirst=true;
		List<List<String>> rows = new ArrayList<>();
	
		try (Scanner rowScanner = new Scanner(file)) {
		    while (rowScanner.hasNextLine()) {
		    	List<String> columns = new ArrayList<String>();
		    	String line=rowScanner.nextLine();
		    	
		    	//pular cabeçalho
		    	if(isFirst) {
		    		isFirst=false;
		    		continue;
		    	}	
		    	if(line==null)break;
		    	columns.addAll(Arrays.asList(line.split(COLUMN_DELIMITER)));
			    rows.add(columns);
		    }
		}
		rows=getValidRows(rows);
		return rows;
	}
	private static List<List<String>> getValidRows(List<List<String>> rows){
		List<List<String>> validRows=new ArrayList<List<String>>();
		for (List<String> columns : rows) {
			try {
				String year = columns.get(CSVUtil.COLUMN_YEAR);
				if(StringUtils.isBlank(year)||!StringUtils.isNumeric(year)){
					continue;
				}				
			}catch(Exception e) {
				continue;
			}
			try {
				String title = columns.get(CSVUtil.COLUMN_TITLE);
				if(StringUtils.isBlank(title)){
					continue;
				}				
			}catch(Exception e) {
				continue;
			}
			try {
				String studios = columns.get(CSVUtil.COLUMN_STUDIOS);
				if(StringUtils.isBlank(studios)){
					continue;
				}				
			}catch(Exception e) {
				continue;
			}
			try {
				String producers = columns.get(CSVUtil.COLUMN_PRODUCERS);
				if(StringUtils.isBlank(producers)){
					continue;
				}				
			}catch(Exception e) {
				continue;
			}
			if(columns.size()==CSVUtil.COLUMN_NUMBER_IN_A_FILLED_ROW) {
				try {
					String winner = columns.get(CSVUtil.COLUMN_WINNER);
					if(StringUtils.isBlank(winner)||!CSVUtil.WORD_FOR_TRUE.equals(winner)){
						continue;
					}				
				}catch(Exception e) {
					continue;
				}
			}
			validRows.add(columns);			
		}
		return validRows;
	}
	
}
