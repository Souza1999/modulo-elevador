package main.elevador;

import java.util.List;

public class Painel implements Observer {

    @Override
    public void atualizar(int andarAtual, String status, List<Integer> filaRequisicoes) {
        System.out.println("=== Painel do Elevador ===");
        System.out.println("Andar atual: " + andarAtual);
        System.out.println("Status: " + status);
        System.out.println("Fila de requisições: " + filaRequisicoes);
        System.out.println("==========================");
    }
}
