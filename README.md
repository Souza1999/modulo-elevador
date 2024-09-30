# Sistema de Controle de Elevador

Este projeto implementa um sistema de controle de elevador usando **padrões de design** para simular o comportamento de um elevador que pode ser requisitado tanto internamente (pelos passageiros dentro do elevador) quanto externamente (pelos botões de chamada nos andares). O sistema gerencia as requisições de maneira eficiente, utilizando filas de requisições separadas para subir e descer, e exibindo o estado do elevador em um painel.

## Design Patterns Aplicados

## 1. **Singleton**
O padrão **Singleton** é aplicado na classe `Elevador`, garantindo que apenas uma instância do elevador seja criada ao longo de todo o programa. Isso é importante para garantir que todos os usuários, internos e externos, estejam interagindo com a mesma instância do elevador.

#### Implementação:

A classe `Elevador` possui um construtor privado e um método `getInstance` que retorna a única instância da classe.

```java
public class Elevador {
    private static Elevador instance;

    private Elevador(int totalAndares) {
        // Inicialização do elevador
    }

    public static Elevador getInstance(int totalAndares) {
        if (instance == null) {
            instance = new Elevador(totalAndares);
        }
        return instance;
    }
}
```

## 2. **Observer**
O padrão **Observer** é utilizado para atualizar o painel do elevador sempre que ocorre uma mudança no estado do elevador. Quando o elevador se move, abre ou fecha a porta, os observadores são notificados, e o painel exibe o estado atual do elevador e a fila de requisições.

#### Implementação:
- A interface `Observer` define o método `atualizar`.
- A classe `Painel` implementa a interface `Observer` e exibe as atualizações no painel.
- A classe `Elevador` mantém uma lista de observadores e notifica todos os observadores quando uma mudança ocorre.

```java
public interface Observer {
    void atualizar(int andarAtual, String status, List<Integer> botoesPressionados);
}

public class Painel implements Observer {
    @Override
    public void atualizar(int andarAtual, String status, List<Integer> botoesPressionados) {
        System.out.println("=== Painel do Elevador ===");
        System.out.println("Andar atual: " + andarAtual);
        System.out.println("Status: " + status);
        System.out.println("Botões pressionados: " + botoesPressionados);
        System.out.println("==========================");
    }
}
```

## 3. **State**
O padrão State é utilizado para gerenciar o comportamento do elevador dependendo do seu estado atual. O elevador pode estar no estado de subindo, descendo ou parado, e cada um desses estados possui comportamentos específicos para mover o elevador, abrir ou fechar a porta.

Implementação:
A interface EstadoElevador define os métodos mover, abrirPorta, e fecharPorta.
As classes Subindo, Descendo, e Parado implementam essa interface, fornecendo o comportamento específico de cada estado.

```java
public interface EstadoElevador {
    void mover(Elevador elevador, int andarDestino);
    void abrirPorta(Elevador elevador);
    void fecharPorta(Elevador elevador);
}

public class Subindo implements EstadoElevador {
    @Override
    public void mover(Elevador elevador, int andarDestino) {
        // Lógica de movimentação ao subir
    }

    @Override
    public void abrirPorta(Elevador elevador) {
        // Lógica de abrir a porta
    }

    @Override
    public void fecharPorta(Elevador elevador) {
        // Lógica de fechar a porta
    }
}
```

