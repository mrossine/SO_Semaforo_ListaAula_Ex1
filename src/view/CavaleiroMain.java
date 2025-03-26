package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCavaleiro;

public class CavaleiroMain {

	public static void main(String[] args) {
		
		Semaphore semaforo = new Semaphore(1);

		for (int i = 1; i < 5; i++) {
			ThreadCavaleiro t = new ThreadCavaleiro("Cavaleiro_" + i, semaforo);
			t.start();
		}
	}

}
