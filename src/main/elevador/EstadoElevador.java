package main.elevador;

public interface EstadoElevador {
    void mover(Elevador elevador, int andarDestino);
    void abrirPorta(Elevador elevador);
    void fecharPorta(Elevador elevador);
}
