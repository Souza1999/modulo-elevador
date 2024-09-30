package main.elevador;

public class BotaoExterno {
    private int andar;
    private boolean subir;
    private boolean descer;

    public BotaoExterno(int andar) {
        this.andar = andar;
        this.subir = false;
        this.descer = false;
    }

    // Pressionar o botão de subir
    public void pressionarSubir() {
        this.subir = true;
        System.out.println("Botão de subir pressionado no andar " + andar);
    }

    // Pressionar o botão de descer
    public void pressionarDescer() {
        this.descer = true;
        System.out.println("Botão de descer pressionado no andar " + andar);
    }

    public boolean isSubir() {
        return subir;
    }

    public boolean isDescer() {
        return descer;
    }

    // Resetar os botões após o elevador atender a requisição
    public void resetar() {
        this.subir = false;
        this.descer = false;
    }

    public int getAndar() {
        return andar;
    }
}
