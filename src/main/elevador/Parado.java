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
            try {
                System.out.println("Abrindo porta...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elevador.setPortaAberta(true);
            System.out.println("Porta aberta!");
        } else {
            System.out.println("");
        }
    }

    @Override
    public void fecharPorta(Elevador elevador) {
        if (elevador.isPortaAberta()) {
            System.out.println("Fechando porta...");
            try {
                Thread.sleep(1500);  // Simula o tempo para fechar a porta (1.5 segundos)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elevador.setPortaAberta(false);
            System.out.println("Porta fechada!");

        } else {
            System.out.println("A porta está fechada.");
        }
    }
}
