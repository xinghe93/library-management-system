package com.w.entity;

public class BorrowRecords {
    private String collectionNo;
    private String borrowerId;

    public BorrowRecords(String collectionNo, String borrowerId) {
        this.collectionNo = collectionNo;
        this.borrowerId = borrowerId;
    }

    public String getCollectionNo() {
        return collectionNo;
    }
    public String getBorrowerId() {
        return borrowerId;
    }

    @Override
    public String toString() {
        return String.format(
                "馆藏编号: %s\n借阅人ID: %s",
                collectionNo, borrowerId
        );
    }
}
