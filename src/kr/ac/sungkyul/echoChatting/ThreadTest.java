package kr.ac.sungkyul.echoChatting;

public class ThreadTest {
	public static void main(String[] args) {
		DigitThread t1 = new DigitThread();
		AlpahabetThread t2 = new AlpahabetThread();
		t1.start();
		t2.start();

		
	}
}
