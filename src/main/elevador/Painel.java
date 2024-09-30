package main.elevador;

import java.util.List;

public class Painel implements Observer {

    @Override
    public void atualizar(int andarAtual, String status, List<Integer> filaRequisicoes, List<Integer> requisicoesExternasSubir, List<Integer> requisicoesExternasDescer) {
        System.out.println("=== Painel do Elevador ===");
        System.out.println("Andar atual: " + andarAtual);
        System.out.println("Status: " + status);
        System.out.println("Botões pressionados: " + filaRequisicoes);
        System.out.println("Pendentes subir: " + requisicoesExternasSubir);
        System.out.println("Pendentes descer: " + requisicoesExternasDescer);
        System.out.println("==========================");
    }
}
