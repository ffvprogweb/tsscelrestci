package com.fatec.scelrest.model;


import javax.persistence.*;
import javax.validation.constraints.*;
@Entity
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Size(min = 4, max = 4, message = "ISBN deve ter 4 caracteres")
	@Column(unique=true) //nao funciona com @Valid tem que tratar na camada de persistencia
	private String isbn;
	@NotNull
	@Size(min = 1, max = 50, message = "Titulo deve ter entre 1 e 50 caracteres")
	private String titulo;
	@NotNull
	@Size(min = 1, max = 50, message = "Autor deve ter entre 1 e 50 caracteres")
	private String autor;
	public Livro() {
	}
	public Livro(String i, String t, String a) {
		this.isbn = i;
		this.titulo = t;
		this.autor = a;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}
}
