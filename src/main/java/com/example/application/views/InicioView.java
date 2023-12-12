package com.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "inicio", layout = Layout.class) 
@PageTitle("Semente Solidária - Início")
public class InicioView extends VerticalLayout {
  
  private final H1 title = new H1("Semente Solidária");
  private final H2 subtitle = new H2("Bem vindo Sementinha");

  public InicioView()
  {
    setSizeFull();
    setJustifyContentMode(JustifyContentMode.START);
    setAlignItems(Alignment.CENTER);

    title.getStyle().set("font-size", "32px");
    subtitle.getStyle().set("font-size", "20px");
    
    add(title, subtitle);
  }

}