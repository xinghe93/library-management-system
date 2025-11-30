package com.w.system;

import com.w.enums.BookType;
import com.w.entity.*;
import com.w.util.CollectionUtils;

import java.util.*;

public class LibraryService {
    private final LibraryData libraryData;
    public LibraryService(LibraryData libraryData) {
        this.libraryData = libraryData;
    }
    // 检查图书是否已存在（根据ISBN）
    public boolean isBookExists(String isbn) {
        return libraryData.getBooksByIsbn().containsKey(isbn);
    }
    // 检查用户是否已存在（根据用户ID）
    public boolean isUserExists(String userId) {
        return libraryData.getUsersById().containsKey(userId);
    }
    // 检查实体书是否已存在（根据馆藏编号）
    public boolean isBookItemExists(String collectionNo) {
        return libraryData.getCollectionNos().contains(collectionNo);
    }
    // 添加图书信息
    public boolean addBook(Book book) {
        // 检查图书是否已存在
        if (isBookExists(book.getIsbn())) {
            return false;
        }
        libraryData.getBooksByIsbn().put(book.getIsbn(), book);
        // 更新图书类型统计
        BookType type = book.getType();
        libraryData.getBookCountsByType().put(type, 
            libraryData.getBookCountsByType().getOrDefault(type, 0) + 1);
        return true;
    }
    // 直接添加实体书（不添加图书信息）
    public boolean addBookItem(BookItem bookItem) {
        // 检查馆藏编号是否已存在
        if (isBookItemExists(bookItem.getCollectionNo())) {
            return false;
        }
        // 检查对应的图书信息是否存在
        if (!isBookExists(bookItem.getIsbn())) {
            return false;
        }
        // 添加实体书
        libraryData.getBookItemsByCollectionNo().put(bookItem.getCollectionNo(), bookItem);
        libraryData.getCollectionNos().add(bookItem.getCollectionNo());
        return true;
    }
    // 添加实体书（同时添加图书信息）
    public void addBookItem(String collectionNo, Book book) {
        // 先添加图书信息
        addBook(book);
        // 再添加实体书
        BookItem bookItem = new BookItem(collectionNo, book.getIsbn());
        libraryData.getBookItemsByCollectionNo().put(collectionNo, bookItem);
        libraryData.getCollectionNos().add(collectionNo);
    }
    // 添加用户
    public boolean addUser(User user) {
        // 检查用户是否已存在
        if (isUserExists(user.getId())) {
            return false;
        }
        libraryData.getUsersById().put(user.getId(), user);
        return true;
    }
    // 根据ISBN查找图书
    public Book findBookByIsbn(String isbn) {
        return libraryData.getBooksByIsbn().get(isbn);
    }
    // 根据书名查找图书
    public List<Book> findBooksByTitle(String title) {
        return (List<Book>) CollectionUtils.filter(
            libraryData.getBooksByIsbn().values(),
            book -> book.getTitle().contains(title)
        );
    }
    
    // 根据作者查找图书
    public List<Book> findBooksByAuthor(String author) {
        return (List<Book>) CollectionUtils.filter(
            libraryData.getBooksByIsbn().values(),
            book -> book.getAuthor().contains(author)
        );
    }
    // 获取指定ISBN图书的所有实体书及其状态
    public List<BookItem> getBookItemsByIsbn(String isbn) {
        return (List<BookItem>) CollectionUtils.filter(
            libraryData.getBookItemsByCollectionNo().values(),
            item -> item.getIsbn().equals(isbn)
        );
    }
    // 借阅图书
    public boolean borrowBook(String collectionNo, String userId) {
        // 检查用户是否存在
        if (!isUserExists(userId)) {
            return false;
        }
        BookItem bookItem = libraryData.getBookItemsByCollectionNo().get(collectionNo);
        // 棜查图书是否存在
        if (bookItem == null) {
            return false;
        }
        // 检查图书是否可借阅
        if (!bookItem.isAvailable()) {
            return false;
        }
        // 检查用户是否已经借阅了这本书
        BorrowRecords borrowRecord = CollectionUtils.findFirst(
            libraryData.getBorrowRecords(),
            record -> record.getCollectionNo().equals(collectionNo) 
                && record.getBorrowerId().equals(userId)
        );
        
        if (borrowRecord != null) {
            // 用户已经借阅了这本书
            return false;
        }
        // 设置为已借出
        bookItem.setAvailable(false);
        // 添加借阅记录
        libraryData.getBorrowRecords().add(new BorrowRecords(collectionNo, userId));
        return true;
    }
    // 归还图书
    public String returnBook(String collectionNo) {
        BookItem bookItem = libraryData.getBookItemsByCollectionNo().get(collectionNo);
        // 检查图书是否存在
        if (bookItem == null) {
            return ""; // 归还失败
        }
        // 查找当前借阅者
        BorrowRecords borrowRecord = CollectionUtils.findFirst(
            libraryData.getBorrowRecords(),
            record -> record.getCollectionNo().equals(collectionNo)
        );
        if (borrowRecord == null) {
            // 图书未被借阅
            return ""; // 归还失败
        }
        // 移除借阅记录
        libraryData.getBorrowRecords().removeIf(
            record -> record.getCollectionNo().equals(collectionNo)
        );
        // 检查是否有预约，如果有则自动借阅给预约者，按照预约顺序找到第一个预约者
        List<Reservation> reservationsForBook = new ArrayList<>();
        for (Reservation r : libraryData.getReservations()) {
            if (r.getCollectionNo().equals(collectionNo)) {
                reservationsForBook.add(r);
            }
        }
        String nextReservedUser = null;
        if (!reservationsForBook.isEmpty()) {
            Reservation firstReservation = reservationsForBook.getFirst();
            nextReservedUser = firstReservation.getUserId();
            // 自动借阅给第一个预约者
            borrowBook(collectionNo, firstReservation.getUserId());
            // 移除第一个预约记录
            libraryData.getReservations().remove(firstReservation);
        } else {
            // 没有预约用户，图书变为可借阅状态
            bookItem.setAvailable(true);
        }
        return nextReservedUser;
    }
    // 预约图书
    public boolean reserveBook(String collectionNo, String userId) {
        // 检查用户是否存在
        if (!isUserExists(userId)) {
            return false;
        }
        BookItem bookItem = libraryData.getBookItemsByCollectionNo().get(collectionNo);
        // 检查图书是否存在
        if (bookItem == null) {
            return false;
        }
        // 检查图书是否可借阅
        if (bookItem.isAvailable()) {
            return false; // 图书可借阅，不能预约
        }
        // 检查用户是否已经借阅了这本书
        boolean isAlreadyBorrowed = false;
        for (BorrowRecords record : libraryData.getBorrowRecords()) {
            if (record.getCollectionNo().equals(collectionNo) 
                && record.getBorrowerId().equals(userId)) {
                isAlreadyBorrowed = true;
                break;
            }
        }
        if (isAlreadyBorrowed) {
            return false; // 用户已经借阅了这本书，不能预约
        }
        // 检查用户是否已经预约了这本书
        boolean isAlreadyReserved = false;
        for (Reservation reservation : libraryData.getReservations()) {
            if (reservation.getCollectionNo().equals(collectionNo) 
                && reservation.getUserId().equals(userId)) {
                isAlreadyReserved = true;
                break;
            }
        }
        if (isAlreadyReserved) {
            return false; // 用户已经预约了这本书
        }
        // 添加预约记录
        libraryData.getReservations().add(new Reservation(collectionNo, userId));
        return true;
    }
    // 检查用户是否已经借阅了指定图书
    public boolean isBookBorrowedByUser(String collectionNo, String userId) {
        return CollectionUtils.findFirst(
            libraryData.getBorrowRecords(),
            record -> record.getCollectionNo().equals(collectionNo) 
                && record.getBorrowerId().equals(userId)
        ) != null;
    }
    // 检查用户是否已经预约了指定图书
    public boolean isBookReservedByUser(String collectionNo, String userId) {
        return CollectionUtils.findFirst(
            libraryData.getReservations(),
            reservation -> reservation.getCollectionNo().equals(collectionNo) 
                && reservation.getUserId().equals(userId)
        ) != null;
    }
    // 根据馆藏编号获取图书实体
    public BookItem getBookItemByCollectionNo(String collectionNo) {
        return libraryData.getBookItemsByCollectionNo().get(collectionNo);
    }
    // 获取用户在预约队列中的位置
    public int getReservationPosition(String collectionNo, String userId) {
        List<Reservation> reservationsForBook = (List<Reservation>) CollectionUtils.filter(
            libraryData.getReservations(),
            reservation -> reservation.getCollectionNo().equals(collectionNo)
        );
        for (int i = 0; i < reservationsForBook.size(); i++) {
            if (reservationsForBook.get(i).getUserId().equals(userId)) {
                return i + 1; // 位置从1开始
            }
        }
        return -1; // 用户未预约此书
    }
    // 给图书评分
    public void rateBook(String isbn, double rating) {
        libraryData.getBookRatings().put(isbn, rating);
    }
    // 获取图书评分
    public Double getBookRating(String isbn) {
        return libraryData.getBookRatings().get(isbn);
    }
    // 获取所有图书
    public List<Book> getAllBooks() {
        List<Book> result = new ArrayList<>();
        CollectionUtils.copy(libraryData.getBooksByIsbn().values(), result);
        return result;
    }
    // 获取图书类型统计
    public Map<BookType, Integer> getBookCountByType() {
        return libraryData.getBookCountsByType();
    }
    // 按ISBN排序图书
    public List<Book> sortBooksByIsbn() {
        List<Book> books = new ArrayList<>(libraryData.getBooksByIsbn().values());
        books.sort(Comparator.comparing(Book::getIsbn));
        return books;
    }
    // 书名排序图书
    public List<Book> sortBooksByTitle() {
        List<Book> books = new ArrayList<>(libraryData.getBooksByIsbn().values());
        books.sort(Comparator.comparing(Book::getTitle));
        return books;
    }
    // 按作者排序图书
    public List<Book> sortBooksByAuthor() {
        List<Book> books = new ArrayList<>(libraryData.getBooksByIsbn().values());
        books.sort(Comparator.comparing(Book::getAuthor));
        return books;
    }
    // 按出版日期排序图书
    public List<Book> sortBooksByPublishDate() {
        List<Book> books = new ArrayList<>(libraryData.getBooksByIsbn().values());
        books.sort(Comparator.comparing(Book::getPublishDate));
        return books;
    }
}