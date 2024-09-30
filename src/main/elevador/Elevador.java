package main.elevador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ElevadorOLD {
    private static ElevadorOLD instance;
    private int andarAtual;
    private int totalAndares;
    private boolean portaAberta;
    private EstadoElevador estadoAtual;
    private List<Integer> filaRequisicoes;
    private List<Observer> observadores; // Lista de observadores
    private List<Integer> requisicoesExternasSubir;
    private List<Integer> requisicoesExternasDescer;

    // Construtor privado para garantir o padrão Singleton
    private ElevadorOLD(int totalAndares) {
        this.andarAtual = 0; // O elevador começa no térreo
        this.totalAndares = totalAndares;
        this.portaAberta = true; // Começa com a porta aberta
        this.estadoAtual = new Parado(); // Estado inicial do elevador
        this.filaRequisicoes = new ArrayList<>();
        this.observadores = new ArrayList<>(); // Inicializa a lista de observadores
        this.requisicoesExternasSubir = new ArrayList<>();
        this.requisicoesExternasDescer = new ArrayList<>();
    }

    // Método para obter a única instância do elevador (Singleton)
    public static ElevadorOLD getInstance(int totalAndares) {
        if (instance == null) {
            instance = new ElevadorOLD(totalAndares);
        }
        return instance;
    }

    // Registra um observador (como o Painel)
    public void registrarObservador(Observer observador) {
        observadores.add(observador);
        System.out.println("Observador registrado: " + observador.getClass().getSimpleName());
    }

    // Notifica todos os observadores sobre mudanças no elevador
    public void notificarObservadores(String status) {
        for (Observer observador : observadores) {
            observador.atualizar(andarAtual, status, filaRequisicoes);
        }
    }

    // Adiciona uma requisição de andar na fila
    public void adicionarRequisicao(int andar) {
        if (!filaRequisicoes.contains(andar)) {
            filaRequisicoes.add(andar);
        }
    }

    public void adicionarRequisicaoExternaSubir(int andar) {
        if (!requisicoesExternasSubir.contains(andar)) {
            requisicoesExternasSubir.add(andar);
        }
    }

    public void adicionarRequisicaoExternaDescer(int andar) {
        if (!requisicoesExternasDescer.contains(andar)) {
            requisicoesExternasDescer.add(andar);
        }
    }

    // Ordena a fila de requisições otimizando o percurso
    private void ordenarFila() {
        Collections.sort(filaRequisicoes, new Comparator<Integer>() {
            @Override
            public int compare(Integer andar1, Integer andar2) {
                int distancia1 = Math.abs(andarAtual - andar1);
                int distancia2 = Math.abs(andarAtual - andar2);

                // Se as distâncias forem iguais, priorize baseado no estado (subindo/descendo)
                if (distancia1 == distancia2) {
                    if (estadoAtual instanceof Subindo) {
                        return Integer.compare(andar1, andar2); // Prioriza o andar maior
                    } else if (estadoAtual instanceof Descendo) {
                        return Integer.compare(andar2, andar1); // Prioriza o andar menor
                    }
                }

                return Integer.compare(distancia1, distancia2); // Ordena pela menor distância
            }
        });
    }

    // Move o elevador para os andares da fila de forma otimizada
    public void visitarAndares() {
        ordenarFila();
        while (!filaRequisicoes.isEmpty() || !requisicoesExternasSubir.isEmpty() || !requisicoesExternasDescer.isEmpty()) {
            int proximoAndar;

            if (estadoAtual instanceof Subindo) {
                if (!filaRequisicoes.isEmpty()) {
                    proximoAndar = filaRequisicoes.get(0);  // Atende o primeiro da fila
                    if (proximoAndar > andarAtual) {  // Garante que a prioridade é dos andares acima
                        moverPara(proximoAndar);
                        filaRequisicoes.remove(0);
                    }
                } else if (!requisicoesExternasSubir.isEmpty()) {
                    proximoAndar = requisicoesExternasSubir.get(0);  // Atende os pedidos de fora (subir)
                    moverPara(proximoAndar);
                    requisicoesExternasSubir.remove(0);
                }
            } else if (estadoAtual instanceof Descendo) {
                if (!filaRequisicoes.isEmpty()) {
                    proximoAndar = filaRequisicoes.get(0);
                    if (proximoAndar < andarAtual) {  // Garante que a prioridade é dos andares abaixo
                        moverPara(proximoAndar);
                        filaRequisicoes.remove(0);
                    }
                } else if (!requisicoesExternasDescer.isEmpty()) {
                    proximoAndar = requisicoesExternasDescer.get(0);  // Atende os pedidos de fora (descer)
                    moverPara(proximoAndar);
                    requisicoesExternasDescer.remove(0);
                }
            }

            // Parar quando todas as solicitações forem atendidas
            if (filaRequisicoes.isEmpty() && requisicoesExternasSubir.isEmpty() && requisicoesExternasDescer.isEmpty()) {
                System.out.println("Elevador parado no andar " + andarAtual);
                estadoAtual = new Parado();
                notificarObservadores("Parado");
            }
        }
    }

    // Método para mover o elevador para o andar desejado
    public void moverPara(int andar) {
        estadoAtual.mover(this, andar);
    }

    //metodo antigo
/* // Move o elevador para o andar desejado
    public void moverPara(int andar) {

        if (andar == this.andarAtual) {
            // Caso o elevador já esteja no andar solicitado
            System.out.println("O elevador já está no andar " + andar);
            // Certifica-se de que a porta está aberta
            if (!isPortaAberta()) {
                abrirPorta();
                notificarObservadores("Porta Aberta");
            }
//            notificarObservadores("Elevador já está no andar " + andar);
            return; // Não realiza movimentação
        }

        fecharPorta();        //Fecha a porta antes de se mover
        estadoAtual.mover(this, andar);        //Movimento do elevador

        //Notifica ao chegar no andar destino
        //notificarObservadores("Elevador chegou ao andar " + andar);

        // Só abre a porta e notifica "Porta Aberta" se ela estiver fechada
        if (!isPortaAberta()) {
            abrirPorta();
            notificarObservadores("Porta Aberta");
        }

    }

    */

    // Métodos para abrir e fechar a porta
    public void abrirPorta() {
        estadoAtual.abrirPorta(this);
        notificarObservadores("Porta Aberta");
    }

    public void fecharPorta() {
        estadoAtual.fecharPorta(this);
    }

    // Getters e Setters
    public int getAndarAtual() {
        return andarAtual;
    }

    public void setAndarAtual(int andarAtual) {
        this.andarAtual = andarAtual;
    }

    public boolean isPortaAberta() {
        return portaAberta;
    }

    public void setPortaAberta(boolean portaAberta) {
        this.portaAberta = portaAberta;
    }

    public void setEstado(EstadoElevador estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    public List<Integer> getFilaRequisicoes() {
        return filaRequisicoes;
    }

    public void setFilaRequisicoes(List<Integer> filaRequisicoes) {
        this.filaRequisicoes = filaRequisicoes;
    }

    public EstadoElevador getEstadoAtual() {
        return estadoAtual;
    }
}