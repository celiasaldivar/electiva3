
package com.electiva.vaadin.view;

import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class Inicio extends VerticalLayout implements View {

    public final static String VIEW_NAME = "inicio";

    public Inicio() {
        setupLayout();
        addHeader();
        addBotones();
    }

    private void setupLayout() {        
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    }

    private void addHeader() {
        Label header = new Label("Inicio");
        header.addStyleName(ValoTheme.LABEL_H1);
        addComponent(header);
    }

    private void addBotones() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button btnAlumno = new Button("Administrar Alumno");
        Button btnMateria = new Button("Administrar Materias");
        Button btnNotas = new Button("Administrar Notas");

        btnAlumno.addClickListener((event) -> {
            getUI().getNavigator().navigateTo(AdministrarAlumno.VIEW_NAME);
        });

        btnMateria.addClickListener((event) -> {
            getUI().getNavigator().navigateTo(AdministrarMateria.VIEW_NAME);
        });
        
        btnNotas.addClickListener((event) -> {
            getUI().getNavigator().navigateTo(AdministrarNota.VIEW_NAME);
        });

        horizontalLayout.setSpacing(true);
        horizontalLayout.addComponents(btnAlumno, btnMateria, btnNotas);

        addComponent(horizontalLayout);

    }

}
