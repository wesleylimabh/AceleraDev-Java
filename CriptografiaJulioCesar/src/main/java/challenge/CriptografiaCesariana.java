package challenge;

public class CriptografiaCesariana implements Criptografia {

	private final int INICIO = 97;
	private final int FIM = 122;
	private final int DESLOCAMENTO = 3;

	@Override
	public String criptografar(String texto) {
		if (!(texto.isEmpty()) && (texto != null)) {
			StringBuilder aux = new StringBuilder();
			texto = texto.toLowerCase();

			for (int i = 0; i < texto.length(); i++) {
				char letra = texto.charAt(i);
				int numASCII = letra + DESLOCAMENTO;
				if (isInRange(letra)) {
					if (isInRange(numASCII)) {
						aux.append((char) (numASCII));
					} else {
						aux.append((char) (INICIO - (FIM - numASCII)));
					}

				} else {
					aux.append(letra);
				}

			}
			return aux.toString();
		} else
			throw new java.lang.IllegalArgumentException();
	}

	@Override
	public String descriptografar(String texto) {
		if (!(texto.isEmpty()) && (texto != null)) {
			StringBuilder aux = new StringBuilder();
			texto = texto.toLowerCase();

			for (int i = 0; i < texto.length(); i++) {
				char letra = texto.charAt(i);
				int numASCII = letra - DESLOCAMENTO;
				if (isInRange(letra)) {
					if (isInRange(numASCII)) {
						aux.append((char) (numASCII));
					} else {
						aux.append((char) (FIM - (INICIO - numASCII)));
					}

				} else {
					aux.append(letra);
				}

			}
			return aux.toString();
		} else
			throw new java.lang.IllegalArgumentException();
	}

	private boolean isInRange(int c) {
		return c >= 97 && c <= 122;
	}
}
