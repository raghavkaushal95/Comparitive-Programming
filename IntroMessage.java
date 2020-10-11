

public class IntroMessage implements Message {
	
	private String from;
	private Long timestamp;
	
	public IntroMessage(String from) {
		this.from = from;
		this.timestamp = System.currentTimeMillis();
	}
	
	public IntroMessage(String from,Long timestamp) {
		
		this.from = from;
		this.timestamp = timestamp;
	}
	
	public String getFrom() {
		
		return this.from;
	}
	
	public Long getTimeStamp() {
		
		return this.timestamp;
	}
	
	@Override
	public ThreadMessageType getType() {
		
		return ThreadMessageType.IntroMessage;
	}

}
