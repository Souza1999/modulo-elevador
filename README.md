# Sistema de Controle de Elevador

Este projeto implementa um sistema de controle de elevador usando **padrões de design** para simular o comportamento de um elevador que pode ser requisitado tanto internamente (pelos passageiros dentro do elevador) quanto externamente (pelos botões de chamada nos andares). O sistema gerencia as requisições de maneira eficiente, utilizando filas de requisições separadas para subir e descer, e exibindo o estado do elevador em um painel.

## Design Patterns Aplicados

### 1. **Singleton**
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

