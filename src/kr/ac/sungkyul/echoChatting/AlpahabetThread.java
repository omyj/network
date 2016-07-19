package kr.ac.sungkyul.echoChatting;

public class AlpahabetThread extends Thread {
	@Override
	public void run() {
		try {
			
			for (int i = 'a'; i < 'z'; i++) {
				System.out.print((char)i);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		super.run();
	}
}
