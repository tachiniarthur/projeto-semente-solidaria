package com.example.application.views.Collections;

import com.example.application.controllers.CollectionController;
import com.example.application.controllers.UserController;
import com.example.application.models.CollectionModel;
import com.example.application.views.Layout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "arrecadacoes", layout = Layout.class)
public class CollectionView extends VerticalLayout {

    private final Grid<CollectionController> arrecadacaoGrid = new Grid<>(CollectionController.class);
   
    public CollectionView() {
       
        Button novaArrecadacaoButton = new Button("Nova Arrecadação", new Icon(VaadinIcon.PLUS));
        novaArrecadacaoButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        novaArrecadacaoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        novaArrecadacaoButton.setAriaLabel("Adicionar arrecadação");
        novaArrecadacaoButton.addClickListener(e -> {
            UI.getCurrent().navigate(CreateCollectionView.class);
        });

        novaArrecadacaoButton.getStyle().set("background-color", "#13a815");
        novaArrecadacaoButton.getStyle().set("color", "#ffffff");

        arrecadacaoGrid.setColumns("valorDoado", "formaDoacao", "data", "nomeDoador");

        arrecadacaoGrid.addComponentColumn(item -> {
            Button editButton = new Button("Editar");
            editButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
            editButton.addClickListener(e -> {
                UI.getCurrent().navigate("editar/arrecadacao/" + item.getId());
            });
            return editButton;
        });
       
        arrecadacaoGrid.addComponentColumn(item -> {
            Button deleteButton = new Button("Excluir");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> {
                UI.getCurrent().navigate("deletar/arrecadacao/" + item.getId());
            });

            deleteButton.getStyle().set("background-color", "#ec5353");
            deleteButton.getStyle().set("color", "#ffffff");
            return deleteButton;
        });

        add(novaArrecadacaoButton, arrecadacaoGrid);
        listarArrecadacoes();
    }

    private void listarArrecadacoes() {
        Integer userId = UserController.getId();
        arrecadacaoGrid.setItems(CollectionModel.getAll(userId));
    }

}