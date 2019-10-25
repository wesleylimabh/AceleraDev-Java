package br.com.codenation;

import static br.com.codenation.desafio.utils.MensagemException.IDENTIFICADOR_UTILIZADO;
import static br.com.codenation.desafio.utils.MensagemException.JOGADOR_NAO_ENCOTRADO;
import static br.com.codenation.desafio.utils.MensagemException.SEM_CAPITAO_DEFINIDO;
import static br.com.codenation.desafio.utils.MensagemException.TIME_NAO_ENCOTRADO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.domain.Jogador;
import br.com.codenation.domain.Time;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
	private List<Time> listaTimes = new ArrayList<Time>();
	private List<Jogador> listaJogadores = new ArrayList<Jogador>();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal,
			String corUniformeSecundario) {
		if (listaTimes.stream().anyMatch(time -> time.getId().equals(id))) {
			throw new IdentificadorUtilizadoException(IDENTIFICADOR_UTILIZADO);
		}

		Time novoTime = new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
		listaTimes.add(novoTime);

	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade,
			BigDecimal salario) {

		verificarTimeExiste(idTime);

			if (listaJogadores.stream().anyMatch(time -> time.getId().equals(id))) {
				throw new IdentificadorUtilizadoException(IDENTIFICADOR_UTILIZADO);
			} else {
				Jogador jogador = new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario);
				listaJogadores.add(jogador);
			}
		}
	

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		
		verificarJogadorExiste(idJogador);

		Long idTime = listaJogadores.stream().filter(jogador -> jogador.getId().equals(idJogador)).findFirst()
				.map(jogador -> jogador.getIdTime()).get();

		listaTimes.stream().filter(time -> time.getId().equals(idTime)).findFirst().get().setIdCapitao(idJogador);

	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {

		verificarTimeExiste(idTime);

		Time timeCapitao = listaTimes.stream().filter(time -> time.getId().equals(idTime)).findFirst().get();
		if (timeCapitao.getIdCapitao() == null) {
			throw new CapitaoNaoInformadoException(SEM_CAPITAO_DEFINIDO);
		}
		return timeCapitao.getIdCapitao();

	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {

		verificarJogadorExiste(idJogador);
		
		return listaJogadores.stream().filter(jogador -> jogador.getId().equals(idJogador)).findFirst().get().getNome();
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		
		verificarTimeExiste(idTime);
		
		return listaTimes.stream().filter(jogador -> jogador.getId().equals(idTime)).findFirst().get().getNome();
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		
		verificarTimeExiste(idTime);

		return listaJogadores.stream().filter(jogador -> jogador.getIdTime().equals(idTime))
				.sorted(Comparator.comparing(Jogador::getId)).map(Jogador::getId)
				.collect(Collectors.toList());

	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		
		verificarTimeExiste(idTime);		

		List<Jogador> listaJogadores = buscaJogadores(idTime);
		return listaJogadores.stream().max(Comparator.comparing(Jogador::getNivelHabilidade)).get().getId();

	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		
		verificarTimeExiste(idTime);
		
		List<Jogador> listaJogadores = buscaJogadores(idTime);
		return listaJogadores.stream()
				.sorted(Comparator.comparing(Jogador::getDataNascimento).thenComparing(Jogador::getId))
				.map(Jogador::getId).findFirst().get();
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {

		return listaTimes.stream().sorted(Comparator.comparing(Time::getId)).map(Time::getId)
				.collect(Collectors.toList());
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		
		verificarTimeExiste(idTime);
		
		List<Jogador> listaJogadores = buscaJogadores(idTime);
		return listaJogadores.stream()
				.sorted(Comparator.comparing(Jogador::getSalario).reversed().thenComparing(Jogador::getId))
				.map(Jogador::getId).findFirst().get();

	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		
		verificarJogadorExiste(idJogador);
		
		return listaJogadores.stream().filter(jogador -> jogador.getId().equals(idJogador)).findFirst().get()
				.getSalario();
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {

		return listaJogadores.stream()
				.sorted(Comparator.comparing(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId))
				.limit(top).map(Jogador::getId).collect(Collectors.toList());

	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		
		verificarTimeExiste(timeDaCasa);
		verificarTimeExiste(timeDeFora);

		Time timeCasa = listaTimes.stream().filter(time -> time.getId().equals(timeDaCasa)).findFirst().get();
		Time timeFora = listaTimes.stream().filter(time -> time.getId().equals(timeDeFora)).findFirst().get();

		if (timeCasa.getCorUniformePrincipal().equals(timeFora.getCorUniformePrincipal())) {
			return timeFora.getCorUniformeSecundario();
		} else {
			return timeFora.getCorUniformePrincipal();
		}
	}

	private List<Jogador> buscaJogadores(Long idTime) {
		return listaJogadores.stream().filter(jogador -> jogador.getIdTime().equals(idTime))
				.collect(Collectors.toList());
	}

	private void verificarTimeExiste(Long idTime) {
		if(!listaTimes.stream().filter(time -> time.getId().equals(idTime)).findAny().isPresent()) {
			throw new TimeNaoEncontradoException(TIME_NAO_ENCOTRADO);			
		}
		
	}
	private void verificarJogadorExiste(Long idJogador) {
		if(!listaJogadores.stream().filter(jogador -> jogador.getId().equals(idJogador)).findAny().isPresent()) {
			throw new JogadorNaoEncontradoException(JOGADOR_NAO_ENCOTRADO);
		}
	}
}
