package parser.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class All2GParser {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Integer> alarmZaho = new LinkedHashMap();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Integer> alarmZahp = new LinkedHashMap();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Integer> alarmZeol = new LinkedHashMap();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Integer> alarmZeoh = new LinkedHashMap();
	private String separator = ",";
	private String bsc;
	private int alarmNum;
	private String alarmText;
	private String alarmType;
	private String alarmSeverity;
	private int numbers;
	private String line;
	private String fullAlarm;


	
	public void parser(File file) {
		String line;
		//BufferedReader in = new BufferedReader(new InputStreamReader(new InputStream("D:\\Tmp\\log1.log")));
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			while ((line = in.readLine()) != null) {
				if (line.startsWith("< ZAHO")) {
					zaho(in,alarmZaho);
				}
				
				if (line.startsWith("< ZAHP")) {
					zahp(in,alarmZahp);
				}
				
				if (line.startsWith("< ZEOL")) {
					zeol(in,alarmZeol);
				}
				
				if (line.startsWith("< ZEOH")) {
					zeoh(in,alarmZeoh);
				}
			//	System.out.println(line);
			}
			
			in.close();
		} catch (IOException e) {
			System.out.println("ZahoPars alarm");
		}
		
	//	System.out.println();
	}
	
	
	public void zaho(BufferedReader in, Map<String, Integer> alarmZaho) throws NumberFormatException, IOException {
		while ((line = in.readLine()) != null) {
			if (line.startsWith("           BSC")) {
				bsc = line.substring(line.indexOf("BSC"),line.indexOf("BSC")+7).trim();
				line = in.readLine();
				alarmSeverity = line.substring(0,3).trim();
				alarmType = line.substring(4, 11).trim();
				line = in.readLine();
				alarmNum = Integer.parseInt(line.substring(11, 15).trim());
				alarmText = line.substring(16, line.length()).trim();
				if (alarmType.equals("NOTICE")) {
					alarmSeverity = "N/A";
				}
				fullAlarm = bsc + separator + alarmNum + separator + alarmText + separator + alarmSeverity + separator + alarmType + separator;
				numbers = alarmZaho.containsKey(fullAlarm) ?  alarmZaho.get(fullAlarm)+1 : 1;
				alarmZaho.put(fullAlarm, numbers);
			}
			
			if (line.startsWith("END OF ALARMS CURRENTLY ON")) {
				break;
			}
		}
	}
	
	public void zahp(BufferedReader in, Map<String, Integer> alarmZahp) throws NumberFormatException, IOException {
		while ((line = in.readLine()) != null) {
			if (line.startsWith("    <HIST> BSC")) {
				bsc = line.substring(line.indexOf("BSC"),line.indexOf("BSC")+7).trim();
				line = in.readLine();
			//	System.out.println(line);
				alarmSeverity = line.substring(0,3).trim();
				alarmType = line.substring(4, 11).trim();
				if (alarmType.equals("CANCEL"))
					continue;
				line = in.readLine();
			//	System.out.println(line);
				alarmNum = Integer.parseInt(line.substring(11, 15).trim());
				alarmText = line.substring(16, line.length()).trim();
				if (alarmType.equals("NOTICE")) {
					alarmSeverity = "N/A";
				}
				fullAlarm = bsc + separator + alarmNum + separator + alarmText + separator + alarmSeverity + separator + alarmType + separator;
				numbers = alarmZahp.containsKey(fullAlarm) ?  alarmZahp.get(fullAlarm)+1 : 1;
				alarmZahp.put(fullAlarm, numbers);
			}
			
			if (line.startsWith("END OF ALARM HISTORY")) {
				break;
			}
		}
	}
	
	public void zeol(BufferedReader in, Map<String, Integer> alarmZeol) throws NumberFormatException, IOException {
		while ((line = in.readLine()) != null) {
			if (line.startsWith("           BSC")) {
				bsc = line.substring(line.indexOf("BSC"),line.indexOf("BSC")+7).trim();
				line = in.readLine();
				alarmSeverity = line.substring(0,3).trim();
				alarmType = line.substring(4, 9).trim();
				line = in.readLine();
				if (!(line.contains("(")))
					line = in.readLine();
				alarmNum = Integer.parseInt(line.substring(11, 15).trim());
				alarmText = line.substring(16, line.length()).trim();
				if (alarmType.equals("NOTICE")) {
					alarmSeverity = "N/A";
				}
				fullAlarm = bsc + separator + alarmNum + separator + alarmText + separator + alarmSeverity + separator + alarmType + separator;
				numbers = alarmZeol.containsKey(fullAlarm) ?  alarmZeol.get(fullAlarm)+1 : 1;
				alarmZeol.put(fullAlarm, numbers);
			}
			
			if (line.startsWith("END OF BTS ALARM LISTING")) {
				break;
			}
		}
	}
	
	public void zeoh(BufferedReader in, Map<String, Integer> alarmZeol) throws NumberFormatException, IOException {
		while ((line = in.readLine()) != null) {
			if (line.startsWith("    <HIST> BSC")) {
				bsc = line.substring(line.indexOf("BSC"),line.indexOf("BSC")+7).trim();
				line = in.readLine();
				alarmSeverity = line.substring(0,3).trim();
				if (line.length() > 9)
					alarmType = line.substring(4, 10).trim();
				else
					alarmType = line.substring(4, 9).trim();
				if (alarmType.equals("CANCEL"))
					continue;
				line = in.readLine();
				if (!(line.contains("(")))
					line = in.readLine();
				alarmNum = Integer.parseInt(line.substring(11, 15).trim());
				alarmText = line.substring(16, line.length()).trim();
				if (alarmType.equals("NOTICE")) {
					alarmSeverity = "N/A";
				}
				fullAlarm = bsc + separator + alarmNum + separator + alarmText + separator + alarmSeverity + separator + alarmType + separator;
				numbers = alarmZeol.containsKey(fullAlarm) ?  alarmZeol.get(fullAlarm)+1 : 1;
				alarmZeol.put(fullAlarm, numbers);
			}
			
			if (line.startsWith("END OF BTS ALARM HISTORY LISTING")) {
				break;
			}
		}
	}
	
	
	public Map<String, Integer> getAlarmZaho(){
		return alarmZaho;
	}
	
	public Map<String, Integer> getAlarmZahp(){
		return alarmZahp;
	}
	
	public Map<String, Integer> getAlarmZeol(){
		return alarmZeol;
	}
	
	public Map<String, Integer> getAlarmZeoh(){
		return alarmZeoh;
	}
	

}
