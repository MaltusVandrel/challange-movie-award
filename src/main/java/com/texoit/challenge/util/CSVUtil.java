package com.texoit.challenge.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVUtil {
	public static final int COLUMN_YEAR=0,COLUMN_TITLE=1,COLUMN_STUDIOS=2,COLUMN_PRODUCERS=3,COLUMN_WINNER=4;
	public static final int COLUMN_NUMBER_IN_A_FILLED_ROW=5;
	public static final int COLUMN_NUMBER_IN_A_INCOMPLETE_ROW=4;
	public static final String COLUMN_DELIMITER=";";
	public static final String LIST_DELIMITER=",|\\ and ";
	public static final String WORD_FOR_TRUE="yes";
	
	
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
//			    try (Scanner columnScanner = new Scanner()) {
//			    	columnScanner.useDelimiter(COLUMN_DELIMITER);
//			        while (columnScanner.hasNext()) {
//			        	columns.add(columnScanner.next());
//			        }
//			    }
			    rows.add(columns);
		    }
		}
		return rows;
	}
	
	
}
