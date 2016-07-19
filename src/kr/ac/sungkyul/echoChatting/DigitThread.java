package kr.ac.sungkyul.echoChatting;

public class DigitThread extends Thread {
	@Override
	public void run() {
		try {
			for (int i = 0; i < 9; i++) {
				System.out.print(i);
			}
			super.run();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
