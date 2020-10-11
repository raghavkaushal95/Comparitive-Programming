

public class MessageBus {
	
	private CallThreadManager threadmanager;
	private MasterThread master;
	
	public MessageBus(MasterThread master,CallThreadManager threadmanager) {
		
		this.master = master;
		this.threadmanager = threadmanager;
	}
	
	public void sendCallThreadMessage(String recipient,Message message) {
		
		CallThread ct = threadmanager.getCallingThread(recipient);
		if(ct != null) {
		    ct.queueMessage(message);
		}
	}
	
	public void sendMasterMessage(Message message) {
		
		this.master.queueMessage(message);
	}
}
