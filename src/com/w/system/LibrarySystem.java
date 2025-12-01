package com.w.system;

import com.w.enums.BookType;
import com.w.entity.Book;
import com.w.entity.User;

public class LibrarySystem {
    public static void main(String[] args) {
        LibraryData libraryData = new LibraryData();
        LibraryService libraryService = new LibraryService(libraryData);
        LibraryConsole libraryConsole = new LibraryConsole(libraryService);

        // 初始化数据
        initData(libraryData, libraryService);

        libraryConsole.start();
    }

    private static void initData(LibraryData libraryData, LibraryService libraryService) {
        // 添加图书
        Book book1 = new Book("978-0-12-345678-9", "Java编程思想", "Bruce Eckel", BookType.TECHNOLOGY, "2020-01-01");
        Book book2 = new Book("978-1-23-456789-0", "Python数据分析", "Wes McKinney", BookType.TECHNOLOGY, "2019-05-15");
        Book book3 = new Book("978-2-34-567890-1", "Spring Boot实战", "Craig Walls", BookType.TECHNOLOGY, "2021-03-20");
        Book book4 = new Book("978-3-45-678901-2", "百年孤独", "加西亚·马尔克斯", BookType.FICTION, "2018-08-10");
        Book book5 = new Book("978-4-56-789012-3", "三体", "刘慈欣", BookType.FICTION, "2015-11-05");
        Book book6 = new Book("978-5-67-890123-4", "白夜行", "东野圭吾", BookType.FICTION, "2017-04-18");
        Book book7 = new Book("978-6-78-901234-5", "时间简史", "史蒂芬·霍金", BookType.SCIENCE, "2016-09-25");
        Book book8 = new Book("978-7-89-012345-6", "万物简史", "比尔·布莱森", BookType.SCIENCE, "2019-02-12");
        Book book9 = new Book("978-8-90-123456-7", "人类简史", "尤瓦尔·赫拉利", BookType.HISTORY, "2017-07-30");
        Book book10 = new Book("978-9-01-234567-8", "艺术的故事", "贡布里希", BookType.ART, "2018-05-08");
        Book book11 = new Book("978-0-13-245678-9", "苏菲的世界", "乔斯坦·贾德", BookType.PHILOSOPHY, "2015-03-14");
        Book book12 = new Book("978-1-24-356789-0", "高效能人士的七个习惯", "史蒂芬·柯维", BookType.OTHER, "2020-06-22");
        Book book13 = new Book("978-1-25-356789-1", "刻意练习：如何从新手到大师", "安德斯·艾利克森", BookType.OTHER, "2016-11-01");
        Book book14 = new Book("978-2-26-456789-2", "原子习惯", "詹姆斯·克利尔", BookType.OTHER, "2018-10-16");
        Book book15 = new Book("978-3-27-556789-3", "你当像鸟飞往你的山", "塔拉·韦斯特弗", BookType.OTHER, "2018-02-20");

        // 添加实体书
        libraryService.addBookItem("10001", book1);
        libraryService.addBookItem("10002", book1);
        libraryService.addBookItem("10003", book1);
        libraryService.addBookItem("10004", book1);
        libraryService.addBookItem("10005", book1);
        libraryService.addBookItem("10011", book2);
        libraryService.addBookItem("10012", book2);
        libraryService.addBookItem("10013", book2);
        libraryService.addBookItem("10014", book2);
        libraryService.addBookItem("10015", book2);
        libraryService.addBookItem("10021", book3);
        libraryService.addBookItem("10022", book3);
        libraryService.addBookItem("10023", book3);
        libraryService.addBookItem("10024", book3);
        libraryService.addBookItem("10025", book3);
        libraryService.addBookItem("20001", book4);
        libraryService.addBookItem("20002", book4);
        libraryService.addBookItem("20003", book4);
        libraryService.addBookItem("20011", book5);
        libraryService.addBookItem("20012", book5);
        libraryService.addBookItem("20013", book5);
        libraryService.addBookItem("20021", book6);
        libraryService.addBookItem("20022", book6);
        libraryService.addBookItem("20023", book6);
        libraryService.addBookItem("30001", book7);
        libraryService.addBookItem("30002", book7);
        libraryService.addBookItem("30011", book8);
        libraryService.addBookItem("30012", book8);
        libraryService.addBookItem("40001", book9);
        libraryService.addBookItem("40002", book9);
        libraryService.addBookItem("50001", book10);
        libraryService.addBookItem("50002", book10);
        libraryService.addBookItem("60001", book11);
        libraryService.addBookItem("60002", book11);
        libraryService.addBookItem("70001", book12);
        libraryService.addBookItem("70002", book12);
        libraryService.addBookItem("70011", book13);
        libraryService.addBookItem("70012", book13);
        libraryService.addBookItem("70021", book14);
        libraryService.addBookItem("70022", book14);
        libraryService.addBookItem("70031", book15);
        libraryService.addBookItem("70032", book15);

        // 添加用户
        libraryService.addUser(new User("U001", "张三", "13812345678"));
        libraryService.addUser(new User("U002", "李四", "13987654321"));
        libraryService.addUser(new User("U003", "王五", "15911223344"));
        libraryService.addUser(new User("U004", "赵六", "18655667788"));
        libraryService.addUser(new User("U005", "孙七", "17799887766"));
    }
}
