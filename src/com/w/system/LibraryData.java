package com.w.system;

import com.w.entity.Book;
import com.w.entity.User;
import com.w.entity.BorrowRecords;
import com.w.entity.Reservation;
import com.w.enums.BookType;
import com.w.util.CollectionUtils;

import java.util.*;

public class LibraryData {
    // 1. ISBN -> 多本书(同一ISBN的所有馆藏)
    private Map<String, List<Book>> booksByIsbn = new HashMap<>();
    // 2. 馆藏编号 -> 一本书
    private Map<String, Book> collectionNoToBook = new HashMap<>();
    // 3. 馆藏编号集合
    private Set<String> collectionNos = new HashSet<>();
    // 4. 用户ID -> 一位用户
    private Map<String, User> usersById = new HashMap<>();
    // 5. 存储用户借阅记录
    private List<BorrowRecords> borrowRecords = new ArrayList<>();
    // 6. 存储用户预约记录
    private List<Reservation> reservations = new ArrayList<>();
    // 7. 存储不同类型图书数量
    private Map<BookType, Integer> bookCountsByType = new EnumMap<>(BookType.class);
    // 8. 存储图书评分
    private Map<String, Double> bookRatings = new HashMap<>();


    // -------------------- 图书相关 --------------------
    public boolean addBook(Book book) {
        // 检查馆藏编号是否已存在
        if (collectionNos.contains(book.getCollectionNo())) {
            return false; // 馆藏编号已存在，返回false表示添加失败
        }
        
        collectionNos.add(book.getCollectionNo());
        collectionNoToBook.put(book.getCollectionNo(), book);
        booksByIsbn.computeIfAbsent(book.getIsbn(), k -> new ArrayList<>()).add(book);
        bookCountsByType.put(book.getType(), bookCountsByType.getOrDefault(book.getType(), 0) + 1);
        bookRatings.put(book.getIsbn(), 0.0);
        return true;
    }

    public List<Book> getBooksByIsbn(String isbn) {
        return booksByIsbn.getOrDefault(isbn, Collections.emptyList());
    }

    // 添加无参方法用于获取全部图书
    public Map<String, List<Book>> getBooksByIsbn() {
        return new HashMap<>(booksByIsbn);
    }

    public Book getBookByCollectionNo(String collectionNo) {
        return collectionNoToBook.get(collectionNo);
    }

    public List<Book> searchBooksByTitle(String title) {
        List<Book> allBooks = new ArrayList<>();
        booksByIsbn.values().forEach(allBooks::addAll);
        
        return new ArrayList<>(CollectionUtils.filter(allBooks, 
            book -> book.getTitle().contains(title)));
    }

    public List<Book> searchBooksByAuthor(String keyword) {
        List<Book> allBooks = new ArrayList<>();
        booksByIsbn.values().forEach(allBooks::addAll);
        
        return new ArrayList<>(CollectionUtils.filter(allBooks, 
            book -> book.getAuthor().contains(keyword)));
    }

    // -------------------- 用户相关 --------------------
    public boolean isUserIdExists(String userId) {
        return usersById.containsKey(userId);
    }

    public void addUser(User user) {
        usersById.put(user.getId(), user);
    }

    public User getUserById(String id) {
        return usersById.get(id);
    }

    // -------------------- 借阅记录相关 --------------------
    public void addBorrowRecord(BorrowRecords record) {
        borrowRecords.add(record);
    }

    public List<BorrowRecords> getAllBorrowRecords() {
        return new ArrayList<>(borrowRecords);
    }

    // -------------------- 预约相关 --------------------
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // 直接从预约列表中删除指定预约记录
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public boolean isReserved(String collectionNo, String userId) {
        return !CollectionUtils.filter(reservations, 
            reservation -> reservation.getBook().getCollectionNo().equals(collectionNo) &&
                          reservation.getUser().getId().equals(userId)).isEmpty();
    }

    public Reservation getFirstReservationByBook(Book book) {
        return CollectionUtils.findFirst(reservations, 
            reservation -> reservation.getBook().equals(book));
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    // -------------------- 统计相关 --------------------
    public Map<BookType, Integer> getBookCountByType() {
        return new HashMap<>(bookCountsByType);
    }

    // -------------------- 图书评分相关 --------------------
    public double getBookRating(Book book) {
        return bookRatings.getOrDefault(book.getIsbn(), 0.0);
    }

    public Map<String, Double> getBookRatings() {
        return new HashMap<>(bookRatings);
    }
}
