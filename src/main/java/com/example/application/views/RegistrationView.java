package com.example.application.views;

import com.example.application.controllers.UserController;
import com.example.application.models.UserModel;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("registrar-se")
@PageTitle("Semente Solidária - Redefinir senha")
public class RegistrationView extends VerticalLayout {

        private final H1 title = new H1("Criar nova conta de usuário");
        private final TextField nameField = new TextField("Nome do usuário");
        private final TextField emailField = new TextField("Email do usuário");
        private final TextField usernameField = new TextField("Username do usuário");
        private final PasswordField passwordField = new PasswordField("Senha do usuário");
        private final PasswordField confirmPasswordField = new PasswordField("Confirmar senha");

        private final Binder<UserController> binder = new Binder<>(UserController.class);

        public RegistrationView() {
                setSizeFull();
                setJustifyContentMode(JustifyContentMode.CENTER);
                setAlignItems(Alignment.CENTER);

                title.getStyle().set("font-size", "32px");
                nameField.setWidth("300px");
                emailField.setWidth("300px");
                usernameField.setWidth("300px");
                passwordField.setWidth("300px");
                confirmPasswordField.setWidth("300px");

                nameField.getStyle().set("margin", "0px");
                emailField.getStyle().set("margin", "0px");
                usernameField.getStyle().set("margin", "0px");
                passwordField.getStyle().set("margin", "0px");
                confirmPasswordField.getStyle().set("margin", "0px");

                nameField.getStyle().set("padding", "0px");
                emailField.getStyle().set("padding", "0px");
                usernameField.getStyle().set("padding", "0px");
                passwordField.getStyle().set("padding", "0px");
                confirmPasswordField.getStyle().set("padding", "0px");

                binder.forField(nameField)
                        .asRequired("Por favor, preencha o nome")
                        .withValidator(new StringLengthValidator(
                                "O nome deve ter pelo menos 2 caracteres", 2, null))
                        .bind(UserController::getName, UserController::setName);

                binder.forField(emailField)
                        .asRequired("Por favor, preencha o email")
                        .withValidator(new EmailValidator("Email inválido"))
                        .bind(UserController::getEmail, UserController::setEmail);

                binder.forField(usernameField)
                        .asRequired("Por favor, preencha o username")
                        .withValidator(new StringLengthValidator(
                                "O username deve ter pelo menos 4 caracteres", 4, null))
                        .bind(UserController::getUsername, UserController::setUsername);

                binder.forField(passwordField)
                        .asRequired("Por favor, preencha a senha")
                        .withValidator(new StringLengthValidator(
                                "A senha deve ter pelo menos 6 caracteres", 6, null))
                        .bind(UserController::getPassword, UserController::setPassword);
               
                UserController UserController = new UserController();
                binder.setBean(UserController);

                Button criarContaButton = new Button("Criar conta");
                criarContaButton.addClassName("allButtons");
                criarContaButton.setWidth("300px");
                criarContaButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                criarContaButton.setAriaLabel("Criar conta");
               
                criarContaButton.addClickListener(e -> {
                        BinderValidationStatus<UserController> validationStatus = binder.validate();

                        if (validationStatus.hasErrors()) {
                                validationStatus.getFieldValidationErrors().forEach(error -> {
                                        String errorMessage = error.getMessage().orElse("");
                                        Notification.show(errorMessage, 5000, Notification.Position.BOTTOM_START);
                                });

                                String confirmPassword = confirmPasswordField.getValue();
                                String password = passwordField.getValue();

                                if (!confirmPassword.equals(password)) {
                                        confirmPasswordField.setInvalid(true);
                                        Notification.show("A senha e a confirmação de senha não coincidem.", 5000, Notification.Position.BOTTOM_START);
                                }
                        } else {
                                String name = nameField.getValue();
                                String email = emailField.getValue();
                                String username = usernameField.getValue();
                                String password = passwordField.getValue();
                                String confirmPassword = confirmPasswordField.getValue();

                                if (!password.equals(confirmPassword)) {
                                        confirmPasswordField.setInvalid(true);
                                        Notification.show("A senha e a confirmação de senha não coincidem.", 5000, Notification.Position.BOTTOM_START);
                                        return;
                                }

                                boolean userInserted = UserModel.insertUser(name, email, username, password);

                                if (userInserted) {
                                        UI.getCurrent().navigate(LoginView.class);
                                } else {
                                        Notification.show("Erro ao criar conta. Tente novamente.", 5000, Notification.Position.BOTTOM_START);
                                }
                        }
                });

                Button voltarLoginButton = new Button("Voltar");
                voltarLoginButton.addClassName("allButtons");
                voltarLoginButton.setWidth("300px");
                voltarLoginButton.setAriaLabel("Voltar");
                voltarLoginButton.addClickListener(e -> {
                        UI.getCurrent().navigate(LoginView.class);
                });

                add(title, nameField, emailField, usernameField, passwordField, confirmPasswordField, criarContaButton, voltarLoginButton);
        }
}