# Sistema de Farmácia (POO em Java)

Compilar e rodar (dentro de `farmacia/`):

mkdir out
javac -d out -sourcepath src src/farmacia/app/Main.java
java -cp out farmacia.app.Main

## Onde cada requisito aparece (Mapeamento Arquitetural)

| Requisito da Disciplina | Onde e Como foi Implementado |
| :--- | :--- |
| **Associação e Composição** | A classe `Venda` associa-se a um `Cliente` (via construtor) e compõe uma lista de múltiplos objetos `ItemVenda`. O `MenuFarmacia` mantém uma associação com o `VendaService`. |
| **Coleções (Collections)** | Utilização nativa da interface `List` (com `ArrayList`) para gerenciar o carrinho de compras na classe `Venda` e o estoque completo no `ProdutoRepositorioMemoria`. |
| **Herança e Classes Abstratas** | `Produto` atua como classe base abstrata. As classes `Medicamento` e `Cosmetico` herdam diretamente dela, enquanto `MedicamentoControlado` aprofunda a hierarquia especializando `Medicamento`. |
| **Polimorfismo** | Aplicado no cálculo de totais sem o uso de `if/else`: a classe `ItemVenda` chama `produto.getPrecoFinal()` e a classe `VendaService` chama `pagamento.calcularValorFinal()`. O comportamento muda dinamicamente dependendo do objeto injetado. |
| **Uso de Interfaces** | Criação de contratos arquiteturais rigorosos através da interface `ProdutoRepositorio` (abstração de dados) e da interface `Pagamento` (abstração de transações). |
| **Tratamento de Exceções** | Criação de 4 *checked exceptions* personalizadas, separando erros de negócio (ex: `EstoqueInsuficienteException`) de erros de interface (ex: `TipoProdutoInvalidoException`). Todas são obrigatoriamente capturadas e tratadas com blocos `try/catch` no `MenuFarmacia`. |
| **Injeção de Dependência (DI)** | As classes não instanciam as suas próprias dependências. O repositório e os serviços são criados na classe `Main` e injetados via construtor no `VendaService` e `MenuFarmacia`, reduzindo o acoplamento. |
| **Princípios SOLID** | **SRP:** `MenuFarmacia` isola a interação (I/O) e o `VendaService` centraliza as regras de negócio. **OCP:** Novas formas de pagamento podem ser adicionadas sem alterar a venda. **DIP:** O serviço de vendas depende apenas da abstração do repositório, não da sua implementação concreta. |
| **Padrões de Projeto (GoF)** | Uso do **Factory Pattern** através da `PagamentoFactory`, que encapsula a lógica de criação e devolve a implementação correta de pagamento com base na escolha do usuário. |