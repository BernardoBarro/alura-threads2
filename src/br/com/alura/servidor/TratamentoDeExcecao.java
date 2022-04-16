package br.com.alura.servidor;

import java.lang.Thread.UncaughtExceptionHandler;

public class TratamentoDeExcecao implements UncaughtExceptionHandler{

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("Deu certo a execao na thread " + t.getName() + ", " + e.getMessage());
	}

}
