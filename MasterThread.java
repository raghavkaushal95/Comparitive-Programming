

import java.util.List;

public class MasterThread extends Thread {
	
	private static final String callsfile = "calls.txt";
	CallThreadManager ctm;
	MessageQueue msgqueue;
	MessageBus msgbus;
	
	public MasterThread() {
		
	}
	
	public void queueMessage(Message msg) {
		
		this.msgqueue.addMessage(msg);
	}
	
	@Override
	public void run() {
		
		this.ctm = new CallThreadManager();
		this.msgqueue = new MessageQueue();
		this.msgbus = new MessageBus(this,ctm);
		List<CallsReader.CallEntry> entries = CallsReader.loadCalls(callsfile);
		System.out.println("** Calls to be made **");
		
		for(CallsReader.CallEntry entry : entries) {
			
			String msg = String.format("%s: [%s]",entry.getCaller(),entry.getStrCallees());
			System.out.println(msg);
			ctm.addCallingThread( new CallThread(entry.getCaller(),entry.getCallees(),msgbus));
		}
		
		System.out.println("\n");
		
		for(CallsReader.CallEntry entry : entries) {
			
			this.ctm.getCallingThread(entry.getCaller()).start();
		}
		
		Message msg = null;
		while((msg = msgqueue.getMessage(1500))!=null) {
			if(msg.getType() == ThreadMessageType.LogMessage) {
				MasterLogMessage logmsg = (MasterLogMessage)msg;
				System.out.println(logmsg.getLogMsg());
			}
		}
		
		System.out.println("\nMaster has received no replies for 1.5 seconds, ending...");
	}
}
