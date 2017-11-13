package com.electiva.vaadin.view;

import com.electiva.vaadin.entity.ManagerFactory;
import com.electiva.vaadin.entity.Materia;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Query;
import com.vaadin.navigator.View;

public class AdministrarMateria extends VerticalLayout implements View {

    public final static String VIEW_NAME = "materia";

    private Button buttonBuscar;
    private TextField textFieldBuscar;
    private Grid<Materia> tabla;
    private Button buttonGuardar;
    private Button buttonEliminar;
    private Button buttonModificar;
    private Materia materiaSelecionada;

    public AdministrarMateria() { // Constructor de la clase
        addHeader();
        addBuscar();
        addTabla();
        addBotonesCrud();
    }

    private void addHeader() {
        Label labelCabecera = new Label("Administrar Materia");
        labelCabecera.setStyleName(ValoTheme.LABEL_H1);
        addComponent(labelCabecera);
        setComponentAlignment(labelCabecera, Alignment.TOP_CENTER);
    }

    private void addBuscar() {
        this.textFieldBuscar = new TextField();
        textFieldBuscar.setPlaceholder("Buscar por Nombre de Materia");
        textFieldBuscar.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textFieldBuscar.setIcon(VaadinIcons.SEARCH);
        textFieldBuscar.setWidth(400, Sizeable.Unit.PIXELS);

        textFieldBuscar.addFocusListener((event) -> { // Cuando obtiene el foco de borra todo el TextField
            textFieldBuscar.setValue("");
        });

        // Si se preciono la tecla 'INTRO' dentro del TextField
        textFieldBuscar.addShortcutListener(new ShortcutListener("Enter", KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                Notification.show("Hey!!!");
            }
        });

        // Si se preciono la tecla 'ESCAPE' dentro del TextField
        textFieldBuscar.addShortcutListener(new ShortcutListener("Enter", KeyCode.ESCAPE, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                textFieldBuscar.setValue("");
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(textFieldBuscar);
        addComponent(horizontalLayout);
    }

    private void addTabla() {
        this.tabla = new Grid<>(Materia.class);
        tabla.setWidth(100, Sizeable.Unit.PERCENTAGE); // el ancho de la tabla
        tabla.setHeight(300, Sizeable.Unit.PIXELS); // la altura de la tabla
        tabla.setItems(getMaterias());
        tabla.setSelectionMode(SelectionMode.SINGLE);
        tabla.addSelectionListener(event -> { // Evento que 'escucha' a las filas selecionadas
            if (event.isUserOriginated()) {
                materiaSelecionada = event.getFirstSelectedItem().get();
            }
        });
        addComponent(tabla); // anadimos al VerticalLayout
    }

    private void addBotonesCrud() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button buttonAgregar = new Button("Agregar", VaadinIcons.PLUS);
        Button buttonEliminar = new Button("Eliminar", VaadinIcons.TRASH);
        Button buttonModificar = new Button("Modificar", VaadinIcons.EDIT);
        buttonEliminar.addStyleName(ValoTheme.BUTTON_DANGER);

        buttonEliminar.addClickListener((event) -> { //Evento del boton 'Eliminar'            
            if (null != materiaSelecionada) {
                mostrarPantallaEliminarMateria(materiaSelecionada);
            } else {
                Notification.show("Seleccione una fila para Eliminar");
            }
        });

        buttonAgregar.addClickListener((event) -> { //Evento del boton 'Agregar'    
            mostrarPantallaAgregarMateria();
        });

        buttonModificar.addClickListener((event) -> { //Evento del boton 'Modificar'    
            if (null != materiaSelecionada) {
                mostrarPantallaModificarMateria(materiaSelecionada);
            } else {
                Notification.show("Seleccione una fila para Modificar");
            }
        });

        horizontalLayout.addComponents(buttonAgregar, buttonModificar, buttonEliminar); // anadimos al un HorizontalLayout        
        addComponent(horizontalLayout); // anadimos al VerticalLayout
    }

    private List<Materia> getMaterias() {
        Query q = ManagerFactory.getEntityManager().createQuery("select m from Materia m");
        List<Materia> materias = new ArrayList<Materia>(q.getResultList());
        return materias;
    }

    private void mostrarPantallaAgregarMateria() {
        final Window window = new Window("Agregar Materia");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldMateria = new TextField("Nombre Materia");
        textFieldMateria.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldMateria.setPlaceholder("Nombre Materia");

        final TextField textFieldCodigo = new TextField("Codigo");
        textFieldCodigo.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldCodigo.setPlaceholder("Codigo");

        final TextField textFieldSemestre = new TextField("Semestre");
        textFieldSemestre.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldSemestre.setPlaceholder("Semestre");

        formLayout.addComponents(textFieldMateria, textFieldCodigo, textFieldSemestre);

        Button buttonGuardar = new Button("Guardar", FontAwesome.SAVE);
        buttonGuardar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button buttonCancelar = new Button("Cancelar", FontAwesome.CLOSE);
        buttonCancelar.addClickListener(e -> window.close());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(buttonGuardar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);
        
        
         buttonGuardar.addClickListener((event) -> {
            String nombre = textFieldMateria.getValue();
            String codigo = textFieldCodigo.getValue();
            Integer semestre = new Integer(textFieldSemestre.getValue());

            Materia materia = new Materia();
            materia.setNombreMateria(nombre);
            materia.setCodigoMateria(codigo);
            materia.setSemestre(semestre);

            System.out.println("" + materia);

            try {
                ManagerFactory.getEntityManager().getTransaction().begin();
                ManagerFactory.getEntityManager().persist(materia);
                ManagerFactory.getEntityManager().getTransaction().commit();
                Notification.show("Se ha guardado correctamente");
                textFieldMateria.clear();
                textFieldCodigo.clear();
                textFieldSemestre.clear();
                tabla.setItems(getMaterias());
            } catch (Exception e) {
                Notification.show("Ha ocuarrido un error", Notification.Type.ERROR_MESSAGE);
            }

        });


        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

    private void mostrarPantallaEliminarMateria(Materia materia) {
        final Window window = new Window("Eliminar Materia");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldMateria = new TextField("Nombre Materia", materia.getIdMateria().toString());
        textFieldMateria.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldMateria.setEnabled(false);

        final TextField textFieldCodigo = new TextField("Codigo", materia.getCodigoMateria());
        textFieldCodigo.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldCodigo.setEnabled(false);

        final TextField textFieldSemestre = new TextField("Semestre", materia.getSemestre().toString());
        textFieldSemestre.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldSemestre.setEnabled(false);

        formLayout.addComponents(textFieldMateria, textFieldCodigo, textFieldSemestre);

        Button buttonEliminar = new Button("Eliminar", VaadinIcons.TRASH);
        buttonEliminar.addStyleName(ValoTheme.BUTTON_DANGER);
        buttonEliminar.addClickListener(e -> {
            try {
                ManagerFactory.getEntityManager().getTransaction().begin();
                ManagerFactory.getEntityManager().remove(materia);
                ManagerFactory.getEntityManager().getTransaction().commit();
                Notification.show("Se ha eliminado correctamente el registro", Notification.Type.TRAY_NOTIFICATION);
                tabla.setItems(getMaterias());
                textFieldMateria.clear();
                textFieldCodigo.clear();
                textFieldSemestre.clear();
                materiaSelecionada = null;
                window.close();
            } catch (Exception ex) {
                Notification.show("Ha ocurrido un error", Notification.Type.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        Button buttonCancelar = new Button("Cancelar", VaadinIcons.EXIT);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(buttonEliminar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);

        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

    private void mostrarPantallaModificarMateria(Materia materia) {
        final Window window = new Window("Modificar Materia");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldMateria = new TextField("Nombre", materia.getNombreMateria());
        textFieldMateria.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        final TextField textFieldCodigo = new TextField("Codigo" , materia.getCodigoMateria().toString());
        textFieldCodigo.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        final TextField textFieldSemestre = new TextField("Semestre" , materia.getSemestre().toString());
        textFieldSemestre.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        formLayout.addComponents(textFieldMateria, textFieldCodigo, textFieldSemestre);

        Button buttonGuardar = new Button("Guardar", FontAwesome.SAVE);
        buttonGuardar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button buttonCancelar = new Button("Cancelar", FontAwesome.CLOSE);
        buttonCancelar.addClickListener(e -> window.close());
        
         buttonGuardar.addClickListener(e -> {

            try {
                ManagerFactory.getEntityManager().getTransaction().begin();

                String nombre = textFieldMateria.getValue();
                String codigo = textFieldCodigo.getValue();
                Integer semestre = new Integer(textFieldSemestre.getValue());

                materia.setNombreMateria(nombre);
                materia.setCodigoMateria(codigo);
                materia.setSemestre(semestre);

                System.out.println("" + materia);

                ManagerFactory.getEntityManager().getTransaction().commit();
                Notification.show("Se ha Modificado correctamente el registro", Notification.Type.TRAY_NOTIFICATION);
                tabla.setItems(getMaterias());
                textFieldMateria.clear();
                textFieldCodigo.clear();
                textFieldSemestre.clear();
                materiaSelecionada = null;
                window.close();
            } catch (Exception ex) {
                Notification.show("Ha ocurrido un error", Notification.Type.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(buttonGuardar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);

        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

}
