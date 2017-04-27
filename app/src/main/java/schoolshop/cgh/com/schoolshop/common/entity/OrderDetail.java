package schoolshop.cgh.com.schoolshop.common.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class OrderDetail implements Serializable{
	@SerializedName("orderId")
	private Integer orderId;
	@SerializedName("goodId")
	private Integer goodId;
	@SerializedName("orderBuyid")
	private Integer orderBuyid;
	@SerializedName("goodName")
	private String goodName;
	@SerializedName("goodNum")
	private Integer goodNum;
	@SerializedName("goodPrice")
	private Long goodPrice;
	@SerializedName("goodImagelist")
	private String goodImagelist;
	@SerializedName("personName")
	private String personName;
	@SerializedName("userAccount")
	private String userAccount;
	@SerializedName("orderState")
	private Integer orderState;
	@SerializedName("orderTime")
	private Date orderTime;

	public OrderDetail(){

	}

	public OrderDetail(Order order , Good good , Person person , User user){
		this.orderId = order.getOrderId();
		this.goodId = good.getGoodId();
		this.orderBuyid = order.getOrderBuyid();
		this.goodName = good.getGoodName();
		this.goodNum = good.getGoodNum();
		this.goodPrice = good.getGoodPrice();
		this.goodImagelist = good.getGoodImagelist();
		this.personName = person.getPersonName();
		this.userAccount = user.getUserAccount();
		this.orderState = order.getOrderState();
		this.orderTime = order.getOrderTime();
	}

	public OrderDetail(Integer orderId, Integer goodId, Integer orderBuyid, String goodName, Integer goodNum,
					   Long goodPrice, String goodImagelist, String personName, String userAccount, Integer orderState,
					   Date orderTime) {
		super();
		this.orderId = orderId;
		this.goodId = goodId;
		this.orderBuyid = orderBuyid;
		this.goodName = goodName;
		this.goodNum = goodNum;
		this.goodPrice = goodPrice;
		this.goodImagelist = goodImagelist;
		this.personName = personName;
		this.userAccount = userAccount;
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

	public Integer getOrderBuyid() {
		return orderBuyid;
	}

	public void setOrderBuyid(Integer orderBuyid) {
		this.orderBuyid = orderBuyid;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Integer getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(Integer goodNum) {
		this.goodNum = goodNum;
	}

	public Long getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(Long goodPrice) {
		this.goodPrice = goodPrice;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
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

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getGoodImagelist() {
		return goodImagelist;
	}

	public void setGoodImagelist(String goodImagelist) {
		this.goodImagelist = goodImagelist;
	}

}
