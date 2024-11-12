package com.example.CarSellProject.dto;


import com.example.CarSellProject.entity.Car;
import com.example.CarSellProject.entity.Shop;
import com.example.CarSellProject.entity.ShopCarId;
import com.example.CarSellProject.entity.Shopcar;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShopCarDTO {
    private int shopid;
    private int carnum;
    
    
    public Shopcar toEntity(Shop shop, Car car) {
        ShopCarId id = new ShopCarId();
        id.setShopid(shop.getShopid());
        id.setCarnum(car.getCarnum());

        return new Shopcar(id, shop, car);
    }

    
}
