


public class exchange {

	public static void main(String[] args) {
		
		try {
			MasterThread master = new MasterThread();
			master.start();
			master.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
