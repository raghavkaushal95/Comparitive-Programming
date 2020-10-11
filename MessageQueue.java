

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class MessageQueue {
	
	private LinkedBlockingDeque<Message> msgs;
	
	public MessageQueue() {
		
		this.msgs = new LinkedBlockingDeque<Message>();
	}
	
	public void addMessage(Message msg) {
		
		msgs.add(msg);
	}
	
	public Message getMessage(long timeout)  {
		
		Message msg = null;
		try {
			msg = msgs.pollLast(timeout,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			
		}
		return msg;
	}
}
