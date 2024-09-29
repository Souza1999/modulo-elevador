package main.elevador;

import java.util.ArrayList;
import java.util.List;

public class Elevador {
    private static Elevador instance;
    private int andarAtual;
    private int totalAndares;
    private boolean portaAberta;
    private EstadoElevador estadoAtual;
    private List<Integer> filaRequisicoes;
    private List<Observer> observadores; // Lista de observadores

    // Construtor privado para garantir o padrão Singleton
    private Elevador(int totalAndares) {
        this.andarAtual = 0; // O elevador começa no térreo
        this.totalAndares = totalAndares;
        this.portaAberta = true; // Começa com a porta aberta
        this.estadoAtual = new Parado(); // Estado inicial do elevador
        this.filaRequisicoes = new ArrayList<>();
        this.observadores = new ArrayList<>(); // Inicializa a lista de observadores
    }

    // Método para obter a única instância do elevador (Singleton)
    public static Elevador getInstance(int totalAndares) {
        if (instance == null) {
            instance = new Elevador(totalAndares);
        }
        return instance;
    }

    // Registra um observador (como o Painel)
    public void registrarObservador(Observer observador) {
        observadores.add(observador);
    }

    // Notifica todos os observadores sobre mudanças no elevador
    private void notificarObservadores(String status) {
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

    // Move o elevador para o andar desejado
    public void moverPara(int andar) {
        estadoAtual.mover(this, andar);
        notificarObservadores(estadoAtual.getClass().getSimpleName()); // Notifica após a mudança de estado
    }

    // Métodos para abrir e fechar a porta
    public void abrirPorta() {
        estadoAtual.abrirPorta(this);
        notificarObservadores("Porta Aberta");
    }

    public void fecharPorta() {
        estadoAtual.fecharPorta(this);
        notificarObservadores("Porta Fechada");
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
        return estado
