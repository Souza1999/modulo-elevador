package main.elevador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Elevador {
    private static Elevador instance;
    private int andarAtual;
    private int totalAndares;
    private boolean portaAberta;
    private EstadoElevador estadoAtual;
    private List<Integer> filaRequisicoes;
    private List<Integer> requisicoesExternasSubir;
    private List<Integer> requisicoesExternasDescer;
    private List<Observer> observadores;

    // Construtor privado para garantir o padrão Singleton
    private Elevador(int totalAndares) {
        this.andarAtual = 0; // O elevador começa no térreo
        this.totalAndares = totalAndares;
        this.portaAberta = true; // Começa com a porta aberta
        this.estadoAtual = new Parado(); // Estado inicial do elevador
        this.filaRequisicoes = new ArrayList<>();
        this.requisicoesExternasSubir = new ArrayList<>();
        this.requisicoesExternasDescer = new ArrayList<>();
        this.observadores = new ArrayList<>();
    }

    // Método para obter a única instância do elevador (Singleton)
    //A instância é armazenada em um atributo estático chamado instance, garantindo que ela seja compartilhada em todo o sistema
    public static Elevador getInstance(int totalAndares) {
        if (instance == null) {
            instance = new Elevador(totalAndares);
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
            observador.atualizar(andarAtual, status, filaRequisicoes, requisicoesExternasSubir, requisicoesExternasDescer);
        }
    }

    // Adiciona uma requisição de andar na fila
    public void adicionarRequisicao(int andar) {
        if (!filaRequisicoes.contains(andar)) {
            filaRequisicoes.add(andar);
        }
    }

    // Adiciona uma requisição externa de subir
    public void adicionarRequisicaoExternaSubir(int andar) {
        if (!requisicoesExternasSubir.contains(andar)) {
            requisicoesExternasSubir.add(andar);
        }
    }

    // Adiciona uma requisição externa de descer
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

    public void visitarAndares() {
        ordenarFila();
        while (!filaRequisicoes.isEmpty() || !requisicoesExternasSubir.isEmpty() || !requisicoesExternasDescer.isEmpty()) {
            int proximoAndar;

            // Se houver requisições internas
            if (!filaRequisicoes.isEmpty()) {
                proximoAndar = filaRequisicoes.get(0);  // Remove o andar da fila após processar
                fecharPorta();
                moverPara(proximoAndar);
                abrirPorta();

                // Atualiza o painel com a fila de requisições atualizada
                System.out.println("*Apagando luz do botão pressionado*");
                filaRequisicoes.remove(0);  // Remover o andar da fila apenas após processar
                notificarObservadores("Porta Aberta");
            } else if (!requisicoesExternasSubir.isEmpty()) {  // Prioriza requisições de subir
                proximoAndar = requisicoesExternasSubir.get(0);
                fecharPorta();
                moverPara(proximoAndar);
                abrirPorta();

                // Atualiza o painel com a fila de requisições atualizada
                System.out.println("*Limpando fila de requisições externas de subir*");
                requisicoesExternasSubir.remove(0);  // Remover o andar da fila de subir após processar
                notificarObservadores("Porta Aberta");
            } else if (!requisicoesExternasDescer.isEmpty()) {  // Prioriza requisições de descer
                proximoAndar = requisicoesExternasDescer.get(0);
                fecharPorta();
                moverPara(proximoAndar);
                abrirPorta();

                // Atualiza o painel com a fila de requisições atualizada
                System.out.println("*Limpando fila de requisições externas de descer*");
                requisicoesExternasDescer.remove(0);  // Remover o andar da fila de descer após processar
                notificarObservadores("Porta Aberta");
            }

            // Pausa de 1.5 segundos antes de mover para o próximo andar
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Notificar quando todas as requisições forem atendidas
        System.out.println("Elevador parou no andar " + andarAtual + ". Todas as requisições foram atendidas.");
    }


    // Método para mover o elevador para o andar desejado
    public void moverPara(int andar) {
        estadoAtual.mover(this, andar);
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
        return estadoAtual;
    }
}
