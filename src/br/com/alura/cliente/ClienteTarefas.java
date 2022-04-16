package br.com.alura.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {
	public static void main(String[] args) throws Exception, Exception {
		
		
		Socket socket = new Socket("localhost", 12345);
		
		System.out.println("conexão estabelecida");
		
		Thread threadEnviaComando = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("Pode enviar comandos");
					PrintStream saida = new PrintStream(socket.getOutputStream());
					Scanner input = new Scanner(System.in);
					while(input.hasNextLine()) {
						String linha = input.nextLine();
						
						if(linha.trim().equals("")) {
							break;
						}
						
						saida.println(linha);
					}
					saida.close();
					input.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		Thread threadRecebeResposta = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("Recebendo dados do servidor");
					Scanner servidor = new Scanner(socket.getInputStream());
					while(servidor.hasNextLine()) {
						String linha = servidor.nextLine();
						System.out.println(linha);
					}
					servidor.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		threadRecebeResposta.start();
		threadEnviaComando.start();
		
		threadEnviaComando.join();
		
		socket.close();
	}
}
