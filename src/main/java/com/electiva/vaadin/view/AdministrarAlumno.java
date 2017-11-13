package com.electiva.vaadin.view;

import com.electiva.vaadin.entity.Alumno;
import com.electiva.vaadin.entity.ManagerFactory;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Query;
import com.vaadin.navigator.View;
import java.time.ZoneId;
import java.util.Date;

public class AdministrarAlumno extends VerticalLayout implements View {

    public final static String VIEW_NAME = "alumno";

    private TextField textFieldBuscar;
    private Grid<Alumno> tabla;
    private Button buttonAgregar;
    private Button buttonEliminar;
    private Button buttonModificar;
    private Alumno alumnoSelecionado;

    public AdministrarAlumno() { // Constructor de la clase        
        addTitulo();
        addBuscar();
        addTabla();
        addBotonesCrud();
    }

    private void addTitulo() {
        Label labelCabecera = new Label("Administrar Alumnos");
        labelCabecera.setStyleName(ValoTheme.LABEL_H1);
        addComponent(labelCabecera);
        setComponentAlignment(labelCabecera, Alignment.TOP_CENTER);
    }

    private void addBuscar() {
        this.textFieldBuscar = new TextField();
        textFieldBuscar.setPlaceholder("Buscar por Nombre, Apellido o Cedula");
        textFieldBuscar.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textFieldBuscar.setIcon(VaadinIcons.SEARCH);
        textFieldBuscar.setWidth(400, Unit.PIXELS);

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
        tabla.setWidth(100, Unit.PERCENTAGE); // el ancho de la tabla
        tabla.setHeight(300, Unit.PIXELS); // la altura de la tabla
        tabla.setItems(getAlumnos());
        tabla.setSelectionMode(SelectionMode.SINGLE);
        tabla.addSelectionListener(event -> { // Evento que 'escucha' a las filas selecionadas
            if (event.isUserOriginated()) {
                alumnoSelecionado = event.getFirstSelectedItem().get();
            }
        });
        addComponent(tabla); // anadimos al VerticalLayout
    }

    private void addBotonesCrud() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button buttonAgregar = new Button("Agregar", VaadinIcons.PLUS);
        Button buttonEliminar = new Button("Eliminar", VaadinIcons.TRASH);
        buttonEliminar.addStyleName(ValoTheme.BUTTON_DANGER);
        Button buttonModificar = new Button("Modificar", VaadinIcons.EDIT);

        buttonEliminar.addClickListener(e -> {
            if (null != alumnoSelecionado) { //Evento del boton 'Eliminar'
                mostrarPantallaEliminarAlumno(alumnoSelecionado);
            } else {
                Notification.show("Seleccione una fila para Eliminar!");
            }
        });

        buttonAgregar.addClickListener(e -> mostrarPantallaAgregarAlumno()); //Evento del boton 'Agregar' 

        buttonModificar.addClickListener(e -> { //Evento del boton 'Modificar'    
            if (null != alumnoSelecionado) {
                mostrarPantallaModificarAlumno(alumnoSelecionado);
            } else {
                Notification.show("Seleccione una fila para Modificar");
            }
        });

        horizontalLayout.addComponents(buttonAgregar, buttonModificar, buttonEliminar); // anadimos al un HorizontalLayout
        addComponent(horizontalLayout); // anadimos al VerticalLayout
    }

    private List<Alumno> getAlumnos() {
        Query q = ManagerFactory.getEntityManager().createQuery("select a from Alumno a");
        List<Alumno> alumnos = new ArrayList<Alumno>(q.getResultList());
        return alumnos;
    }

    private void mostrarPantallaAgregarAlumno() {
        final Window window = new Window("Agregar Alumno");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldCedula = new TextField("Cedula");
        textFieldCedula.setWidth(100.0f, Unit.PERCENTAGE);
        textFieldCedula.setPlaceholder("Cedula");

        final TextField textFieldNombre = new TextField("Nombre");
        textFieldNombre.setWidth(100.0f, Unit.PERCENTAGE);
        textFieldNombre.setPlaceholder("Nombre");

        final TextField textFieldApellido = new TextField("Apellido");
        textFieldApellido.setWidth(100.0f, Unit.PERCENTAGE);
        textFieldApellido.setPlaceholder("Apellido");

        final TextField textFieldEmail = new TextField("Email");
        textFieldEmail.setWidth(100.0f, Unit.PERCENTAGE);
        textFieldEmail.setPlaceholder("coreo@gmail.com");

        final DateField dateField = new DateField("Fecha Nac.");
        dateField.setWidth(100.0f, Unit.PERCENTAGE);

        formLayout.addComponents(textFieldCedula,
                textFieldNombre, textFieldApellido, textFieldEmail, dateField);

        Button buttonGuardar = new Button("Guardar", FontAwesome.SAVE);
        buttonGuardar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button buttonCancelar = new Button("Cancelar", FontAwesome.CLOSE);
        buttonCancelar.addClickListener(e -> window.close());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(buttonGuardar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);

        buttonGuardar.addClickListener((event) -> {
            Integer cedula = new Integer(textFieldCedula.getValue());
            String nombre = textFieldNombre.getValue();
            String apellido = textFieldApellido.getValue();
            String email = textFieldEmail.getValue();
            Date fechaNac = Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            Alumno alumno = new Alumno();
            alumno.setNombre(nombre);
            alumno.setApellido(apellido);
            alumno.setCedula(cedula);
            alumno.setEmail(email);
            alumno.setFechaNacimiento(fechaNac);

            System.out.println("" + alumno);

            try {
                ManagerFactory.getEntityManager().getTransaction().begin();
                ManagerFactory.getEntityManager().persist(alumno);
                ManagerFactory.getEntityManager().getTransaction().commit();
                Notification.show("Se ha guardado correctamente");
                textFieldNombre.clear();
                textFieldApellido.clear();
                textFieldCedula.clear();
                textFieldEmail.clear();
                dateField.clear();
                tabla.setItems(getAlumnos());
            } catch (Exception e) {
                Notification.show("Ha ocuarrido un error", Notification.Type.ERROR_MESSAGE);
            }

        });

        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

    private void mostrarPantallaEliminarAlumno(Alumno alumno) {
        final Window window = new Window("Eliminar Alumno");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldCedula = new TextField("Cedula", alumno.getCedula().toString());
        textFieldCedula.setWidth(100.0f, Unit.PERCENTAGE);
        textFieldCedula.setEnabled(false);

        final TextField textFieldNombre = new TextField("Nombre", alumno.getNombre());
        textFieldNombre.setWidth(100.0f, Unit.PERCENTAGE);
        textFieldNombre.setEnabled(false);

        final TextField textFieldApellido = new TextField("Apellido", alumno.getApellido());
        textFieldApellido.setWidth(100.0f, Unit.PERCENTAGE);
        textFieldApellido.setEnabled(false);

        final TextField textFieldEmail = new TextField("Email", alumno.getEmail());
        textFieldEmail.setWidth(100.0f, Unit.PERCENTAGE);
        textFieldEmail.setEnabled(false);

        final DateField dateField = new DateField("Fecha Nac.", alumno.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dateField.setWidth(100.0f, Unit.PERCENTAGE);
        dateField.setEnabled(false);

        formLayout.addComponents(textFieldCedula,
                textFieldNombre, textFieldApellido, textFieldEmail, dateField);

        Button buttonEliminar = new Button("Eliminar", VaadinIcons.TRASH);
        buttonEliminar.addStyleName(ValoTheme.BUTTON_DANGER);
        buttonEliminar.addClickListener(e -> {
            try {
                ManagerFactory.getEntityManager().getTransaction().begin();
                ManagerFactory.getEntityManager().remove(alumno);
                ManagerFactory.getEntityManager().getTransaction().commit();
                Notification.show("Se ha eliminaro correctamente el registro", Notification.Type.TRAY_NOTIFICATION);
                tabla.setItems(getAlumnos());
                textFieldNombre.clear();
                textFieldApellido.clear();
                textFieldCedula.clear();
                textFieldEmail.clear();
                dateField.clear();
                alumnoSelecionado = null;
                window.close();
            } catch (Exception ex) {
                Notification.show("Ha ocurrido un error", Notification.Type.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        Button buttonCancelar = new Button("Cancelar", VaadinIcons.EXIT);
        buttonCancelar.addClickListener(e -> window.close());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(buttonEliminar, buttonCancelar);
        formLayout.addComponent(horizontalLayout);

        window.setContent(formLayout);
        getUI().getUI().addWindow(window);
    }

    private void mostrarPantallaModificarAlumno(Alumno alumno) {
        final Window window = new Window("Modificar Alumno");
        window.setWidth("450px");
        window.setHeight("400px");
        window.center();
        window.setResizable(false);
        window.setModal(true);
        window.setDraggable(false);

        final FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);

        final TextField textFieldCedula = new TextField("Cedula", alumno.getCedula().toString());
        textFieldCedula.setWidth(100.0f, Unit.PERCENTAGE);

        final TextField textFieldNombre = new TextField("Nombre", alumno.getNombre());
        textFieldNombre.setWidth(100.0f, Unit.PERCENTAGE);

        final TextField textFieldApellido = new TextField("Apellido", alumno.getApellido());
        textFieldApellido.setWidth(100.0f, Unit.PERCENTAGE);

        final TextField textFieldEmail = new TextField("Email", alumno.getEmail());
        textFieldEmail.setWidth(100.0f, Unit.PERCENTAGE);

        final DateField dateField = new DateField("Fecha Nac.", alumno.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dateField.setWidth(100.0f, Unit.PERCENTAGE);

        formLayout.addComponents(textFieldCedula,
                textFieldNombre, textFieldApellido, textFieldEmail, dateField);

        Button buttonGuardar = new Button("Guardar", FontAwesome.SAVE);
        buttonGuardar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button buttonCancelar = new Button("Cancelar", FontAwesome.CLOSE);
        buttonCancelar.addClickListener(e -> window.close());

        buttonGuardar.addClickListener(e -> {

            try {
                ManagerFactory.getEntityManager().getTransaction().begin();

                Integer cedula = new Integer(textFieldCedula.getValue());
                String nombre = textFieldNombre.getValue();
                String apellido = textFieldApellido.getValue();
                String email = textFieldEmail.getValue();
                Date fechaNac = Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

                alumno.setNombre(nombre);
                alumno.setApellido(apellido);
                alumno.setCedula(cedula);
                alumno.setEmail(email);
                alumno.setFechaNacimiento(fechaNac);

                System.out.println("" + alumno);

                ManagerFactory.getEntityManager().getTransaction().commit();
                Notification.show("Se ha Modificado correctamente el registro", Notification.Type.TRAY_NOTIFICATION);
                tabla.setItems(getAlumnos());
                textFieldNombre.clear();
                textFieldApellido.clear();
                textFieldCedula.clear();
                textFieldEmail.clear();
                dateField.clear();
                alumnoSelecionado = null;
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
