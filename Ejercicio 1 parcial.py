import tkinter as tk
from tkinter import ttk

class Libro:
    def __init__(self, titulo, isbn, autor, categoria):
        self.titulo = titulo
        self.isbn = isbn
        self.autor = autor
        self.categoria = categoria

    def mostrar_info(self):
        info = f"Título: {self.titulo}\nISBN: {self.isbn}\nAutor: {self.autor.nombre} {self.autor.apellido}\nCategoría: {self.categoria.nombre}"
        return info

class Autor:
    def __init__(self, nombre, apellido):
        self.nombre = nombre
        self.apellido = apellido

    def mostrar_info(self):
        return f"Nombre: {self.nombre} {self.apellido}"

class Usuario:
    def __init__(self, nombre, apellido):
        self.nombre = nombre
        self.apellido = apellido
        self.prestamos = []

class Prestamo:
    def __init__(self, libro, usuario):
        self.libro = libro
        self.usuario = usuario

class Categoria:
    def __init__(self, nombre):
        self.nombre = nombre

class Biblioteca:
    def __init__(self, info_tab):
        self.libros = []
        self.autores = []
        self.usuarios = []
        self.prestamos = []
        self.categorias = []
        self.info_texto = tk.Text(info_tab, height=20, width=50)
        self.info_texto.pack(fill="both", expand=True, padx=10, pady=10)

    def agregar_libro(self, titulo, isbn, autor, categoria):
        autor_existente = next((a for a in self.autores if a.nombre == autor.nombre and a.apellido == autor.apellido), None)
        if not autor_existente:
            self.autores.append(autor)
        categoria_existente = next((c for c in self.categorias if c.nombre == categoria.nombre), None)
        if not categoria_existente:
            self.categorias.append(categoria)
        libro = Libro(titulo, isbn, autor_existente or autor, categoria_existente or categoria)
        self.libros.append(libro)

    def agregar_usuario(self, nombre, apellido):
        usuario = Usuario(nombre, apellido)
        self.usuarios.append(usuario)

    def prestar_libro(self, libro, usuario):
        prestamo = Prestamo(libro, usuario)
        self.prestamos.append(prestamo)
        usuario.prestamos.append(prestamo)

    def eliminar_libro(self, libro):
        self.libros.remove(libro)

    def eliminar_usuario(self, usuario):
        self.usuarios.remove(usuario)
        for prestamo in usuario.prestamos:
            self.prestamos.remove(prestamo)
        usuario.prestamos.clear()

    def mostrar_informacion(self):
        self.info_texto.delete('1.0', tk.END)
        self.info_texto.insert(tk.END, "Libros:\n")
        for libro in self.libros:
            self.info_texto.insert(tk.END, libro.mostrar_info() + "\n\n")
        self.info_texto.insert(tk.END, "Usuarios:\n")
        for usuario in self.usuarios:
            self.info_texto.insert(tk.END, usuario.nombre + " " + usuario.apellido + "\n")
            self.info_texto.insert(tk.END, "Préstamos:\n")
            for prestamo in usuario.prestamos:
                self.info_texto.insert(tk.END, prestamo.libro.titulo + "\n")
            self.info_texto.insert(tk.END, "\n")

root = tk.Tk()
root.title("Sistema de Gestión de Bibliotecas")
root.geometry("800x600")

notebook = ttk.Notebook(root)
notebook.pack(fill="both", expand=True)

libro_tab = ttk.Frame(notebook)
notebook.add(libro_tab, text="Libros")

libro_frame = tk.LabelFrame(libro_tab, text="Agregar Libro", padx=10, pady=10)
libro_frame.pack(fill="x", padx=10, pady=10)

titulo_label = tk.Label(libro_frame, text="Título:")
titulo_label.grid(row=0, column=0, sticky="w", padx=5, pady=5)
titulo_entry = tk.Entry(libro_frame)
titulo_entry.grid(row=0, column=1, sticky="w", padx=5, pady=5)

isbn_label = tk.Label(libro_frame, text="ISBN:")
isbn_label.grid(row=1, column=0, sticky="w", padx=5, pady=5)
isbn_entry = tk.Entry(libro_frame)
isbn_entry.grid(row=1, column=1, sticky="w", padx=5, pady=5)

autor_nombre_label = tk.Label(libro_frame, text="Nombre del Autor:")
autor_nombre_label.grid(row=2, column=0, sticky="w", padx=5, pady=5)
autor_nombre_entry = tk.Entry(libro_frame)
autor_nombre_entry.grid(row=2, column=1, sticky="w", padx=5, pady=5)

autor_apellido_label = tk.Label(libro_frame, text="Apellido del Autor:")
autor_apellido_label.grid(row=3, column=0, sticky="w", padx=5, pady=5)
autor_apellido_entry = tk.Entry(libro_frame)
autor_apellido_entry.grid(row=3, column=1, sticky="w", padx=5, pady=5)

categoria_label = tk.Label(libro_frame, text="Categoría:")
categoria_label.grid(row=4, column=0, sticky="w", padx=5, pady=5)
categoria_entry = tk.Entry(libro_frame)
categoria_entry.grid(row=4, column=1, sticky="w", padx=5, pady=5)

def agregar_libro():
    titulo = titulo_entry.get()
    isbn = isbn_entry.get()
    autor_nombre = autor_nombre_entry.get()
    autor_apellido = autor_apellido_entry.get()
    categoria_nombre = categoria_entry.get()
    autor = Autor(autor_nombre, autor_apellido)
    categoria = Categoria(categoria_nombre)
    biblioteca.agregar_libro(titulo, isbn, autor, categoria)
    mostrar_informacion()
    titulo_entry.delete(0, tk.END)
    isbn_entry.delete(0, tk.END)
    autor_nombre_entry.delete(0, tk.END)
    autor_apellido_entry.delete(0, tk.END)
    categoria_entry.delete(0, tk.END)

agregar_libro_button = tk.Button(libro_frame, text="Agregar Libro", command=agregar_libro)
agregar_libro_button.grid(row=5, column=0, columnspan=2, padx=5, pady=5)

usuario_tab = ttk.Frame(notebook)
notebook.add(usuario_tab, text="Usuarios")

usuario_frame = tk.LabelFrame(usuario_tab, text="Agregar Usuario", padx=10, pady=10)
usuario_frame.pack(fill="x", padx=10, pady=10)

usuario_nombre_label = tk.Label(usuario_frame, text="Nombre:")
usuario_nombre_label.grid(row=0, column=0, sticky="w", padx=5, pady=5)
usuario_nombre_entry = tk.Entry(usuario_frame)
usuario_nombre_entry.grid(row=0, column=1, sticky="w", padx=5, pady=5)

usuario_apellido_label = tk.Label(usuario_frame, text="Apellido:")
usuario_apellido_label.grid(row=1, column=0, sticky="w", padx=5, pady=5)
usuario_apellido_entry = tk.Entry(usuario_frame)
usuario_apellido_entry.grid(row=1, column=1, sticky="w", padx=5, pady=5)

def agregar_usuario():
    nombre = usuario_nombre_entry.get()
    apellido = usuario_apellido_entry.get()
    biblioteca.agregar_usuario(nombre, apellido)
    mostrar_informacion()
    usuario_nombre_entry.delete(0, tk.END)
    usuario_apellido_entry.delete(0, tk.END)

agregar_usuario_button = tk.Button(usuario_frame, text="Agregar Usuario", command=agregar_usuario)
agregar_usuario_button.grid(row=2, column=0, columnspan=2, padx=5, pady=5)

info_tab = ttk.Frame(notebook)
notebook.add(info_tab, text="Información")

biblioteca = Biblioteca(info_tab)

def mostrar_informacion():
    biblioteca.mostrar_informacion()

def eliminar_libro():
    seleccion = biblioteca.info_texto.get(tk.SEL_FIRST, tk.SEL_LAST)
    for libro in biblioteca.libros:
        if libro.titulo in seleccion:
            biblioteca.eliminar_libro(libro)
            break
    mostrar_informacion()

def eliminar_usuario():
    seleccion = biblioteca.info_texto.get(tk.SEL_FIRST, tk.SEL_LAST)
    for usuario in biblioteca.usuarios:
        if usuario.nombre in seleccion or usuario.apellido in seleccion:
            biblioteca.eliminar_usuario(usuario)
            break
    mostrar_informacion()

eliminar_frame = tk.Frame(info_tab)
eliminar_frame.pack(fill="x", padx=10, pady=10)
eliminar_libro_button = tk.Button(eliminar_frame, text="Eliminar Libro", command=eliminar_libro)
eliminar_libro_button.pack(side="left", padx=5)
eliminar_usuario_button = tk.Button(eliminar_frame, text="Eliminar Usuario", command=eliminar_usuario)
eliminar_usuario_button.pack(side="left", padx=5)

root.mainloop()