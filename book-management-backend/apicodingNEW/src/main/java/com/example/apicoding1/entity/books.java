package com.example.apicoding1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class books {
	@Id
    private Integer isbn;
	private String title;
	private String author;
	private int publicationYear;
	public books() {
		super();
		// TODO Auto-generated constructor stub
	}
	public books(Integer isbn, String title, String author, int publicationYear) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
	}
	public Integer getIsbn() {
		return isbn;
	}
	public void setIsbn(Integer isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}
	@Override
	public String toString() {
		return "book [isbn=" + isbn + ", title=" + title + ", author=" + author + ", publicationYear=" + publicationYear
				+ "]";
	}
	
	
	

}
