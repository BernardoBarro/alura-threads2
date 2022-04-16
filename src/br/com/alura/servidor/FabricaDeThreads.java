package br.com.alura.servidor;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {

	private static int NUMERO = 1;

	@Override
	public Thread newThread(Runnable r) {
		
		Thread thread = new Thread(r, "Thread Servidor Tarefas " + NUMERO);
		
		NUMERO ++;
		
		thread.setUncaughtExceptionHandler(new TratamentoDeExcecao());
		return thread;
	}

}
