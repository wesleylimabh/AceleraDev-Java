package br.com.codenation.desafioexe;

import java.util.ArrayList;
import java.util.List;

import br.com.codenation.TestadorFibonacci;
import br.com.codenation.annotation.Desafio;

public class DesafioApplication {

	public static void main(String[] args) {
		new TestadorFibonacci().testaDesafio(DesafioApplication.class);
	}

	@Desafio("Fibonacci")
	public static List<Integer> fibonacci() {
		final int limit = 350;
		List<Integer> fibo = new ArrayList<Integer>();
		fibo.add(0);
		fibo.add(1);
		int i = 1;
		while (fibo.get(i) + fibo.get(i - 1) < limit) {
			fibo.add(fibo.get(i) + fibo.get(i - 1));
			i++;
		}
		return fibo;
	}

	@Desafio("isFibonacci")
	public static Boolean isFibonacci(Integer a) {
		List<Integer> fibo = fibonacci();
		if(fibo.contains(a)) {
			return true;
		}else return false;
	}

}
