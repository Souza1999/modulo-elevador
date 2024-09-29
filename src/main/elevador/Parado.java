package main.elevador;

public class Parado implements EstadoElevador {

    @Override
    public void mover(Elevador elevador, int andarDestino) {
        if (elevador.getAndarAtual() == andarDestino) {
            System.out.println("O elevador já está no andar " + andarDestino);
        } else {
            // Se o andar de destino for maior que o atual, mudar para o estado Subindo
            if (elevador.getAndarAtual() < andarDestino) {
                elevador.setEstado(new Subindo());
            } else {
                // Se o andar de destino for menor que o atual, mudar para o estado Descendo
                elevador.setEstado(new Descendo());
            }
            elevador.moverPara(andarDestino); // Chama o movimento no novo estado
        }
    }

    @Override
    public void abrirPorta(Elevador elevador) {
        if (!elevador.isPortaAberta()) {
            System.out.println("Abrindo porta...");
            elevador.setPortaAberta(true);
        } else {
            System.out.println("A porta já está aberta.");
        }
    }

    @Override
    public void fecharPorta(Elevador elevador) {
        if (elevador.isPortaAberta()) {
            System.out.println("Fechando porta...");
            elevador.setPortaAberta(false);
        } else {
            System.out.println("A porta já está fechada.");
        }
    }
}
