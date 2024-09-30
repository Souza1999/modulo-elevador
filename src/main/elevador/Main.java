package main.elevador;

import java.util.Scanner;

//novo main com requisições externas

public class Main {
    public static void main(String[] args) {
        Elevador elevador = Elevador.getInstance(10);
        Painel painel = new Painel();
        System.out.println("<< Elevador inicializado >>");
        elevador.registrarObservador(painel);
        System.out.println("<< O elevador está parado no térreo (Andar 0) >>");


        BotaoExterno[] botoesExternos = new BotaoExterno[10];
        for (int i = 0; i < botoesExternos.length; i++) {
            botoesExternos[i] = new BotaoExterno(i);
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Pressione 1 para adicionar um passageiro dentro do elevador\n ou 2 para um passageiro fora (botões subir/descer):");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a linha para evitar problemas

            if (opcao == 1) {
                // Passageiro dentro do elevador adiciona múltiplas requisições
                System.out.println("Digite os andares para onde o elevador deve ir\n(Digite o andar desejado e pressione Enter. \n Para iniciar o percurso, pressione apenas Enter.): ");
                while (true) {
                    String input = scanner.nextLine();
                    if (input.isEmpty()) {
                        break;  // Inicia o percurso quando o usuário pressiona Enter sem digitar
                    }

                    try {
                        int andar = Integer.parseInt(input);
                        if (andar >= 0 && andar <= 9) {
                            elevador.adicionarRequisicao(andar);
                        } else {
                            System.out.println("Andar inválido. Selecione um andar entre 0 e 9.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Por favor, insira um número entre 0 e 9.");
                    }
                }
                elevador.visitarAndares();
            } else if (opcao == 2) {
                // Passageiro fora do elevador pressiona o botão
                System.out.println("Selecione o andar do passageiro (0 a 9): ");
                int andar = scanner.nextInt();
                scanner.nextLine(); // Consumir a linha


                if (andar >= 0 && andar <= 9) {
                    System.out.println("Pressione 1 para subir ou 2 para descer: ");
                    int direcao = scanner.nextInt();

                    if (direcao == 1) {
                        botoesExternos[andar].pressionarSubir();
                        elevador.adicionarRequisicaoExternaSubir(andar);
                    } else if (direcao == 2) {
                        botoesExternos[andar].pressionarDescer();
                        elevador.adicionarRequisicaoExternaDescer(andar);
                    }

                    elevador.visitarAndares();  // Chamamos visitarAndares após adicionar a requisição externa
                } else {
                    System.out.println("Andar inválido. Por favor, selecione um andar entre 0 e 9.");
                }
            }
        }
    }
}



//MAIN ANTIGO, DESCOMENTAR PARA USAR
/*
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
*/