package com.example.application.views.Products;

import com.example.application.controllers.ProductController;
import com.example.application.controllers.UserController;
import com.example.application.models.ProductModel;
import com.example.application.views.Layout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;

@Route(value = "criar/produto", layout = Layout.class)
public class CreateProductView extends VerticalLayout {

        private final H1 title = new H1("Cadastrar novo Produto");
        private final TextField produtoNomeField = new TextField("Nome do Produto");
        private final ComboBox<String> produtoTipoField = new ComboBox<>("Tipo do Produto");
        private final IntegerField produtoQuantidadeField = new IntegerField("Quantidade de Produtos");
        private final DatePicker produtoDataPicker = new DatePicker("Data do recebimento do Produto");
        private final TextField produtoNomeDoadorField = new TextField("Nome do Doador do Produto (Opcional)");

        private final Binder<ProductController> binder = new Binder<>(ProductController.class);

        public CreateProductView() {
                setSizeFull();
                setJustifyContentMode(JustifyContentMode.CENTER);
                setAlignItems(Alignment.CENTER);

                title.getStyle().set("font-size", "32px");
                produtoNomeField.setWidth("300px");
                produtoTipoField.setWidth("300px");
                produtoTipoField.setItems("Alimento", "Brinquedo", "Equipamentos eletrônicos", "Equipamentos esportivos", "Higiene", "Livros", "Material escolar", "Móveis", "Produtos para bebês", "Roupas e calçados");
                produtoQuantidadeField.setWidth("300px");
                produtoDataPicker.setWidth("300px");
                produtoNomeDoadorField.setWidth("300px");

                produtoNomeField.getStyle().set("margin", "0px");
                produtoTipoField.getStyle().set("margin", "0px");
                produtoQuantidadeField.getStyle().set("margin", "0px");
                produtoDataPicker.getStyle().set("margin", "0px");
                produtoNomeDoadorField.getStyle().set("margin", "0px");

                produtoNomeField.getStyle().set("padding", "0px");
                produtoTipoField.getStyle().set("padding", "0px");
                produtoQuantidadeField.getStyle().set("padding", "0px");
                produtoDataPicker.getStyle().set("padding", "0px");
                produtoNomeDoadorField.getStyle().set("padding", "0px");

                binder.forField(produtoNomeField)
                .asRequired("Por favor, preencha o campo do nome do produto")
                .withValidator(new StringLengthValidator(
                        "O tipo deve ter pelo menos 2 caracteres", 2, null))
                .bind(ProductController::getNome, ProductController::setNome);

                binder.forField(produtoQuantidadeField)
                .asRequired("Por favor, preencha o valor")
                .bind(ProductController::getQuantidade, ProductController::setQuantidade);
                
                binder.forField(produtoTipoField)
                .asRequired("Por favor, selecione o tipo do produto")
                .bind(ProductController::getTipoProduto, ProductController::setTipoProduto);

                binder.forField(produtoDataPicker)
                .asRequired("Por favor, preencha a data")
                .withConverter(new LocalDateToDateConverter())
                .bind(ProductController::getData, ProductController::setData);

                ProductController ProductController = new ProductController();
                binder.setBean(ProductController);

                Button salvarProdutoButton = new Button("Criar Produto");
                salvarProdutoButton.addClassName("allButtons");  
                salvarProdutoButton.setWidth("300px");
                salvarProdutoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                salvarProdutoButton.setAriaLabel("Criar produto");
                salvarProdutoButton.addClickListener(e -> {
                BinderValidationStatus<ProductController> validationStatus = binder.validate();

                if (validationStatus.hasErrors()) {
                        validationStatus.getFieldValidationErrors().forEach(error -> {
                                String errorMessage = error.getMessage().orElse("");
                                Notification.show(errorMessage, 5000, Notification.Position.BOTTOM_START);
                        });

                } else {
                        String nome = produtoNomeField.getValue();
                        String tipo = produtoTipoField.getValue();
                        int quantidade = produtoQuantidadeField.getValue();
                        java.sql.Date data = new java.sql.Date(produtoDataPicker.getValue().atStartOfDay().toInstant(java.time.ZoneOffset.UTC).toEpochMilli());
                        String nomeDoador = produtoNomeDoadorField.getValue();
                        int userId = UserController.getId();

                        boolean insertProduct = ProductModel.insertProduct(nome, tipo, quantidade, data, nomeDoador, userId);

                        if (insertProduct) {
                                UI.getCurrent().navigate(ProductView.class);
                        } else {
                                Notification.show("Erro ao criar produto. Tente novamente.", 5000, Notification.Position.BOTTOM_START);
                        }
                }
                });

                add(title, produtoNomeField, produtoTipoField, produtoQuantidadeField, produtoDataPicker, produtoNomeDoadorField, salvarProdutoButton);

        }

}