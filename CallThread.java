

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CallThread extends Thread {
	
	private String caller;
	private List<String> callees;
	private MessageQueue msgque;
	private MessageBus msgbus;
	
	public CallThread(String caller,List<String> callees,MessageBus msgbus) {
		
		this.caller = caller;
		this.callees = callees;
		this.msgque = new MessageQueue();
		this.msgbus = msgbus;
	}
	
	@Override
	public void run() {
		
		Message msg = null;
		
		for(String callee : callees) {
			
			try {
				ThreadLocalRandom trl = ThreadLocalRandom.current();
				Thread.sleep(trl.nextLong(10,50));
				IntroMessage intromsg = new IntroMessage(caller);
				msgbus.sendCallThreadMessage(callee,intromsg);
			} catch (InterruptedException e) {
			}
		}
		
		while((msg = msgque.getMessage(1000)) != null) {
			if(msg.getType() == ThreadMessageType.IntroMessage) {
				IntroMessage intromsg = (IntroMessage) msg;
				ReplyMessage replymsg = new ReplyMessage(caller,intromsg.getTimeStamp());
				msgbus.sendCallThreadMessage(intromsg.getFrom(),replymsg);
				msgbus.sendMasterMessage(MasterLogMessage.createIntroReceivedMessage(caller,intromsg.getFrom(),intromsg.getTimeStamp()));
			}else if(msg.getType() == ThreadMessageType.ReplyMessage) {
				ReplyMessage replymsg = (ReplyMessage) msg;
				msgbus.sendMasterMessage(MasterLogMessage.createReplyReceivedMessage(caller,replymsg.getFrom(),replymsg.getTimeStamp()));
			}
		}
		msgbus.sendMasterMessage(MasterLogMessage.createNoMoreMessagesMessage(caller));
	}
	
	public String getCaller() {
		
		return this.caller;
	}
	
	public void queueMessage(Message msg) {
		
		this.msgque.addMessage(msg);
	}
}
