package com.example.application.views;

import com.example.application.views.Collections.CollectionView;
import com.example.application.views.Products.ProductView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class Layout extends AppLayout { 

    public Layout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Semente Solidária");
        logo.addClassNames(
            LumoUtility.FontSize.LARGE, 
            LumoUtility.Margin.MEDIUM);
    
        HorizontalLayout logout = new HorizontalLayout(
            new RouterLink("Logout", LoginView.class)
        );
        
        logout.addClassNames("logout-style");
        logout.addClassNames(
            LumoUtility.Padding.Vertical.NONE,
            LumoUtility.Padding.Horizontal.MEDIUM);

        var header = new HorizontalLayout(new DrawerToggle(), logo); 
    
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassName("header-style");
    
        addToNavbar(header, logout);
    }    

    private void createDrawer() {
        VerticalLayout drawerLayout = new VerticalLayout(
            new RouterLink("Doações de Produtos", ProductView.class),
            new RouterLink("Doações em Dinheiro", CollectionView.class)
        );

        drawerLayout.addClassName("drawer-style");
    
        drawerLayout.setSizeFull();
    
        addToDrawer(drawerLayout);
    }

}
