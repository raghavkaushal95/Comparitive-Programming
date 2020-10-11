

public class MasterLogMessage implements Message {
	
	private String logmsg;
	
	public MasterLogMessage(String logmsg) {
		
		this.logmsg = logmsg;
	}
	
	public String getLogMsg() {
		
		return this.logmsg;
	}
	
	@Override
	public ThreadMessageType getType() {
		
		return ThreadMessageType.LogMessage;
	}
	
	static MasterLogMessage createIntroReceivedMessage(String recipient,String source,Long timestamp) {
		
		String msg = String.format("%s received intro message from %s [%d]",recipient,source,timestamp);
		return new MasterLogMessage(msg);
	}
	
	static MasterLogMessage createReplyReceivedMessage(String recipient,String source,Long timestamp) {
		
		String msg = String.format("%s received reply message from %s [%d]",recipient,source,timestamp);
		return new MasterLogMessage(msg);
	}
	
	static MasterLogMessage createNoMoreMessagesMessage(String source) {
		
		String msg = String.format("\nProcess %s has received no calls for 1 second, ending... ",source);
		return new MasterLogMessage(msg);
	}
}
