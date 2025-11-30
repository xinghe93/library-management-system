package com.w.entity;

public class BookItem {
    private String collectionNo;
    private String isbn;
    private boolean available;

    public BookItem(String collectionNo, String isbn) {
        this.collectionNo = collectionNo;
        this.isbn = isbn;
        this.available = true;
    }

    public String getCollectionNo() {
        return collectionNo;
    }
    public String getIsbn() {
        return isbn;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format(
                "馆藏编号: %s  状态: %s",
                collectionNo, available ? "可借阅" : "已借出"
        );
    }
}
