package br.com.alura.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidor;
	private ExecutorService threadPool;

	public DistribuirTarefas(Socket socket, ServidorTarefas servidor, ExecutorService threadPool) {
		this.socket = socket;
		this.servidor = servidor;
		this.threadPool = threadPool;
	}

	@Override
	public void run() {

		try {
			System.out.println("Distribuindo tarefas para " + socket);

			Scanner input = new Scanner(socket.getInputStream());

			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			
			while (input.hasNextLine()) {
				String comando = input.nextLine();
				System.out.println("Comando recebido " + comando);

				switch (comando) {
				case "c1": {
					saidaCliente.println("Confirmação comando c1");
					ComandoC1 c1 = new ComandoC1(saidaCliente);
					this.threadPool.execute(c1);
					break;
				}
				case "c2": {
					saidaCliente.println("Confirmação comando c2");
					ComandoC2ChamaWS c2WS = new ComandoC2ChamaWS(saidaCliente);
					ComandoC2AcessaBanco c2Banco = new ComandoC2AcessaBanco(saidaCliente);
					Future<String> futureWS = this.threadPool.submit(c2WS);
					Future<String> futureBanco = this.threadPool.submit(c2Banco);
					
					this.threadPool.submit(new JuntaResultadosFutureWSFutureBanco(futureBanco, futureWS, saidaCliente));
					
					break;
				}
				case "fim": {
					saidaCliente.println("Desligando o servidor");
					servidor.parar();
					break;
				}
				default: {
					saidaCliente.println("Comando não encontrado");
					break;
				}
				}

				System.out.println(comando);
			}
			saidaCliente.close();
			input.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
