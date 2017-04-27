package schoolshop.cgh.com.schoolshop.common.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{
    @SerializedName("orderId")
    private Integer orderId;
    @SerializedName("goodId")
    private Integer goodId;
    @SerializedName("orderSellid")
    private Integer orderSellid;
    @SerializedName("orderBuyid")
    private Integer orderBuyid;
    @SerializedName("orderState")
    private Integer orderState;
    @SerializedName("orderTime")
    private Date orderTime;

    public Order(){
    	
    }
    
    public Order(Integer orderId){
    	this.orderId = orderId;
    }
    
    public Order(Integer orderId, Integer goodId, Integer orderSellid, Integer orderBuyid, Integer orderState,
			Date orderTime) {
		super();
		this.orderId = orderId;
		this.goodId = goodId;
		this.orderSellid = orderSellid;
		this.orderBuyid = orderBuyid;
		this.orderState = orderState;
		this.orderTime = orderTime;
	}

	public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getOrderSellid() {
        return orderSellid;
    }

    public void setOrderSellid(Integer orderSellid) {
        this.orderSellid = orderSellid;
    }

    public Integer getOrderBuyid() {
        return orderBuyid;
    }

    public void setOrderBuyid(Integer orderBuyid) {
        this.orderBuyid = orderBuyid;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}