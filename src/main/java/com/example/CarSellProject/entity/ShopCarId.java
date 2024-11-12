package com.example.CarSellProject.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ShopCarId implements Serializable {
    private Integer shopid;
    private Integer carnum;
    
    
    // 기본 생성자
    public ShopCarId() {}

    // 생성자
    public ShopCarId(Integer shopid, Integer carnum) {
        this.shopid = shopid;
        this.carnum = carnum;
    }

    // getter, setter
    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public Integer getCarnum() {
        return carnum;
    }

    public void setCarnum(Integer carnum) {
        this.carnum = carnum;
    }

    // equals() 메서드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 같은 객체일 경우
        if (!(o instanceof ShopCarId)) return false; // 타입 확인
        ShopCarId that = (ShopCarId) o;
        return shopid.equals(that.shopid) && carnum.equals(that.carnum); // 두 필드 비교
    }

    // hashCode() 메서드
    @Override
    public int hashCode() {
        return 31 * shopid.hashCode() + carnum.hashCode(); // 해시코드 생성
    }
    
}
