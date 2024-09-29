package main.elevador;

public class Descendo implements EstadoElevador {

    @Override
    public void mover(Elevador elevador, int andarDestino) {
        System.out.println("Elevador descendo...");

        for (int i = elevador.getAndarAtual(); i >= andarDestino; i--) {
            System.out.println("Andar: " + i);
            elevador.setAndarAtual(i);
        }

        // Quando chega ao andar de destino, abre a porta e muda o estado para Parado
        elevador.setEstado(new Parado());
        elevador.abrirPorta();
    }

    @Override
    public void abrirPorta(Elevador elevador) {
        System.out.println("O elevador está descendo. Não é possível abrir a porta agora.");
    }

    @Override
    public void fecharPorta(Elevador elevador) {
        System.out.println("Fechando porta...");
        elevador.setPortaAberta(false);
    }
}
