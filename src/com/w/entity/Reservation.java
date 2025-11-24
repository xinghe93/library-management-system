package com.w.entity;

public class Reservation {
    private User user;
    private Book book;

    public Reservation(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public User getUser() {
        return user;
    }
    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return String.format(
                "用户: %s\n图书: %s",
                user, book
        );
    }
}
