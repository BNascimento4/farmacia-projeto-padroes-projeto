package farmacia.model;

import java.util.ArrayList;
import java.util.List;


public class Venda {
    private final Cliente cliente;
    private final List<ItemVenda> itens = new ArrayList<>();

    public Venda(Cliente cliente) {
        this.cliente = cliente;
    }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
    }

    public double getTotal() {
        double total = 0;
        for (ItemVenda item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public Cliente getCliente() { return cliente; }
    public List<ItemVenda> getItens() { return itens; }

    public void imprimirRecibo() {
        System.out.println("---- RECIBO ----");
        System.out.println("Cliente: " + cliente.getNome());
        for (ItemVenda i : itens) {
            System.out.printf("%2dx %-22s R$ %7.2f%n",
                    i.getQuantidade(), i.getProduto().getNome(), i.getSubtotal());
        }
        System.out.printf("TOTAL: R$ %.2f%n", getTotal());
    }
}
