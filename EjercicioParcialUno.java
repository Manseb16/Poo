/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejercicioparcialuno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Libro {
    String titulo;
    String isbn;
    Autor autor;
    Categoria categoria;

    public Libro(String titulo, String isbn, Autor autor, Categoria categoria) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.categoria = categoria;
    }

    public String mostrarInfo() {
        return "Título: " + titulo + "\nISBN: " + isbn + "\nAutor: " + autor.nombre + " " + autor.apellido + "\nCategoría: " + categoria.nombre;
    }
}

class Autor {
    String nombre;
    String apellido;

    public Autor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String mostrarInfo() {
        return "Nombre: " + nombre + " " + apellido;
    }
}

class Usuario {
    String nombre;
    String apellido;
    ArrayList<Prestamo> prestamos;

    public Usuario(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.prestamos = new ArrayList<>();
    }
}

class Prestamo {
    Libro libro;
    Usuario usuario;

    public Prestamo(Libro libro, Usuario usuario) {
        this.libro = libro;
        this.usuario = usuario;
    }
}

class Categoria {
    String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}

class Biblioteca {
    ArrayList<Libro> libros;
    ArrayList<Autor> autores;
    ArrayList<Usuario> usuarios;
    ArrayList<Prestamo> prestamos;
    ArrayList<Categoria> categorias;
    JTextArea infoTexto;

    public Biblioteca(JTextArea infoTexto) {
        this.libros = new ArrayList<>();
        this.autores = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.infoTexto = infoTexto;
    }

    public void agregarLibro(String titulo, String isbn, Autor autor, Categoria categoria) {
        Autor autorExistente = autores.stream()
                .filter(a -> a.nombre.equals(autor.nombre) && a.apellido.equals(autor.apellido))
                .findFirst().orElse(null);
        if (autorExistente == null) {
            autores.add(autor);
            autorExistente = autor;
        }

        Categoria categoriaExistente = categorias.stream()
                .filter(c -> c.nombre.equals(categoria.nombre))
                .findFirst().orElse(null);
        if (categoriaExistente == null) {
            categorias.add(categoria);
            categoriaExistente = categoria;
        }

        Libro libro = new Libro(titulo, isbn, autorExistente, categoriaExistente);
        libros.add(libro);
    }

    public void agregarUsuario(String nombre, String apellido) {
        Usuario usuario = new Usuario(nombre, apellido);
        usuarios.add(usuario);
    }

    public void prestarLibro(Libro libro, Usuario usuario) {
        Prestamo prestamo = new Prestamo(libro, usuario);
        prestamos.add(prestamo);
        usuario.prestamos.add(prestamo);
    }

    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        prestamos.removeAll(usuario.prestamos);
        usuario.prestamos.clear();
    }

    public void mostrarInformacion() {
        infoTexto.setText("");
        infoTexto.append("Libros:\n");
        for (Libro libro : libros) {
            infoTexto.append(libro.mostrarInfo() + "\n\n");
        }
        infoTexto.append("Usuarios:\n");
        for (Usuario usuario : usuarios) {
            infoTexto.append(usuario.nombre + " " + usuario.apellido + "\n");
            infoTexto.append("Préstamos:\n");
            for (Prestamo prestamo : usuario.prestamos) {
                infoTexto.append(prestamo.libro.titulo + "\n");
            }
            infoTexto.append("\n");
        }
    }
}

public class EjercicioParcialUno extends JFrame {
    private Biblioteca biblioteca;
    private JTextField tituloEntry, isbnEntry, autorNombreEntry, autorApellidoEntry, categoriaEntry;
    private JTextField usuarioNombreEntry, usuarioApellidoEntry;

    public EjercicioParcialUno() {
        super("Sistema de Gestión de Bibliotecas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane notebook = new JTabbedPane();
        
        JPanel libroTab = new JPanel();
        notebook.addTab("Libros", libroTab);
        
        JPanel usuarioTab = new JPanel();
        notebook.addTab("Usuarios", usuarioTab);
        
        JPanel infoTab = new JPanel(new BorderLayout());
        notebook.addTab("Información", infoTab);

        JTextArea infoTexto = new JTextArea(20, 50);
        infoTab.add(new JScrollPane(infoTexto), BorderLayout.CENTER);

        biblioteca = new Biblioteca(infoTexto);

        configurarLibroTab(libroTab);
        configurarUsuarioTab(usuarioTab);
        configurarInfoTab(infoTab);

        add(notebook);
    }

    private void configurarLibroTab(JPanel libroTab) {
        libroTab.setLayout(new BoxLayout(libroTab, BoxLayout.Y_AXIS));
        
        JPanel libroFrame = new JPanel(new GridLayout(6, 2, 5, 5));
        libroFrame.setBorder(BorderFactory.createTitledBorder("Agregar Libro"));
        
        libroFrame.add(new JLabel("Título:"));
        tituloEntry = new JTextField();
        libroFrame.add(tituloEntry);
        
        libroFrame.add(new JLabel("ISBN:"));
        isbnEntry = new JTextField();
        libroFrame.add(isbnEntry);
        
        libroFrame.add(new JLabel("Nombre del Autor:"));
        autorNombreEntry = new JTextField();
        libroFrame.add(autorNombreEntry);
        
        libroFrame.add(new JLabel("Apellido del Autor:"));
        autorApellidoEntry = new JTextField();
        libroFrame.add(autorApellidoEntry);
        
        libroFrame.add(new JLabel("Categoría:"));
        categoriaEntry = new JTextField();
        libroFrame.add(categoriaEntry);
        
        JButton agregarLibroButton = new JButton("Agregar Libro");
        agregarLibroButton.addActionListener(e -> agregarLibro());
        libroFrame.add(agregarLibroButton);
        
        libroTab.add(libroFrame);
    }

    private void configurarUsuarioTab(JPanel usuarioTab) {
        usuarioTab.setLayout(new BoxLayout(usuarioTab, BoxLayout.Y_AXIS));
        
        JPanel usuarioFrame = new JPanel(new GridLayout(3, 2, 5, 5));
        usuarioFrame.setBorder(BorderFactory.createTitledBorder("Agregar Usuario"));
        
        usuarioFrame.add(new JLabel("Nombre:"));
        usuarioNombreEntry = new JTextField();
        usuarioFrame.add(usuarioNombreEntry);
        
        usuarioFrame.add(new JLabel("Apellido:"));
        usuarioApellidoEntry = new JTextField();
        usuarioFrame.add(usuarioApellidoEntry);
        
        JButton agregarUsuarioButton = new JButton("Agregar Usuario");
        agregarUsuarioButton.addActionListener(e -> agregarUsuario());
        usuarioFrame.add(agregarUsuarioButton);
        
        usuarioTab.add(usuarioFrame);
    }

    private void configurarInfoTab(JPanel infoTab) {
        JPanel eliminarFrame = new JPanel();
        JButton eliminarLibroButton = new JButton("Eliminar Libro");
        eliminarLibroButton.addActionListener(e -> eliminarLibro());
        eliminarFrame.add(eliminarLibroButton);
        
        JButton eliminarUsuarioButton = new JButton("Eliminar Usuario");
        eliminarUsuarioButton.addActionListener(e -> eliminarUsuario());
        eliminarFrame.add(eliminarUsuarioButton);
        
        infoTab.add(eliminarFrame, BorderLayout.SOUTH);
    }

    private void agregarLibro() {
        String titulo = tituloEntry.getText();
        String isbn = isbnEntry.getText();
        String autorNombre = autorNombreEntry.getText();
        String autorApellido = autorApellidoEntry.getText();
        String categoriaNombre = categoriaEntry.getText();
        
        Autor autor = new Autor(autorNombre, autorApellido);
        Categoria categoria = new Categoria(categoriaNombre);
        biblioteca.agregarLibro(titulo, isbn, autor, categoria);
        biblioteca.mostrarInformacion();
        
        tituloEntry.setText("");
        isbnEntry.setText("");
        autorNombreEntry.setText("");
        autorApellidoEntry.setText("");
        categoriaEntry.setText("");
    }

    private void agregarUsuario() {
        String nombre = usuarioNombreEntry.getText();
        String apellido = usuarioApellidoEntry.getText();
        biblioteca.agregarUsuario(nombre, apellido);
        biblioteca.mostrarInformacion();
        
        usuarioNombreEntry.setText("");
        usuarioApellidoEntry.setText("");
    }

    private void eliminarLibro() {
        JOptionPane.showMessageDialog(this, "Funcionalidad no implementada");
    }

    private void eliminarUsuario() {
        JOptionPane.showMessageDialog(this, "Funcionalidad no implementada");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EjercicioParcialUno().setVisible(true);
        });
    }
}