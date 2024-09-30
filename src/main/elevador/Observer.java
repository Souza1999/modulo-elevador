package main.elevador;

import java.util.List;

public interface Observer {
    void atualizar(int andarAtual, String status, List<Integer> filaRequisicoes, List<Integer> requisicoesExternasSubir, List<Integer> requisicoesExternasDescer);
}
