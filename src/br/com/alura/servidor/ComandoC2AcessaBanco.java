package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBanco implements Callable<String> {

	private PrintStream saidaCliente;

	public ComandoC2AcessaBanco(PrintStream saidaCliente) {
		this.saidaCliente = saidaCliente;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Servidor recebeu comando c2 - Banco");
		
		saidaCliente.println("processando comando c2 - Banco");
		
		Thread.sleep(15000);
		
		int numero = new Random().nextInt(100) + 1;
		
		saidaCliente.println("Servidor finalizou o comando c2 - Banco");
		
		return Integer.toString(numero);
	}

}
