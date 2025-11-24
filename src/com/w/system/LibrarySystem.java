package com.w.system;

import com.w.entity.Book;
import com.w.entity.User;
import com.w.enums.BookType;

import java.util.*;

public class LibrarySystem {
    public static void main(String[] args) {
        LibraryData libraryData = new LibraryData();
        LibraryService libraryService = new LibraryService(libraryData);
        LibraryConsole console = new LibraryConsole(libraryService);

        // 初始化测试用例
        initializeTestData(libraryData);

        console.start();
    }

    private static void initializeTestData(LibraryData libraryData) {
        // 添加示例图书
        // 技术类
        libraryData.addBook(new Book("978-0-12-345678-9", "A001", "Java编程思想", "Bruce Eckel", BookType.TECHNOLOGY, "2020-01-01"));
        libraryData.addBook(new Book("978-0-12-345678-9", "A002", "Java编程思想", "Bruce Eckel", BookType.TECHNOLOGY, "2020-01-01"));
        libraryData.addBook(new Book("978-0-12-345678-9", "A003", "Java编程思想", "Bruce Eckel", BookType.TECHNOLOGY, "2020-01-01"));
        libraryData.addBook(new Book("978-0-12-345678-9", "A004", "Java编程思想", "Bruce Eckel", BookType.TECHNOLOGY, "2020-01-01"));
        libraryData.addBook(new Book("978-0-12-345678-9", "A005", "Java编程思想", "Bruce Eckel", BookType.TECHNOLOGY, "2020-01-01"));
        libraryData.addBook(new Book("978-1-23-456789-0", "A011", "Python数据分析", "Wes McKinney", BookType.TECHNOLOGY, "2019-05-15"));
        libraryData.addBook(new Book("978-1-23-456789-0", "A012", "Python数据分析", "Wes McKinney", BookType.TECHNOLOGY, "2019-05-15"));
        libraryData.addBook(new Book("978-1-23-456789-0", "A013", "Python数据分析", "Wes McKinney", BookType.TECHNOLOGY, "2019-05-15"));
        libraryData.addBook(new Book("978-1-23-456789-0", "A014", "Python数据分析", "Wes McKinney", BookType.TECHNOLOGY, "2019-05-15"));
        libraryData.addBook(new Book("978-1-23-456789-0", "A015", "Python数据分析", "Wes McKinney", BookType.TECHNOLOGY, "2019-05-15"));
        libraryData.addBook(new Book("978-2-34-567890-1", "A021", "Spring Boot实战", "Craig Walls", BookType.TECHNOLOGY, "2021-03-20"));
        libraryData.addBook(new Book("978-2-34-567890-1", "A022", "Spring Boot实战", "Craig Walls", BookType.TECHNOLOGY, "2021-03-20"));
        libraryData.addBook(new Book("978-2-34-567890-1", "A023", "Spring Boot实战", "Craig Walls", BookType.TECHNOLOGY, "2021-03-20"));
        libraryData.addBook(new Book("978-2-34-567890-1", "A024", "Spring Boot实战", "Craig Walls", BookType.TECHNOLOGY, "2021-03-20"));
        libraryData.addBook(new Book("978-2-34-567890-1", "A025", "Spring Boot实战", "Craig Walls", BookType.TECHNOLOGY, "2021-03-20"));

        // 小说类
        libraryData.addBook(new Book("978-3-45-678901-2", "B001", "百年孤独", "加西亚·马尔克斯", BookType.FICTION, "2018-08-10"));
        libraryData.addBook(new Book("978-3-45-678901-2", "B002", "百年孤独", "加西亚·马尔克斯", BookType.FICTION, "2018-08-10"));
        libraryData.addBook(new Book("978-3-45-678901-2", "B003", "百年孤独", "加西亚·马尔克斯", BookType.FICTION, "2018-08-10"));
        libraryData.addBook(new Book("978-4-56-789012-3", "B011", "三体", "刘慈欣", BookType.FICTION, "2015-11-05"));
        libraryData.addBook(new Book("978-4-56-789012-3", "B012", "三体", "刘慈欣", BookType.FICTION, "2015-11-05"));
        libraryData.addBook(new Book("978-4-56-789012-3", "B013", "三体", "刘慈欣", BookType.FICTION, "2015-11-05"));
        libraryData.addBook(new Book("978-5-67-890123-4", "B021", "白夜行", "东野圭吾", BookType.FICTION, "2017-04-18"));
        libraryData.addBook(new Book("978-5-67-890123-4", "B022", "白夜行", "东野圭吾", BookType.FICTION, "2017-04-18"));
        libraryData.addBook(new Book("978-5-67-890123-4", "B023", "白夜行", "东野圭吾", BookType.FICTION, "2017-04-18"));

        // 科学类
        libraryData.addBook(new Book("978-6-78-901234-5", "C001", "时间简史", "史蒂芬·霍金", BookType.SCIENCE, "2016-09-25"));
        libraryData.addBook(new Book("978-6-78-901234-5", "C002", "时间简史", "史蒂芬·霍金", BookType.SCIENCE, "2016-09-25"));
        libraryData.addBook(new Book("978-6-78-901234-5", "C003", "时间简史", "史蒂芬·霍金", BookType.SCIENCE, "2016-09-25"));
        libraryData.addBook(new Book("978-6-78-901234-5", "C004", "时间简史", "史蒂芬·霍金", BookType.SCIENCE, "2016-09-25"));
        libraryData.addBook(new Book("978-6-78-901234-5", "C005", "时间简史", "史蒂芬·霍金", BookType.SCIENCE, "2016-09-25"));
        libraryData.addBook(new Book("978-7-89-012345-6", "C011", "万物简史", "比尔·布莱森", BookType.SCIENCE, "2019-02-12"));
        libraryData.addBook(new Book("978-7-89-012345-6", "C012", "万物简史", "比尔·布莱森", BookType.SCIENCE, "2019-02-12"));
        libraryData.addBook(new Book("978-7-89-012345-6", "C013", "万物简史", "比尔·布莱森", BookType.SCIENCE, "2019-02-12"));
        libraryData.addBook(new Book("978-7-89-012345-6", "C014", "万物简史", "比尔·布莱森", BookType.SCIENCE, "2019-02-12"));
        libraryData.addBook(new Book("978-7-89-012345-6", "C015", "万物简史", "比尔·布莱森", BookType.SCIENCE, "2019-02-12"));

        // 历史类
        libraryData.addBook(new Book("978-8-90-123456-7", "D001", "人类简史", "尤瓦尔·赫拉利", BookType.HISTORY, "2017-07-30"));
        libraryData.addBook(new Book("978-8-90-123456-7", "D002", "人类简史", "尤瓦尔·赫拉利", BookType.HISTORY, "2017-07-30"));
        libraryData.addBook(new Book("978-8-90-123456-7", "D003", "人类简史", "尤瓦尔·赫拉利", BookType.HISTORY, "2017-07-30"));

        // 艺术类
        libraryData.addBook(new Book("978-9-01-234567-8", "E001", "艺术的故事", "贡布里希", BookType.ART, "2018-05-08"));
        libraryData.addBook(new Book("978-9-01-234567-8", "E002", "艺术的故事", "贡布里希", BookType.ART, "2018-05-08"));
        libraryData.addBook(new Book("978-9-01-234567-8", "E003", "艺术的故事", "贡布里希", BookType.ART, "2018-05-08"));

        // 哲学类
        libraryData.addBook(new Book("978-0-13-245678-9", "F001", "苏菲的世界", "乔斯坦·贾德", BookType.PHILOSOPHY, "2015-03-14"));
        libraryData.addBook(new Book("978-0-13-245678-9", "F002", "苏菲的世界", "乔斯坦·贾德", BookType.PHILOSOPHY, "2015-03-14"));
        libraryData.addBook(new Book("978-0-13-245678-9", "F003", "苏菲的世界", "乔斯坦·贾德", BookType.PHILOSOPHY, "2015-03-14"));

        // 其他类
        libraryData.addBook(new Book("978-1-24-356789-0", "G001", "高效能人士的七个习惯", "史蒂芬·柯维", BookType.OTHER, "2020-06-22"));
        libraryData.addBook(new Book("978-1-24-356789-0", "G002", "高效能人士的七个习惯", "史蒂芬·柯维", BookType.OTHER, "2020-06-22"));
        libraryData.addBook(new Book("978-1-24-356789-0", "G003", "高效能人士的七个习惯", "史蒂芬·柯维", BookType.OTHER, "2020-06-22"));
        libraryData.addBook(new Book("978-1-25-356789-1", "G011", "刻意练习：如何从新手到大师", "安德斯·艾利克森", BookType.OTHER, "2016-11-01"));
        libraryData.addBook(new Book("978-1-25-356789-1", "G012", "刻意练习：如何从新手到大师", "安德斯·艾利克森", BookType.OTHER, "2016-11-01"));
        libraryData.addBook(new Book("978-2-26-456789-2", "G021", "原子习惯", "詹姆斯·克利尔", BookType.OTHER, "2018-10-16"));
        libraryData.addBook(new Book("978-2-26-456789-2", "G022", "原子习惯", "詹姆斯·克利尔", BookType.OTHER, "2018-10-16"));
        libraryData.addBook(new Book("978-3-27-556789-3", "G031", "你当像鸟飞往你的山", "塔拉·韦斯特弗", BookType.OTHER, "2018-02-20"));

        // 添加示例用户
        libraryData.addUser(new User("U001", "张三", "13812345678"));
        libraryData.addUser(new User("U002", "李四", "13987654321"));
        libraryData.addUser(new User("U003", "王五", "15911223344"));
        libraryData.addUser(new User("U004", "赵六", "18655667788"));
        libraryData.addUser(new User("U005", "孙七", "17799887766"));
    }
}