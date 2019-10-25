package challenge;

import java.util.ArrayList;
import java.util.List;

public class Estacionamento {

	private List<Carro> carrosEstacionadosList = new ArrayList<Carro>();

    public void estacionar(Carro carro) {
    	
    	Boolean motoristaMenor = carro.getMotorista().getIdade() < 18;
    	Boolean motoristaSemPontos = carro.getMotorista().getPontos() > 20;
    	
    	if(motoristaMenor || motoristaSemPontos) {
    		throw new EstacionamentoException("Não pode estacionar");
    	}
    	

    	if(carrosEstacionados() < 10) {
    		carrosEstacionadosList.add(carro);	
    	}else {
    		
    		if(carrosEstacionadosList.get(0).getMotorista().getIdade() > 55) {
    			if(carrosEstacionadosList.stream().findFirst().filter(car -> car.getMotorista().getIdade() < 55).isPresent()) {
    				carrosEstacionadosList.remove(carrosEstacionadosList.stream().findFirst().filter(car -> car.getMotorista().getIdade() < 55).get());
    				carrosEstacionadosList.add(carro);
    			}
    		}else {
    			carrosEstacionadosList.remove(0);
    			carrosEstacionadosList.add(carro);
    		}
    				
    		Boolean todosSeniors = carrosEstacionadosList.stream().allMatch(car -> car.getMotorista().getIdade() > 55);
    		if(todosSeniors) {
    			throw new EstacionamentoException("Não pode estacionar");
    		}
    		
    	}
      
    }

    public int carrosEstacionados() {
        return carrosEstacionadosList.size();
    }

    public boolean carroEstacionado(Carro carro) {
        return carrosEstacionadosList.contains(carro);
    }
}
