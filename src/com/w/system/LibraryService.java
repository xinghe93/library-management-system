package com.w.system;

import com.w.entity.Book;
import com.w.entity.User;
import com.w.entity.BorrowRecords;
import com.w.entity.Reservation;
import com.w.enums.BookType;
import com.w.util.CollectionUtils;

import java.util.*;

public class LibraryService {
    private final LibraryData libraryData;

    public LibraryService(LibraryData libraryData) {
        this.libraryData = libraryData;
    }

    // 1. 添加图书（馆藏编号唯一）
    public String addBook(String isbn, String collectionNo, String title, String author, BookType type, String publishDate) {
        Book book = new Book(isbn, collectionNo, title, author, type, publishDate);
        try {
            boolean success = libraryData.addBook(book);
            return success ? "✅ 图书添加成功！\n" : "❌ 添加失败：馆藏编号「" + collectionNo + "」已存在，无法重复添加";
        } catch (Exception e) {
            return "❌ 添加失败：馆藏编号「" + collectionNo + "」已存在，无法重复添加";
        }
    }

    // 2. 添加用户
    public String addUser(String userId, String userName, String contact) {
        // 校验用户ID是否重复
        if (libraryData.isUserIdExists(userId)) {
            return "❌ 错误：用户ID「" + userId + "」已存在，无法重复添加！";
        }
        // 新增用户
        User user = new User(userId, userName, contact);
        libraryData.addUser(user);
        return "✅ 用户添加成功！\n用户信息：" + user;
    }

    // 3. 搜索图书
    public String searchBookByIsbn(String isbn) { // 通过ISBN搜索
        List<Book> books = libraryData.getBooksByIsbn(isbn);
        if (books.isEmpty()) {
            return "未找到ISBN为「" + isbn + "」的图书";
        }
        StringBuilder sb = new StringBuilder("共有" + books.size() + "本书：\n");
        for (Book book : books) {
            sb.append(book.toString()).append("\n");
        }
        return sb.toString();
    }

    public String searchBookByTitle(String title) { // 通过书名搜索
        List<Book> books = libraryData.searchBooksByTitle(title);
        if (books.isEmpty()) {
            return "未找到书名为「" + title + "」的图书";
        }
        return books.toString();
    }

    public String searchBookByAuthor(String author) { // 通过作者搜索
        List<Book> books = libraryData.searchBooksByAuthor(author);
        if (books.isEmpty()) {
            return "未找到作者「" + author + "」的图书";
        }
        return books.toString();
    }

    // 4. 借阅图书
    public String borrowBook(String userId, String collectionNo) {
        User user = libraryData.getUserById(userId);
        if (user == null) {
            return "❌ 错误：用户ID「" + userId + "」不存在！";
        }
        Book book = libraryData.getBookByCollectionNo(collectionNo);
        if (book == null) {
            return "❌ 错误：馆藏编号「" + collectionNo + "」不存在！";
        }
        if (!book.isAvailable()) {
            // 检查是否已预约
            if (libraryData.isReserved(collectionNo, userId)) {
                return "✅ 用户「" + user.getName() + "」已预约图书「" + book.getTitle() + "」，请耐心等待！";
            }
            // 新增预约
            libraryData.addReservation(new Reservation(user, book));
            return "❌ 馆藏编号「" + collectionNo + "」的图书已借出，预约成功！当前排队位置：" + libraryData.getAllBorrowRecords().size();
        }
        // 执行借阅
        book.setAvailable(false);
        BorrowRecords record = new BorrowRecords(user, book);
        libraryData.addBorrowRecord(record);
        return "✅ 借阅成功！\n借阅信息：" + record;
    }

    // 5. 归还图书
    public String returnBook(String collectionNo) {
        Book book = libraryData.getBookByCollectionNo(collectionNo);
        if (book == null) {
            return "❌ 错误：馆藏编号「" + collectionNo + "」不存在！";
        }

        if (book.isAvailable()) {
            return "❌ 错误：该图书未被借出，无法归还！";
        }

        // 查找并移除对应的借阅记录
        List<BorrowRecords> records = libraryData.getAllBorrowRecords();
        BorrowRecords targetRecord = CollectionUtils.findFirst(records,
                r -> r.getBook().getCollectionNo().equals(collectionNo));

        if (targetRecord != null) {
            records.remove(targetRecord);
        }

        // 检查是否有预约
        Reservation reservation = libraryData.getFirstReservationByBook(book);
        if (reservation != null) {
            // 从预约列表中移除预约记录（直接操作原始列表）
            libraryData.removeReservation(reservation);
            // 为预约用户创建借阅记录
            BorrowRecords newRecord = new BorrowRecords(reservation.getUser(), book);
            libraryData.addBorrowRecord(newRecord);
            book.setAvailable(false); // 设置图书为已借出状态
            return String.format(
                    "✅ 图书归还成功！\n已自动为预约用户「%s」完成借阅",
                    reservation.getUser().getName()
            );
        }

        book.setAvailable(true);
        return "✅ 图书归还成功！";
    }

    // 6. 预约图书功能
    public String reserveBook(String userId, String collectionNo) {
        User user = libraryData.getUserById(userId);
        if (user == null) {
            return "❌ 错误：用户ID「" + userId + "」不存在！";
        }

        Book book = libraryData.getBookByCollectionNo(collectionNo);
        if (book == null) {
            return "❌ 错误：馆藏编号「" + collectionNo + "」不存在！";
        }

        if (book.isAvailable()) {
            return "❌ 错误：该图书当前可借阅，无需预约";
        }

        return reserveBook(user, book);
    }

    private String reserveBook(User user, Book book) {
        if (libraryData.isReserved(book.getCollectionNo(), user.getId())) {
            return "✅ 用户「" + user.getName() + "」已预约图书「" + book.getTitle() + "」";
        }

        libraryData.addReservation(new Reservation(user, book));
        return String.format(
                "✅ 预约成功！当前排队位置：%d",
                libraryData.getAllReservations().size()
        );
    }

    // 7. 图书评分功能
    public String rateBook(String collectionNo, double score) {
        if (score < 0 || score > 10) {
            return "❌ 错误：评分必须在0-10之间！";
        }

        Book book = libraryData.getBookByCollectionNo(collectionNo);
        if (book == null) {
            return "❌ 错误：馆藏编号「" + collectionNo + "」不存在！";
        }

        // 更新评分（计算平均评分）
        Map<String, Double> ratings = libraryData.getBookRatings();
        double currentScore = ratings.getOrDefault(book.getIsbn(), 0.0);
        // 简单实现：取上次评分与新评分的平均值（实际应用可存储所有评分计算）
        double newScore = (currentScore + score) / 2;
        ratings.put(book.getIsbn(), newScore);

        return String.format("✅ 评分成功成功！《%s》当前评分：%.1f", book.getTitle(), newScore);
    }

    // 8. 图书分类统计功能
    public String statisticsBookByType() {
        Map<BookType, Integer> stats = libraryData.getBookCountByType();
        if (stats.isEmpty()) {
            return "图书馆暂无图书数据";
        }

        StringBuilder sb = new StringBuilder("图书分类统计：\n");
        stats.forEach((type, count) ->
                sb.append(String.format("%s：%d本\n", type.getDesc(), count))
        );
        return sb.toString();
    }

    // 9. 图书排序功能
    public String sortBooksByPublishDate() {
        return sortBooks("出版日期", Comparator.comparing(Book::getPublishDate));
    }

    public String sortBooksByAuthor() {
        return sortBooks("作者", Comparator.comparing(Book::getAuthor));
    }
    
    public String sortBooksByTitle() {
        return sortBooks("书名", Comparator.comparing(Book::getTitle));
    }
    
    public String sortBooksByIsbn() {
        return sortBooks("ISBN", Comparator.comparing(Book::getIsbn));
    }

    private String sortBooks(String sortType, Comparator<Book> comparator) {
        List<Book> allBooks = new ArrayList<>();
        libraryData.getBooksByIsbn().values().forEach(allBooks::addAll);


        if (allBooks.isEmpty()) {
            return "图书馆暂无图书";
        }

        // 使用工具类排序
        List<Book> sortedBooks = new ArrayList<>(allBooks);
        sortedBooks.sort(comparator);

        return formatSearchResult("按" + sortType + "排序结果", sortedBooks);
    }

    // 工具方法：格式化搜索结果
    private String formatSearchResult(String title, List<Book> books) {
        if (books.isEmpty()) {
            return "未找到相关图书";
        }

        StringBuilder sb = new StringBuilder("----- " + title + "（共" + books.size() + "本）-----\n");
        for (int i = 0; i < books.size(); i++) {
            sb.append(i + 1).append(". ").append(books.get(i)).append("\n\n");
        }
        return sb.toString();
    }
}