package br.com.codenation.desafioexe;

import java.util.ArrayList;
import java.util.List;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> numeros = new ArrayList<>();

		int i = 0;
		int j = 1;
		int t;

		numeros.add(i);


		do{
			t = i + j;
			i = j;
			j = t;
			numeros.add(i);

		} while (t < 350);

		System.out.println(numeros.toString());

	}

}
