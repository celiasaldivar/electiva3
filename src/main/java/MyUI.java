package com.electiva.vaadin;

import com.electiva.vaadin.view.AdministrarAlumno;
import com.electiva.vaadin.view.AdministrarMateria;
import com.electiva.vaadin.view.AdministrarNota;
import com.electiva.vaadin.view.Inicio;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;


@Theme(ValoTheme.THEME_NAME)
@Title("Electiva III")
public class MyUI extends UI {

    private Navigator navigator;
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(true);
        setWidth("100%"); 
        setHeight("100%");
        setContent(layout);
        
        ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView("", new Inicio());
        navigator.addView(AdministrarAlumno.VIEW_NAME, new AdministrarAlumno());
        navigator.addView(AdministrarMateria.VIEW_NAME, new AdministrarMateria());
        navigator.addView(AdministrarNota.VIEW_NAME, new AdministrarNota());
        
//        
//        AdministrarAlumno principal = new AdministrarAlumno();
//        layout.addComponents(principal);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
