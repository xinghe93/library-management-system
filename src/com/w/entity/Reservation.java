package com.w.entity;

public class Reservation {
    private String collectionNo;
    private String userId;

    public Reservation(String collectionNo, String userId) {
        this.collectionNo = collectionNo;
        this.userId = userId;
    }

    public String getCollectionNo() {
        return collectionNo;
    }
    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.format(
                "馆藏编号: %s\n预约人ID: %s",
                collectionNo, userId
        );
    }
}
