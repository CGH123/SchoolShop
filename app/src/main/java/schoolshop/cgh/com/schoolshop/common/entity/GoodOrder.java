package schoolshop.cgh.com.schoolshop.common.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 订单状态个人查看类
 */
public class GoodOrder implements Serializable {

    @SerializedName("goodDetail")
    private GoodDetail goodDetail;
    @SerializedName("order")
    private Order order;

    public GoodOrder() {

    }

    public GoodOrder(GoodDetail goodDetail, Order order) {
        this.goodDetail = goodDetail;
        this.order = order;
    }

    public GoodDetail changeToGoodDetail(GoodOrder goodOrder) {
        return goodOrder.goodDetail;
    }

    public Order changeToOrder(GoodOrder goodOrder) {
        return goodOrder.order;
    }

    public GoodDetail getGoodDetail() {
        return goodDetail;
    }

    public void setGoodDetail(GoodDetail goodDetail) {
        this.goodDetail = goodDetail;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
