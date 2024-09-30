package main.elevador;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Elevador elevador = Elevador.getInstance(10); // Criando elevador com 10 andares
        Painel painel = new Painel(); // Criando o painel
        elevador.registrarObservador(painel); // Registrando o painel como observador

        //Exibe a mensagem inicial
        System.out.println("Elevador inicializado.");
        System.out.println("Andar atual: 0 (térreo). Estado atual: Parado.");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Selecione o andar para onde o elevador deve ir (pressione Enter sem digitar para iniciar o percurso): ");
            String input = scanner.nextLine();

            // Quando o usuário pressionar Enter sem digitar, inicia o percurso
            if (input.isEmpty()) {
                elevador.fecharPorta();
                elevador.visitarAndares();
                continue; // Volta para o início, permitindo mais interações
            }

            try {
                int andar = Integer.parseInt(input);
                if (andar >= 0 && andar <= 9) {  // Exemplo para um elevador com 10 andares
                    elevador.adicionarRequisicao(andar);
                } else {
                    System.out.println("Andar inválido. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            }

        }
    }
}