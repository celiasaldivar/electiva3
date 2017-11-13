package com.electiva.vaadin.view;

import com.electiva.vaadin.entity.Alumno;
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


public class AdministrarNota extends VerticalLayout implements View {

    public final static String VIEW_NAME = "nota";
    
    private Button buttonBuscar;
    private TextField textFieldBuscar;
    private Grid<Alumno> tabla;
    private Button buttonGuardar;
    private Button buttonEliminar;
    private Button buttonModificar;
    private Set<Alumno> alumnosSelecionados;

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
        this.tabla = new Grid<>(Alumno.class);
        tabla.setWidth(100, Sizeable.Unit.PERCENTAGE); // el ancho de la tabla
        tabla.setHeight(300, Sizeable.Unit.PIXELS); // la altura de la tabla
        //tabla.setItems(getNotas());         
        tabla.setSelectionMode(SelectionMode.SINGLE);
        tabla.addSelectionListener(event -> { // Evento que 'escucha' a las filas selecionadas
            alumnosSelecionados = event.getAllSelectedItems();
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
            if (null != alumnosSelecionados) {
                if (alumnosSelecionados.size() > 0) {
                    mostrarPantallaEliminarNota();
                }
            } else {
                Notification.show("Seleccione una fila para Eliminar");
            }
        });

        buttonAgregar.addClickListener((event) -> { //Evento del boton 'Agregar'    
            mostrarPantallaAgregarNota();
        });

        buttonModificar.addClickListener((event) -> { //Evento del boton 'Modificar'    
            if (null != alumnosSelecionados) {
                if (alumnosSelecionados.size() > 0) {
                    mostrarPantallaModificarNota();
                }
            } else {
                Notification.show("Seleccione una fila para Modificar");
            }
        });

        horizontalLayout.addComponents(buttonAgregar, buttonModificar, buttonEliminar); // anadimos al un HorizontalLayout
        addComponent(horizontalLayout); // anadimos al VerticalLayout
    }

    private List<Alumno> getNotas() {
//        Query q = ManagerFactory.getEntityManager().createQuery("select a from Materia a");
//        List<Alumno> alumnos = new ArrayList<Alumno>(q.getResultList());
        return null;
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

        final TextField textFieldCedula = new TextField("Cedula");
        textFieldCedula.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldCedula.setPlaceholder("Cedula");

        final TextField textFieldNombre = new TextField("Nombre");
        textFieldNombre.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldNombre.setPlaceholder("Nombre");

        final TextField textFieldApellido = new TextField("Apellido");
        textFieldApellido.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldApellido.setPlaceholder("Apellido");

        final TextField textFieldEmail = new TextField("Email");
        textFieldEmail.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldEmail.setPlaceholder("coreo@gmail.com");

        final Component componentDateField = new DateField("Fecha Nac.");
        componentDateField.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        formLayout.addComponents(textFieldCedula,
                textFieldNombre, textFieldApellido, textFieldEmail, componentDateField);

        Button buttonGuardar = new Button("Guardar", FontAwesome.SAVE);
        buttonGuardar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button buttonCancelar = new Button("Cancelar", FontAwesome.CLOSE);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(buttonGuardar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);

        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

    private void mostrarPantallaEliminarNota() {
        final Window window = new Window("Eliminar Materia");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldCedula = new TextField("Cedula");
        textFieldCedula.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldCedula.setEnabled(false);

        final TextField textFieldNombre = new TextField("Nombre");
        textFieldNombre.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldNombre.setEnabled(false);

        final TextField textFieldApellido = new TextField("Apellido");
        textFieldApellido.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldApellido.setEnabled(false);

        final TextField textFieldEmail = new TextField("Email");
        textFieldEmail.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        textFieldEmail.setEnabled(false);

        final Component componentDateField = new DateField("Fecha Nac.");
        componentDateField.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        componentDateField.setEnabled(false);

        formLayout.addComponents(textFieldCedula,
                textFieldNombre, textFieldApellido, textFieldEmail, componentDateField);

        Button EliminarGuardar = new Button("Eliminar", VaadinIcons.TRASH);
        EliminarGuardar.addStyleName(ValoTheme.BUTTON_DANGER);
        Button buttonCancelar = new Button("Cancelar", VaadinIcons.EXIT);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(EliminarGuardar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);

        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

    private void mostrarPantallaModificarNota() {
        final Window window = new Window("Modificar Materia");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldCedula = new TextField("Cedula");
        textFieldCedula.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        final TextField textFieldNombre = new TextField("Nombre");
        textFieldNombre.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        final TextField textFieldApellido = new TextField("Apellido");
        textFieldApellido.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        final TextField textFieldEmail = new TextField("Email");
        textFieldEmail.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        final Component componentDateField = new DateField("Fecha Nac.");
        componentDateField.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        formLayout.addComponents(textFieldCedula,
                textFieldNombre, textFieldApellido, textFieldEmail, componentDateField);

        Button buttonGuardar = new Button("Guardar", FontAwesome.SAVE);
        buttonGuardar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button buttonCancelar = new Button("Cancelar", FontAwesome.CLOSE);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(buttonGuardar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);

        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

}
