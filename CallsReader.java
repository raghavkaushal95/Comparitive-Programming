

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallsReader {
	
	private static final String expression = "^\\s*\\{\\s*([a-z]+)\\s*,\\s*\\[([\\s,a-z]*)\\]\\}.";
	
	public static class CallEntry {
		
		private String caller;
		private List<String> callees;
		private String strcallees;
		
		public CallEntry() {
			
			this.caller = null;
			this.callees = new ArrayList<String>();
			this.strcallees = null;
		}
		
		public void setCaller(String caller) {
			
			this.caller = caller;
		}
		
		public void addCallee(String callee) {
			
			this.callees.add(callee);
		}
		
		public void setStrCallees(String callees) {
			
			this.strcallees = callees;
		}
		
		public String getCaller() {
			
			return caller;
		}
		
		public List<String> getCallees() {
			
			return callees;
		}
		
		public String getStrCallees() {
			
			return strcallees;
		}
	}
	
	public static List<CallEntry> loadCalls(String filename) {
		
		
		try {
			List<CallEntry> records = new ArrayList<CallEntry>();
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			Pattern pattern = Pattern.compile(expression);
			String line = null;
			while((line = reader.readLine()) != null) {
				parseLine(line,pattern,records);
			}
			reader.close();
			return records;
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void parseLine(String line,Pattern pattern,List<CallEntry> records) {
		
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			String caller = matcher.group(1).trim();
			String strcallees = matcher.group(2);
			String [] callees = matcher.group(2).split(",");
			CallEntry record = new CallEntry();
			record.setCaller(caller);
			record.setStrCallees(strcallees);
			for(String callee : callees) {
				record.addCallee(callee.trim());
			}
			records.add(record);
		}
	}
	
}
