package com.example.CarSellProject.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Shopcar {
	
	 @EmbeddedId
	 private ShopCarId id;  // 복합 키 사용
	
	 @ManyToOne
	 @MapsId("shopid")
	 @JoinColumn(name = "shopid", referencedColumnName = "shopid", insertable = false, updatable = false)
	 private Shop shop;
	 
	 @ManyToOne
	 @MapsId("carnum")
	 @JoinColumn(name = "carnum", referencedColumnName = "carnum", insertable = false, updatable = false)
	 private Car car;

}
