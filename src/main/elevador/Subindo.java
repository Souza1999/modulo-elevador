package main.elevador;

public class Subindo implements EstadoElevador {

    @Override
    public void mover(Elevador elevador, int andarDestino) {
        System.out.println("Elevador subindo...");

        // Simula a subida do elevador, passando pelos andares até chegar ao destino
        for (int i = elevador.getAndarAtual(); i <= andarDestino; i++) {
            System.out.println("Andar: " + i);
            elevador.setAndarAtual(i);

            // Introduz uma pausa de 2 segundos entre cada andar
            try {
                Thread.sleep(2000); // 2000 milissegundos =2 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // Quando chega ao andar de destino, abre a porta e muda o estado para Parado
        elevador.setEstado(new Parado());
        elevador.abrirPorta();
    }

    @Override
    public void abrirPorta(Elevador elevador) {
        System.out.println("O elevador está subindo. Não é possível abrir a porta agora.");
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

        } else {
            System.out.println("A porta está fechada.");
        }
    }
}
