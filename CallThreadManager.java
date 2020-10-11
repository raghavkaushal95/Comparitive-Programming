

import java.util.HashMap;
import java.util.Map;

public class CallThreadManager {
	
	private Map<String,CallThread> threads;
	
	public CallThreadManager() {
		
		this.threads = new HashMap<String,CallThread>();
	}
	
	public void addCallingThread(CallThread thread) {
		
		this.threads.put(thread.getCaller(),thread);
	}
	
	public CallThread getCallingThread(String caller) {
		
		return this.threads.get(caller);
	}
	
}
