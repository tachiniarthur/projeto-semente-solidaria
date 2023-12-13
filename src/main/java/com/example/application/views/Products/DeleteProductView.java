package com.example.application.views.Products;

import com.example.application.models.ProductModel;
import com.example.application.views.Layout;
import com.example.application.views.Collections.CollectionView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "deletar/produto/:id", layout = Layout.class) 
public class DeleteProductView extends VerticalLayout implements BeforeEnterObserver {

    private int produtoId;
    private final H1 title = new H1("Tem certeza que deseja excluir esse item?");


    public DeleteProductView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        title.getStyle().set("font-size", "32px");

        Button confirmarExclusaoButton = new Button("Confirmar Exclusão");
        confirmarExclusaoButton.addClickListener(e -> {
            excluirProduto();
            UI.getCurrent().navigate(ProductView.class);
        });

        confirmarExclusaoButton.getStyle().set("background-color", "#ec5353");
        confirmarExclusaoButton.getStyle().set("color", "#ffffff");

        Button voltarButton = new Button("Cancelar Exclusão");
        voltarButton.addClickListener(e -> {
            UI.getCurrent().navigate(ProductView.class);
        });
        voltarButton.addClassName("allButtons");
        voltarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, confirmarExclusaoButton, voltarButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String idParameter = event.getRouteParameters().get("id").orElse("");
        try {
            produtoId = Integer.parseInt(idParameter);
        } catch (NumberFormatException e) {
            System.out.println("Erro ao deletar produto");
        }
    }

    private void excluirProduto() {
        ProductModel.delete(produtoId);
    }
}
