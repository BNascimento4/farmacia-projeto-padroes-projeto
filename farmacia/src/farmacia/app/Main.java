package farmacia.app;

import farmacia.repository.ProdutoRepositorio;
import farmacia.repository.ProdutoRepositorioMemoria;
import farmacia.model.*;
import farmacia.service.VendaService;
import farmacia.view.MenuFarmacia;

public class Main {
    public static void main(String[] args) {

        ProdutoRepositorio repositorio = new ProdutoRepositorioMemoria();
        VendaService service = new VendaService(repositorio);

        repositorio.adicionar(new Medicamento(1, "Dipirona 500mg", 8.50, 20));
        repositorio.adicionar(new Medicamento(2, "Ibuprofeno 400mg", 15.90, 10));
        repositorio.adicionar(new MedicamentoControlado(3, "Clonazepam 2mg", 32.00, 5));
        repositorio.adicionar(new Cosmetico(4, "Protetor Solar FPS 50", 45.00, 8));

        MenuFarmacia menu = new MenuFarmacia(repositorio, service);
        menu.iniciar();
    }
}