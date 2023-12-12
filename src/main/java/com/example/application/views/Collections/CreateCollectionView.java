package com.example.application.views.Collections;

import com.example.application.controllers.CollectionController;
import com.example.application.controllers.UserController;
import com.example.application.models.CollectionModel;
import com.example.application.views.Layout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;

@Route(value = "criar/arrecadacao", layout = Layout.class)
public class CreateCollectionView extends VerticalLayout {

    private final H1 title = new H1("Cadastrar nova Arrecadação");
    private final NumberField arrecadacaoValorDoadoField = new NumberField("Valor da Doação");
    private final ComboBox<String> arrecadacaoFormaDoacaoField = new ComboBox<>("Forma da Doação");
    private final DatePicker arrecadacaoDataPicker = new DatePicker("Data da Doação");
    private final TextField arrecadacaoNomeDoadorField = new TextField("Nome do Doador (Opcional)");

    private final Binder<CollectionController> binder = new Binder<>(CollectionController.class);
   
    public CreateCollectionView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        title.getStyle().set("font-size", "32px");
        arrecadacaoValorDoadoField.setWidth("300px");
        arrecadacaoFormaDoacaoField.setWidth("300px");
        arrecadacaoFormaDoacaoField.setItems("Cartão de crédito", "Cartão de débito", "Pix", "Dinheiro");
        arrecadacaoDataPicker.setWidth("300px");
        arrecadacaoNomeDoadorField.setWidth("300px");

        arrecadacaoValorDoadoField.getStyle().set("margin", "0px");
        arrecadacaoFormaDoacaoField.getStyle().set("margin", "0px");
        arrecadacaoDataPicker.getStyle().set("margin", "0px");
        arrecadacaoNomeDoadorField.getStyle().set("margin", "0px");

        arrecadacaoValorDoadoField.getStyle().set("padding", "0px");
        arrecadacaoFormaDoacaoField.getStyle().set("padding", "0px");
        arrecadacaoDataPicker.getStyle().set("padding", "0px");
        arrecadacaoNomeDoadorField.getStyle().set("padding", "0px");

        binder.forField(arrecadacaoValorDoadoField)
            .asRequired("Por favor, preencha o valor doado")
            .bind(CollectionController::getValorDoado, CollectionController::setValorDoado);

        binder.forField(arrecadacaoFormaDoacaoField)
            .asRequired("Por favor, selecione a forma de doação")
            .bind(CollectionController::getFormaDoacao, CollectionController::setFormaDoacao);
        
        binder.forField(arrecadacaoDataPicker)
            .asRequired("Por favor, preencha a data")
            .withConverter(new LocalDateToDateConverter())
            .bind(CollectionController::getData, CollectionController::setData);
           
        CollectionController collectionController = new CollectionController();
        binder.setBean(collectionController);

        Button salvarArrecadacaoButton = new Button("Criar Arrecadação");
        salvarArrecadacaoButton.addClassName("allButtons");  
        salvarArrecadacaoButton.setWidth("300px");
        salvarArrecadacaoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarArrecadacaoButton.setAriaLabel("Criar arrecadação");
        salvarArrecadacaoButton.addClickListener(e -> {
            BinderValidationStatus<CollectionController> validationStatus = binder.validate();

            if (validationStatus.hasErrors()) {
                    validationStatus.getFieldValidationErrors().forEach(error -> {
                            String errorMessage = error.getMessage().orElse("");
                            Notification.show(errorMessage, 5000, Notification.Position.BOTTOM_START);
                    });

            } else {
                    Double valorDoado = arrecadacaoValorDoadoField.getValue();
                    String formaDoacao = arrecadacaoFormaDoacaoField.getValue();
                    java.sql.Date data = new java.sql.Date(arrecadacaoDataPicker.getValue().atStartOfDay().toInstant(java.time.ZoneOffset.UTC).toEpochMilli());
                    String nomeDoador = arrecadacaoNomeDoadorField.getValue();
                    System.err.println(nomeDoador);

                    int userId = UserController.getId();
                    boolean insertCollection = CollectionModel.insertCollection(valorDoado, formaDoacao, data, nomeDoador, userId);

                    if (insertCollection) {
                            UI.getCurrent().navigate(CollectionView.class);
                    } else {
                            Notification.show("Erro ao criar arrecadação. Tente novamente.", 5000, Notification.Position.BOTTOM_START);
                    }

                }
        });

        add(title, arrecadacaoValorDoadoField, arrecadacaoFormaDoacaoField, arrecadacaoDataPicker, arrecadacaoNomeDoadorField, salvarArrecadacaoButton);
    }
}