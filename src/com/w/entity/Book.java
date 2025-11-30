package com.w.entity;

import com.w.enums.BookType;

import java.util.Objects;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private BookType type;
    private String publishDate;

    public Book(String isbn, String title, String author, BookType type, String publishDate) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.type = type;
        this.publishDate = publishDate;
    }

    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public BookType getType() {
        return type;
    }
    public String getPublishDate() {
        return publishDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public String toString() {
        return String.format(
                "ISBN: %s\n书名: %s\n作者: %s\n类型: %s\n出版日期: %s",
                isbn, title, author, type, publishDate
        );
    }
}
