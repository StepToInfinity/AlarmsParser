package parser.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class All3GParser {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Integer> alarmsRNC = new LinkedHashMap();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Integer> alarmsHistoryRNC = new LinkedHashMap();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Integer> alarmsWBTS = new LinkedHashMap();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Integer> alarmsHistoryWBTS = new LinkedHashMap();
	private String separator = ",";
	private String line;
	private String alarm;
	private int numbers;
	private int alarmNum;
	private String RNC;
	private String[] lines;
	
	public void parser(File file) {
		
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			while ((line = in.readLine()) != null) {
				if (line.contains(",RNC-")) {
					line = line.replace(", ", "");
					
					lines = line.split(",");
					lines[5].replaceAll("\"", "");
					if (lines[3].contains("/")) {
						RNC = lines[3].substring(0, lines[3].indexOf("/"));
						
					} else {
						RNC = lines[3];
					}
					if (lines[5].startsWith("BTS booted") && lines[5].endsWith("due to trs management reset")) {
						lines[5] = "BTS booted due to trs management reset";
					}
					if (lines[5].startsWith("ToP master service") && lines[5].endsWith("unusable")) {
						lines[5] = "ToP master service unusable";
					}
					if (lines[5].startsWith("BTS booted") && lines[5].endsWith("due to trs power-on reset")) {
						lines[5] = "BTS booted due to trs power-on reset";
					}
					if (lines[5].startsWith("BTS booted") && lines[5].endsWith("due to trs spurious reset")) {
						lines[5] = "BTS booted due to trs power-on reset";
					}
					
					if (lines[0].equals("NOT FOUND")) {
						lines[0] = "N/A";
					}
					alarmNum = Integer.parseInt(lines[1]);
					if (line.endsWith("\"")) {
						
						alarm = RNC  + separator + lines[1] +  separator + lines[5] + separator+ lines[0] + separator;
						if ((alarmNum < 4000 )||(alarmNum >= 70000)) {
							numbers = alarmsRNC.containsKey(alarm) ?  alarmsRNC.get(alarm)+1 : 1;
							alarmsRNC.put(alarm, numbers);
						} else {
							numbers = alarmsWBTS.containsKey(alarm) ?  alarmsWBTS.get(alarm)+1 : 1;
							alarmsWBTS.put(alarm, numbers);
						}
						
					} else {
						alarm = RNC  + separator + lines[1] + separator + lines[5] + separator + lines[0] + separator;
						if ((alarmNum < 4000 )||(alarmNum >= 70000)) {
							numbers = alarmsHistoryRNC.containsKey(alarm) ?  alarmsHistoryRNC.get(alarm)+1 : 1;
							alarmsHistoryRNC.put(alarm, numbers);
						} else {
							numbers = alarmsHistoryWBTS.containsKey(alarm) ?  alarmsHistoryWBTS.get(alarm)+1 : 1;
							alarmsHistoryWBTS.put(alarm, numbers);
						}
					}
					
				}
				
			}
			
			in.close();
		} catch (IOException e) {
			System.out.println("RNC alarm");
		}
	}
	
	public Map<String, Integer> getAlarmsRNC(){
		return alarmsRNC;
	}
	
	public Map<String, Integer> getAlarmsHistoryRNC(){
		return alarmsHistoryRNC;
	}
	
	public Map<String, Integer> getAlarmsWBTS(){
		return alarmsWBTS;
	}
	
	public Map<String, Integer> getAlarmsHistoryWBTS(){
		return alarmsHistoryWBTS;
	}
	
	

}
