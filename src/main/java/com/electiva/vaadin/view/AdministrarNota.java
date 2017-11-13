package com.electiva.vaadin.view;

import com.electiva.vaadin.entity.Alumno;
import com.electiva.vaadin.entity.ManagerFactory;
import com.electiva.vaadin.entity.Materia;
import com.electiva.vaadin.entity.Nota;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
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
import java.util.List;
import java.util.Set;
import com.vaadin.navigator.View;
import java.util.ArrayList;
import javax.persistence.Query;

public class AdministrarNota extends VerticalLayout implements View {

    public final static String VIEW_NAME = "nota";

    private Button buttonBuscar;
    private TextField textFieldBuscar;
    private Grid<Nota> tabla;
    private Button buttonGuardar;
    private Button buttonEliminar;
    private Button buttonModificar;
    private Nota notaSeleccionada;

    public AdministrarNota() { // Constructor de la clase
        addTitulo();
        addBuscar();
        addTabla();
        addBotonesCrud();
    }

    private void addTitulo() {
        Label labelCabecera = new Label("Administrar Notas");
        labelCabecera.setStyleName(ValoTheme.LABEL_H1);
        addComponent(labelCabecera);
        setComponentAlignment(labelCabecera, Alignment.TOP_CENTER);
    }

    private void addBuscar() {
        this.textFieldBuscar = new TextField();
        textFieldBuscar.setPlaceholder("Buscar por ID de Alumno o Nombre de Notas");
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
        this.tabla = new Grid<Nota>(Nota.class);
        tabla.setWidth(100, Sizeable.Unit.PERCENTAGE); // el ancho de la tabla
        tabla.setHeight(300, Sizeable.Unit.PIXELS); // la altura de la tabla
        tabla.removeColumn("notaPK");
        tabla.setItems(getNotas());
        tabla.setSelectionMode(SelectionMode.SINGLE);
        tabla.addSelectionListener(event -> { // Evento que 'escucha' a las filas selecionadas
            if (event.isUserOriginated()) {
                notaSeleccionada = event.getFirstSelectedItem().get();
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
            if (null != notaSeleccionada) {
                mostrarPantallaEliminarNota(notaSeleccionada);
            } else {
                Notification.show("Seleccione una fila para Eliminar");
            }
        });

        buttonAgregar.addClickListener((event) -> mostrarPantallaAgregarNota()); //Evento del boton 'Agregar'  

        buttonModificar.addClickListener((event) -> { //Evento del boton 'Modificar'    
            if (null != notaSeleccionada) {
                mostrarPantallaModificarNota(notaSeleccionada);
            } else {
                Notification.show("Seleccione una fila para Modificar");
            }
        });

        horizontalLayout.addComponents(buttonAgregar, buttonModificar, buttonEliminar); // anadimos al un HorizontalLayout
        addComponent(horizontalLayout); // anadimos al VerticalLayout
    }

    private List<Nota> getNotas() {
        Query q = ManagerFactory.getEntityManager().createQuery("select n FROM Nota n");
        List<Nota> notas = new ArrayList<Nota>(q.getResultList());
        System.out.println(">>>>>>>>>>>>>>>>>> " + notas);
        return notas;
    }

    private void mostrarPantallaAgregarNota() {
        final Window window = new Window("Agregar Materia");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldIdAlumno = new TextField("Alumno");
        textFieldIdAlumno.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldIdAlumno.setPlaceholder("Alumno");

        final TextField textFieldIdMateria = new TextField("Materia");
        textFieldIdMateria.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldIdMateria.setPlaceholder("Materia");

        final TextField textFieldNota = new TextField("Nota");
        textFieldNota.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldNota.setPlaceholder("Nota");

        formLayout.addComponents(textFieldIdAlumno,
                textFieldIdMateria, textFieldNota);

        Button buttonGuardar = new Button("Guardar", FontAwesome.SAVE);
        buttonGuardar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button buttonCancelar = new Button("Cancelar", FontAwesome.CLOSE);
        buttonCancelar.addClickListener(e -> window.close());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(buttonGuardar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);

        buttonGuardar.addClickListener((event) -> {
            Integer idAlumno = new Integer(textFieldIdAlumno.getValue());
            Integer idMateria = new Integer(textFieldIdMateria.getValue());
            Integer notaCalifi = new Integer(textFieldNota.getValue());

            try {
                ManagerFactory.getEntityManager().getTransaction().begin();

                Nota nota = new Nota(idMateria, idAlumno);
                nota.setNota(notaCalifi);
                System.out.println("" + nota);
                ManagerFactory.getEntityManager().persist(nota);
                ManagerFactory.getEntityManager().getTransaction().commit();
                Notification.show("Se ha guardado correctamente");
                textFieldNota.clear();
                textFieldIdAlumno.clear();
                textFieldIdMateria.clear();
                tabla.setItems(getNotas());
            } catch (Exception ex) {
                Notification.show("Ha ocuarrido un error", Notification.Type.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

    private void mostrarPantallaEliminarNota(Nota nota) {
        final Window window = new Window("Eliminar Materia");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldIdAlumno = new TextField("Alumno");
        textFieldIdAlumno.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldIdAlumno.setPlaceholder("Alumno");

        final TextField textFieldIdMateria = new TextField("Materia");
        textFieldIdMateria.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldIdMateria.setPlaceholder("Materia");

        final TextField textFieldNota = new TextField("Nota");
        textFieldNota.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldNota.setPlaceholder("Nota");

        formLayout.addComponents(textFieldIdAlumno,
                textFieldIdMateria, textFieldNota);

        Button buttonEliminar = new Button("Eliminar", VaadinIcons.TRASH);
        buttonEliminar.addStyleName(ValoTheme.BUTTON_DANGER);
        Button buttonCancelar = new Button("Cancelar", VaadinIcons.EXIT);

        buttonEliminar.addClickListener(e -> {
            try {
                ManagerFactory.getEntityManager().getTransaction().begin();
                ManagerFactory.getEntityManager().remove(nota);
                ManagerFactory.getEntityManager().getTransaction().commit();
                Notification.show("Se ha eliminado correctamente el registro", Notification.Type.TRAY_NOTIFICATION);
                tabla.setItems(getNotas());
                textFieldIdAlumno.clear();
                textFieldIdMateria.clear();
                textFieldNota.clear();
                notaSeleccionada = null;
                window.close();
            } catch (Exception ex) {
                Notification.show("Ha ocurrido un error", Notification.Type.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(buttonEliminar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);

        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

    private void mostrarPantallaModificarNota(Nota nota) {
        final Window window = new Window("Modificar Materia");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldIdAlumno = new TextField("Alumno", nota.getAlumno().getIdAlumno().toString());
        textFieldIdAlumno.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldIdAlumno.setEnabled(false);

        final TextField textFieldIdMateria = new TextField("Materia", nota.getMateria().getIdMateria().toString());
        textFieldIdMateria.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldIdMateria.setEnabled(false);

        final TextField textFieldNota = new TextField("Nota", nota.getNota().toString());
        textFieldNota.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        formLayout.addComponents(textFieldIdAlumno,
                textFieldIdMateria, textFieldNota);

        Button buttonGuardar = new Button("Guardar", FontAwesome.SAVE);
        buttonGuardar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button buttonCancelar = new Button("Cancelar", FontAwesome.CLOSE);
        buttonCancelar.addClickListener(e -> window.close());
        
        buttonGuardar.addClickListener(e -> {

            try {
                ManagerFactory.getEntityManager().getTransaction().begin();

                Integer notaCalf = new Integer(textFieldNota.getValue());
                nota.setNota(notaCalf);

                System.out.println("" + nota);

                ManagerFactory.getEntityManager().getTransaction().commit();
                Notification.show("Se ha Modificado correctamente el registro", Notification.Type.TRAY_NOTIFICATION);
                tabla.setItems(getNotas());
                textFieldIdAlumno.clear();
                textFieldIdMateria.clear();
                textFieldNota.clear();
                notaSeleccionada = null;
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
