package com.w.system;

import com.w.enums.BookType;
import com.w.entity.Book;
import com.w.entity.BookItem;
import com.w.entity.User;

import java.util.*;

public class LibraryConsole {
    private final LibraryService libraryService;
    private final Scanner scanner;

    public LibraryConsole(LibraryService libraryService) {
        this.libraryService = libraryService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = getInput();
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    reserveBook();
                    break;
                case 7:
                    rateBook();
                    break;
                case 8:
                    showStatistics();
                    break;
                case 9:
                    sortBooks();
                    break;
                case 0:
                    System.out.println("感谢使用图书管理系统，再见！");
                    return;
                default:
                    System.out.println("❌ 无效选择，请重新输入");
            }
        }
    }

    private int getInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("❌ 输入格式错误，请输入数字：");
            }
        }
    }

    private void showMenu() {
        System.out.println("========== 图书馆管理系统 ==========");
        System.out.println("【1】 添加图书  【2】 添加用户  【3】 搜索图书");
        System.out.println("【4】 借阅图书  【5】 归还图书  【6】 预约图书");
        System.out.println("【7】 图书评分  【8】 分类统计  【9】 图书排序");
        System.out.println("【0】 退出系统");
        System.out.println("==================================");
        System.out.print("请选择操作(0-9)：");
    }
    // 添加图书主菜单
    private void addBook() {
        System.out.println("---------- 添加图书 ----------");
        System.out.println("请选择添加方式:");
        System.out.println("1. 添加图书信息");
        System.out.println("2. 添加实体书");
        int choice = getInput();
        switch (choice) {
            case 1:
                addBookInfoOnly();
                break;
            case 2:
                addBookItemToExistingBook();
                break;
            default:
                System.out.println("❌ 无效选择");
        }
    }
    // 只添加图书信息
    private void addBookInfoOnly() {
        System.out.println("---------- 添加图书信息 ----------");
        System.out.print("请输入ISBN: ");
        String isbn = scanner.nextLine();
        // 检查ISBN是否已存在
        if (libraryService.isBookExists(isbn)) {
            System.out.println("❌ ISBN已存在！");
            return;
        }
        System.out.print("请输入书名: ");
        String title = scanner.nextLine();
        System.out.print("请输入作者: ");
        String author = scanner.nextLine();
        System.out.print("请输入出版日期(yyyy-mm-dd): ");
        String publishDate = scanner.nextLine();
        System.out.println("请选择图书类型:");
        BookType[] types = BookType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i].getDesc());
        }
        int typeChoice = getInput();
        if (typeChoice < 1 || typeChoice > types.length) {
            System.out.println("❌ 无效的图书类型！");
            return;
        }
        BookType type = types[typeChoice - 1];
        Book book = new Book(isbn, title, author, type, publishDate);
        boolean success = libraryService.addBook(book);
        if (success) {
            System.out.println("✅ 图书信息添加成功！");
        } else {
            System.out.println("❌ 图书信息添加失败！");
        }
    }
    // 基于已有图书信息添加实体书
    private void addBookItemToExistingBook() {
        System.out.println("---------- 添加实体书 ----------");
        // 显示所有已有图书供选择
        List<Book> allBooks = libraryService.getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("❌ 暂无图书信息，请先添加图书信息！");
            return;
        }
        System.out.println("请选择要添加实体书的图书:");
        for (int i = 0; i < allBooks.size(); i++) {
            System.out.println((i + 1) + ". " + allBooks.get(i).getTitle() + 
                              " (ISBN: " + allBooks.get(i).getIsbn() + ")");
        }
        int bookChoice = getInput();
        if (bookChoice < 1 || bookChoice > allBooks.size()) {
            System.out.println("❌ 无效选择！");
            return;
        }
        Book selectedBook = allBooks.get(bookChoice - 1);
        System.out.print("请输入馆藏编号: ");
        String collectionNo = scanner.nextLine();
        // 检查馆藏编号是否已存在
        if (libraryService.isBookItemExists(collectionNo)) {
            System.out.println("❌ 馆藏编号已存在，请使用其他编号！");
            return;
        }
        // 创建一个新的BookItem，这里复用selectedBook的信息
        BookItem bookItem = new BookItem(collectionNo, selectedBook.getIsbn());
        boolean success = libraryService.addBookItem(bookItem);
        if (success) {
            System.out.println("✅ 实体书添加成功！");
        } else {
            System.out.println("❌ 实体书添加失败！");
        }
    }
    // 添加用户
    private void addUser() {
        System.out.print("请输入用户ID: ");
        String id = scanner.nextLine();
        System.out.print("请输入用户名: ");
        String name = scanner.nextLine();
        System.out.print("请输入联系电话: ");
        String contact = scanner.nextLine();
        // 检查用户ID是否已存在
        if (libraryService.isUserExists(id)) {
            System.out.println("❌ 用户ID已存在，请使用其他ID！");
            return;
        }
        User user = new User(id, name, contact);
        boolean success = libraryService.addUser(user);
        if (success) {
            System.out.println("✅ 用户添加成功！");
        } else {
            System.out.println("❌ 用户添加失败！");
        }
    }
    // 搜索图书
    private void searchBook() {
        System.out.println("请选择搜索方式:");
        System.out.println("1. 按ISBN");
        System.out.println("2. 按书名");
        System.out.println("3. 按作者");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                searchBookByIsbn();
                break;
            case 2:
                searchBookByTitle();
                break;
            case 3:
                searchBookByAuthor();
                break;
            default:
                System.out.println("❌ 无效选择");
        }
    }
    // 按ISBN搜索图书
    private void searchBookByIsbn() {
        System.out.print("请输入ISBN: ");
        String isbn = scanner.nextLine();
        Book book = libraryService.findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("❌ 未找到该图书");
            return;
        }
        System.out.println("图书信息:");
        System.out.println(book);
        // 显示评分
        Double rating = libraryService.getBookRating(isbn);
        if (rating != null) {
            System.out.println("评分: " + rating);
        }
        // 显示实体书及其状态
        List<BookItem> bookItems = libraryService.getBookItemsByIsbn(isbn);
        if (!bookItems.isEmpty()) {
            System.out.println("实体书:");
            for (BookItem item : bookItems) {
                System.out.println(item.toString());
            }
        }
    }
    // 按书名搜索图书
    private void searchBookByTitle() {
        System.out.print("请输入书名关键词: ");
        String title = scanner.nextLine();
        List<Book> books = libraryService.findBooksByTitle(title);
        if (books.isEmpty()) {
            System.out.println("❌ 未找到相关图书");
            return;
        }
        System.out.println("找到 " + books.size() + " 本相关图书:");
        for (Book book : books) {
            System.out.println(book);
            System.out.println("-----------------------------");
        }
    }
    // 按作者搜索图书
    private void searchBookByAuthor() {
        System.out.print("请输入作者关键词: ");
        String author = scanner.nextLine();
        List<Book> books = libraryService.findBooksByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("❌ 未找到相关图书");
            return;
        }
        System.out.println("找到 " + books.size() + " 本相关图书:");
        for (Book book : books) {
            System.out.println(book);
            System.out.println("-----------------------------");
        }
    }
    // 借阅图书
    private void borrowBook() {
        System.out.print("请输入用户ID: ");
        String userId = scanner.nextLine();
        // 检查用户是否存在
        if (!libraryService.isUserExists(userId)) {
            System.out.println("❌ 用户不存在，请先添加用户！");
            return;
        }
        System.out.print("请输入馆藏编号: ");
        String collectionNo = scanner.nextLine();
        // 检查用户是否已经借阅了这本书
        boolean isAlreadyBorrowed = libraryService.isBookBorrowedByUser(collectionNo, userId);
        if (isAlreadyBorrowed) {
            System.out.println("❌ 借阅失败，您已经借阅了这本书！");
            return;
        }
        boolean success = libraryService.borrowBook(collectionNo, userId);
        if (success) {
            System.out.println("✅ 借阅成功！");
        } else {
            System.out.println("❌ 借阅失败，图书可能不存在或已被借出！");
        }
    }
    // 归还图书
    private void returnBook() {
        System.out.print("请输入馆藏编号: ");
        String collectionNo = scanner.nextLine();
        String result = libraryService.returnBook(collectionNo);
        if (result != null) {
            if (!result.isEmpty()) {
                // 有预约用户
                System.out.println("✅ 归还成功！图书已自动借给下一位预约用户（用户ID: " + result + "）。");
            } else {
                // 没有预约用户
                System.out.println("✅ 归还成功！");
            }
        } else {
            // 检查图书是否存在
            BookItem bookItem = libraryService.getBookItemByCollectionNo(collectionNo);
            
            if (bookItem != null) {
                System.out.println("❌ 归还失败，该图书未被借出！");
            } else {
                System.out.println("❌ 归还失败，图书不存在！");
            }
        }
    }
    // 预约图书
    private void reserveBook() {
        System.out.print("请输入用户ID: ");
        String userId = scanner.nextLine();
        // 检查用户是否存在
        if (!libraryService.isUserExists(userId)) {
            System.out.println("❌ 用户不存在，请先添加用户！");
            return;
        }
        System.out.print("请输入馆藏编号: ");
        String collectionNo = scanner.nextLine();
        // 检查用户是否已经借阅了这本书
        boolean isAlreadyBorrowed = libraryService.isBookBorrowedByUser(collectionNo, userId);
        if (isAlreadyBorrowed) {
            System.out.println("❌ 预约失败，您已经借阅了这本书！");
            return;
        }
        BookItem bookItem = libraryService.getBookItemByCollectionNo(collectionNo);
        if (bookItem == null) {
            System.out.println("❌ 预约失败，图书不存在！");
            return;
        }
        // 检查图书是否可借阅
        if (bookItem.isAvailable()) {
            System.out.println("❌ 预约失败，图书目前可借阅，请直接借阅！");
            return;
        }
        // 检查用户是否已经预约了这本书
        boolean isAlreadyReserved = libraryService.isBookReservedByUser(collectionNo, userId);
        if (isAlreadyReserved) {
            System.out.println("❌ 预约失败，您已经预约了这本书！");
            return;
        }
        boolean success = libraryService.reserveBook(collectionNo, userId);
        if (success) {
            // 获取预约队列中的位置
            int position = libraryService.getReservationPosition(collectionNo, userId);
            System.out.println("✅ 预约成功！图书已被借出，您在预约队列中的位置是第 " + position + " 位。");
        } else {
            System.out.println("❌ 预约失败！");
        }
    }
    // 评分图书
    private void rateBook() {
        System.out.print("请输入ISBN: ");
        String isbn = scanner.nextLine();
        Book book = libraryService.findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("❌ 未找到该图书");
            return;
        }
        System.out.print("请输入评分(0-10): ");
        double rating = scanner.nextDouble();
        scanner.nextLine();
        if (rating < 0 || rating > 10) {
            System.out.println("评分应在0-10之间");
            return;
        }
        libraryService.rateBook(isbn, rating);
        System.out.println("✅ 评分成功！");
    }
    // 显示统计信息
    private void showStatistics() {
        Map<BookType, Integer> statistics = libraryService.getBookCountByType();
        System.out.println("---------- 图书分类统计 ----------");
        for (Map.Entry<BookType, Integer> entry : statistics.entrySet()) {
            System.out.println(entry.getKey().getDesc() + ": " + entry.getValue() + " 本");
        }
    }
    // 排序图书
    private void sortBooks() {
        System.out.println("请选择排序方式:");
        System.out.println("1. 按ISBN");
        System.out.println("2. 按书名");
        System.out.println("3. 按作者");
        System.out.println("4. 按出版日期");
        int choice = scanner.nextInt();
        scanner.nextLine();
        List<Book> sortedBooks;
        switch (choice) {
            case 1:
                sortedBooks = libraryService.sortBooksByIsbn();
                System.out.println("按ISBN排序结果:");
                break;
            case 2:
                sortedBooks = libraryService.sortBooksByTitle();
                System.out.println("按书名排序结果:");
                break;
            case 3:
                sortedBooks = libraryService.sortBooksByAuthor();
                System.out.println("按作者排序结果:");
                break;
            case 4:
                sortedBooks = libraryService.sortBooksByPublishDate();
                System.out.println("按出版日期排序结果:");
                break;
            default:
                System.out.println("❌ 无效选择");
                return;
        }
        for (Book book : sortedBooks) {
            System.out.println(book);
            System.out.println("-----------------------------");
        }
    }
}
