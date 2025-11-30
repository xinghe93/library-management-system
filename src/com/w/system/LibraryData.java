package com.w.system;

import com.w.enums.BookType;
import com.w.entity.*;

import java.util.*;

public class LibraryData {
    // ISBN -> 图书信息
    private final Map<String, Book> booksByIsbn = new HashMap<>();
    // 馆藏编号 -> 一本实体书
    private final Map<String, BookItem> bookItemsByCollectionNo = new HashMap<>();
    // 馆藏编号集合(确保不重复)
    private final Set<String> collectionNos = new HashSet<>();
    // 用户ID -> 用户信息
    private final Map<String, User> usersById = new HashMap<>();
    // 用户借阅记录
    private final List<BorrowRecords> borrowRecords = new ArrayList<>();
    // 用户预约记录
    private final List<Reservation> reservations = new ArrayList<>();
    // 图书类型数量统计
    private final Map<BookType, Integer> bookCountsByType = new EnumMap<>(BookType.class);
    // 图书评分
    private final Map<String, Double> bookRatings = new HashMap<>();

    public Map<String, Book> getBooksByIsbn() {
        return booksByIsbn;
    }
    public Map<String, BookItem> getBookItemsByCollectionNo() {
        return bookItemsByCollectionNo;
    }
    public Set<String> getCollectionNos() {
        return collectionNos;
    }
    public Map<String, User> getUsersById() {
        return usersById;
    }
    public List<BorrowRecords> getBorrowRecords() {
        return borrowRecords;
    }
    public List<Reservation> getReservations() {
        return reservations;
    }
    public Map<BookType, Integer> getBookCountsByType() {
        return bookCountsByType;
    }
    public Map<String, Double> getBookRatings() {
        return bookRatings;
    }
}
