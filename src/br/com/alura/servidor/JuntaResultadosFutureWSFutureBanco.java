package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class JuntaResultadosFutureWSFutureBanco implements Callable<Void> {

	private Future<String> futureBanco;
	private Future<String> futureWS;
	private PrintStream saidaCliente;

	public JuntaResultadosFutureWSFutureBanco(Future<String> futureBanco, Future<String> futureWS, PrintStream saidaCliente) {
		this.futureBanco = futureBanco;
		this.futureWS = futureWS;
		this.saidaCliente = saidaCliente;
	}

	@Override
	public Void call() throws Exception {
		
		System.out.println("Aguardando resultados do future WS e Banco");
		
		try {
			String numeroMagico = this.futureWS.get(20, TimeUnit.SECONDS);
			String numeroMagico2 = this.futureBanco.get(20, TimeUnit.SECONDS);
			
			this.saidaCliente.println("Resultado comando c2: " + numeroMagico + ", " + numeroMagico2);
			
		} catch (Exception e) {
			System.out.println("Timeout: Cancelando execução do comando c2");
			this.saidaCliente.println("Timeout na execucao do comando c2");
			this.futureWS.cancel(true);
			this.futureBanco.cancel(true);
		} 
		
		System.out.println("Finalizou o JuntaResultadosFutureWSFutureBanco");

		return null;
	}

}
