package com.example.application.views;

import com.example.application.controllers.UserController;
import com.example.application.models.UserModel;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Semente Solidária - Login")
public class LoginView extends VerticalLayout {

    private final H1 title = new H1("Semente Solidária");
    private final TextField usernameField = new TextField("Username");
    private final PasswordField passwordField = new PasswordField("Senha");
    private final H2 textQuestionCreateAccount = new H2("Ainda não possui uma conta?");

    private final Binder<UserController> binder = new Binder<>(UserController.class);

    public LoginView(){
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        title.getStyle().set("font-size", "44px");

        textQuestionCreateAccount.getStyle().set("font-size", "16px");
        textQuestionCreateAccount.getStyle().set("padding-top", "12px");
        textQuestionCreateAccount.getStyle().set("padding-bottom", "0px");

        usernameField.setWidth("300px");
        passwordField.setWidth("300px");

        usernameField.getStyle().set("margin", "0px");
        usernameField.getStyle().set("margin-top", "32px");
        passwordField.getStyle().set("margin", "0px");

        usernameField.getStyle().set("padding", "0px");
        passwordField.getStyle().set("padding", "0px");

        binder.forField(usernameField)
                .asRequired("Por favor, preencha o username")
                .bind(UserController::getUsername, UserController::setUsername);

        binder.forField(passwordField)
                .asRequired("Por favor, preencha a senha")
                .bind(UserController::getPassword, UserController::setPassword);
       
        UserController UserController = new UserController();
        binder.setBean(UserController);

        Button loginButton = new Button("Login");
        loginButton.setWidth("300px");
        loginButton.addClassName("allButtons");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.setAriaLabel("Login");
       
        loginButton.addClickListener(e -> {
                BinderValidationStatus<UserController> validationStatus = binder.validate();

                if (validationStatus.hasErrors()) {
                        validationStatus.getFieldValidationErrors().forEach(error -> {
                                String errorMessage = error.getMessage().orElse("");
                                Notification.show(errorMessage, 5000, Notification.Position.BOTTOM_START);
                        });
                } else {
                        String username = usernameField.getValue();
                        String password = passwordField.getValue();

                        boolean isAuthenticated = UserModel.checkCredentials(username, password);
                        if (isAuthenticated) {
                                int userId = UserModel.getUserIdByUsername(username);
                                UserController.setUserId(userId);
                                e.getSource().getUI().ifPresent(ui -> ui.navigate(InicioView.class));
                        } else {
                                Notification.show("Usuário ou senhão não encontrado! Verifique suas informações e tente novamente!", 5000, Notification.Position.BOTTOM_START);
                        }
                }
            });

        Button esqueciMinhaSenhaButton = new Button("Esqueci minha senha");
        esqueciMinhaSenhaButton.setWidth("300px");
        esqueciMinhaSenhaButton.addClassName("buttonForget");
        esqueciMinhaSenhaButton.addClassName("allButtons");
        esqueciMinhaSenhaButton.setAriaLabel("Esqueci minha senha");
        esqueciMinhaSenhaButton.addClickListener(e -> {
                UI.getCurrent().navigate(ForgetPasswordView.class);
        });

        Button criarContaButton = new Button("Cadastre-se");
        criarContaButton.setWidth("300px");
        criarContaButton.addClassName("allButtons");
        criarContaButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        criarContaButton.setAriaLabel("Cadastre-se");
        criarContaButton.addClickListener(e -> {
                UI.getCurrent().navigate(RegistrationView.class);
        });

        add(title, usernameField, passwordField, loginButton, esqueciMinhaSenhaButton, textQuestionCreateAccount, criarContaButton);

    }
}