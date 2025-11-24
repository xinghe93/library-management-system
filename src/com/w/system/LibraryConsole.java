package com.w.system;

import com.w.enums.BookType;

import java.util.Scanner;

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
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    handleAddBook();
                    break;
                case 2:
                    handleAddUser();
                    break;
                case 3:
                    handleSearchBook();
                    break;
                case 4:
                    handleBorrowBook();
                    break;
                case 5:
                    handleReturnBook();
                    break;
                case 6:
                    handleReserveBook();
                    break;
                case 7:
                    handleRateBook();
                    break;
                case 8:
                    handleStatistics();
                    break;
                case 9:
                    handleSortBooks();
                    break;
                case 0:
                    System.out.println("感谢使用图书馆管理系统！");
                    return;
                default:
                    System.out.println("❌ 无效选项，请重新输入！");
            }
            System.out.println("按回车键继续...");
            scanner.nextLine();
        }
    }

    private void showMenu() {
        System.out.println("========== 图书馆管理系统 ==========");
        System.out.println("【1】 添加图书");
        System.out.println("【2】️ 添加用户");
        System.out.println("【3】 搜索图书");
        System.out.println("【4】 借阅图书");
        System.out.println("【5】 归还图书");
        System.out.println("【6】 预约图书");
        System.out.println("【7】 图书评分");
        System.out.println("【8】 分类统计");
        System.out.println("【9】 图书排序");
        System.out.println("【0】 退出系统");
        System.out.println("=====================================");
        System.out.print("请选择操作（0-9）：");
    }

    private int getIntInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("❌ 输入格式错误，请输入数字：");
            }
        }
    }

    private void handleAddBook() {
        System.out.println("---------- 添加图书 ----------");
        System.out.print("请输入ISBN：");
        String isbn = scanner.nextLine();
        System.out.print("请输入馆藏编号：");
        String collectionNo = scanner.nextLine();
        System.out.print("请输入书名：");
        String title = scanner.nextLine();
        System.out.print("请输入作者：");
        String author = scanner.nextLine();
        System.out.print("请输入出版日期（格式：yyyy-mm-dd）：");
        String publishDate = scanner.nextLine();

        System.out.println("可选图书类型：");
        BookType[] types = BookType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("%d. %s\n", i + 1, types[i].getDesc());
        }
        System.out.print("请选择图书类型（输入序号）：");

        int typeIndex = getIntInput();
        if (typeIndex < 1 || typeIndex > types.length) {
            System.out.println("❌ 无效的图书类型！");
            return;
        }

        BookType type = types[typeIndex - 1];
        String result = libraryService.addBook(isbn, collectionNo, title, author, type, publishDate);
        System.out.println(result);
    }

    private void handleAddUser() {
        System.out.println("---------- 添加用户 ----------");
        System.out.print("请输入用户ID：");
        String userId = scanner.nextLine();
        System.out.print("请输入用户名：");
        String userName = scanner.nextLine();
        System.out.print("请输入联系方式：");
        String contact = scanner.nextLine();

        String result = libraryService.addUser(userId, userName, contact);
        System.out.println(result);
    }

    private void handleSearchBook() {
        System.out.println("---------- 搜索图书 ----------");
        System.out.println("1. 按ISBN搜索");
        System.out.println("2. 按书名搜索");
        System.out.println("3. 按作者搜索");
        System.out.print("请选择搜索方式（1-3）：");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                System.out.print("请输入ISBN：");
                String isbn = scanner.nextLine();
                System.out.println(libraryService.searchBookByIsbn(isbn));
                break;
            case 2:
                System.out.print("请输入书名关键词：");
                String title = scanner.nextLine();
                System.out.println(libraryService.searchBookByTitle(title));
                break;
            case 3:
                System.out.print("请输入作者关键词：");
                String author = scanner.nextLine();
                System.out.println(libraryService.searchBookByAuthor(author));
                break;
            default:
                System.out.println("❌ 无效选项！");
        }
    }

    private void handleBorrowBook() {
        System.out.println("---------- 借阅图书 ----------");
        System.out.print("请输入用户ID：");
        String userId = scanner.nextLine();
        System.out.print("请输入馆藏编号：");
        String collectionNo = scanner.nextLine();

        String result = libraryService.borrowBook(userId, collectionNo);
        System.out.println(result);
    }

    private void handleReturnBook() {
        System.out.println("---------- 归还图书 ----------");
        System.out.print("请输入馆藏编号：");
        String collectionNo = scanner.nextLine();

        String result = libraryService.returnBook(collectionNo);
        System.out.println(result);
    }

    private void handleReserveBook() {
        System.out.println("---------- 预约图书 ----------");
        System.out.print("请输入用户ID：");
        String userId = scanner.nextLine();
        System.out.print("请输入馆藏编号：");
        String collectionNo = scanner.nextLine();

        String result = libraryService.reserveBook(userId, collectionNo);
        System.out.println(result);
    }

    private void handleRateBook() {
        System.out.println("---------- 图书评分 ----------");
        System.out.print("请输入馆藏编号：");
        String collectionNo = scanner.nextLine();
        System.out.print("请输入评分（0-10）：");
        double score = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        String result = libraryService.rateBook(collectionNo, score);
        System.out.println(result);
    }

    private void handleStatistics() {
        System.out.println("---------- 分类统计 ----------");
        String result = libraryService.statisticsBookByType();
        System.out.println(result);
    }

    private void handleSortBooks() {
        System.out.println("---------- 图书排序 ----------");
        System.out.println("1. 按出版日期排序");
        System.out.println("2. 按作者排序");
        System.out.println("3. 按书名排序");
        System.out.println("4. 按ISBN排序");
        System.out.print("请选择排序方式（1-4）：");

        int choice = getIntInput();
        String result;
        switch (choice) {
            case 1:
                result = libraryService.sortBooksByPublishDate();
                break;
            case 2:
                result = libraryService.sortBooksByAuthor();
                break;
            case 3:
                result = libraryService.sortBooksByTitle();
                break;
            case 4:
                result = libraryService.sortBooksByIsbn();
                break;
            default:
                result = "❌ 无效选项！";
        }
        System.out.println(result);
    }
}
