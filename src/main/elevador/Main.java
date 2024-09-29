package main.elevador;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Elevador elevador = Elevador.getInstance(10); // Criando elevador com 10 andares
        Painel painel = new Painel(); // Criando o painel
        elevador.registrarObservador(painel); // Registrando o painel como observador

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Selecione o andar para onde o elevador deve ir (0 a 9): ");
            int andar = scanner.nextInt();

            // Verifica se o andar está dentro dos limites
            if (andar < 0 || andar > 9) {
                System.out.println("Andar inválido. Selecione um andar entre 0 e 9.");
                continue; // Volta para a próxima iteração do loop
            }

            elevador.adicionarRequisicao(andar);
            elevador.moverPara(andar);
        }
    }
}
