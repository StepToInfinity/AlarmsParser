package parser.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class WritterAlarms {
	public void write(Map<String, Integer> alarm, File file, String type) {
		BufferedWriter out;
		String filename;
		String filepath = file.getParent() + "\\parsed\\";
		File folder = new File(filepath);
		String separator = ",";
		if (!folder.exists())
			folder.mkdir();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		String requiredDate = df.format(new Date()).toString();
		try {
			filename = filepath + requiredDate +"_"+ type  + ".csv";
			out = new BufferedWriter(new FileWriter(filename));
			if (type.equals("ZAHO") ||type.equals("ZAHP") ||type.equals("ZEOL") ||type.equals("ZEOH")) {
				out.write("Controller" + separator + "Alarm Number" + separator + "Alarm Text" + separator + "Alarm Severity" + separator + "Alarm Type" + separator + "Number");
			} else {
				out.write("Controller" + separator + "Alarm Number" + separator + "Alarm Text" + separator + "Alarm Severity" + separator + "Number");
			}
			out.newLine();
			
			Iterator<Map.Entry<String, Integer>> enteries = alarm.entrySet().iterator();
			while(enteries.hasNext()) {
				Map.Entry<String, Integer> entry = enteries.next();
				out.write(entry.getKey()+entry.getValue());
				out.newLine();
			}
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.out.println("Writing error");
		}
	}
}

