package controller;

import java.util.concurrent.Semaphore;

public class ThreadCavaleiro extends Thread {

	private String cavaleiro;
	private int tochaCav;
	private Semaphore semaforo;

	private static int saida = (int) (Math.random() * 4);
	private static int tocha = 1;
	private static int pedra = 1;
	private static int[] portas = { 1, 1, 1, 1 };

	public ThreadCavaleiro(String cavaleiro, Semaphore semaforo) {
		this.cavaleiro = cavaleiro;
		this.semaforo = semaforo;
	}

	@Override
	public void run() {
		cavaleiroAndando();
		entraPorta();
	}

	private void entraPorta() {
		try {
			semaforo.acquire();
			int escolha = (int) (Math.random() * 4);
			;
			do {
				if (portas[escolha] == 1) {
					if (escolha != saida) {
						System.out.println("O " + cavaleiro + " foi morto pelo monstro");
						portas[escolha] = 0;
					} else {
						System.out.println("O " + cavaleiro + " escapou");
						portas[escolha] = 0;
					}
				}
			} while (portas[escolha] == 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}
	}

	private void cavaleiroAndando() {
		int distanciaPercorrida = 0;
		int passo = (int) ((Math.random() * 3) + 2);

		while (distanciaPercorrida < 2000) {
			distanciaPercorrida += passo;

			System.out.println("O " + cavaleiro + " andou " + distanciaPercorrida + "m.");

			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (distanciaPercorrida >= 500) {
				try {
					semaforo.acquire();
					passo = pegaTocha(passo);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				} finally {
					semaforo.release();
				}
			}

			if (distanciaPercorrida >= 1500 && tochaCav != 1) {
				try {
					semaforo.acquire();
					passo = pegaPedra(passo);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				} finally {
					semaforo.release();
				}
			}
		}
	}

	private int pegaPedra(int passo) {
		if (pedra == 1) {
			pedra = 0;
			passo += 2;
			System.out.println("O " + cavaleiro + " pegou a pedra");
		}
		return passo;
	}

	private int pegaTocha(int passo) {
		if (tocha == 1) {
			tocha = 0;
			tochaCav = 1;
			passo += 2;
			System.out.println("O " + cavaleiro + " pegou a tocha");
		}
		return passo;
	}
}
