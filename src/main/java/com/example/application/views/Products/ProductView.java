package com.example.application.views.Products;

import com.example.application.controllers.ProductController;
import com.example.application.controllers.UserController;
import com.example.application.models.ProductModel;
import com.example.application.views.Layout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "produtos", layout = Layout.class)
public class ProductView extends VerticalLayout {

    private final Grid<ProductController> produtoGrid = new Grid<>(ProductController.class);
   
    public ProductView() {
       
        Button novoProdutoButton = new Button("Novo Produto", new Icon(VaadinIcon.PLUS));
        novoProdutoButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        novoProdutoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        novoProdutoButton.setAriaLabel("Adicionar produto");
        novoProdutoButton.addClickListener(e -> {
            UI.getCurrent().navigate(CreateProductView.class);
        });

        novoProdutoButton.getStyle().set("background-color", "#13a815");
        novoProdutoButton.getStyle().set("color", "#ffffff");

        produtoGrid.setColumns("nome", "tipoProduto", "quantidade", "data", "doadorNome");

        produtoGrid.addComponentColumn(item -> {
            Button editButton = new Button("Editar");
            editButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
            editButton.addClickListener(e -> {
                UI.getCurrent().navigate("editar/produto/" + item.getIdProduct());
            });
            return editButton;
        });
       
        produtoGrid.addComponentColumn(item -> {
            Button deleteButton = new Button("Excluir");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> {
                UI.getCurrent().navigate("deletar/produto/" + item.getIdProduct());
            });

            deleteButton.getStyle().set("background-color", "#ec5353");
            deleteButton.getStyle().set("color", "#ffffff");
            return deleteButton;
        });
           

        add(novoProdutoButton, produtoGrid);
        listarProdutos();
    }

    private void listarProdutos() {
        Integer userId = UserController.getId();
        produtoGrid.setItems(ProductModel.getAll(userId));
    }
}