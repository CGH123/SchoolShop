package schoolshop.cgh.com.schoolshop.common.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by HUI on 2017-04-20.
 */

public class GoodDetail implements Serializable{
    @SerializedName("goodId")
    private Integer goodId;

    @SerializedName("personId")
    private Integer personId;

    @SerializedName("personName")
    private String personName;

    @SerializedName("personSex")
    private Boolean personSex;

    @SerializedName("personIcon")
    private String personIcon;

    @SerializedName("personGrade")
    private Double personGrade;

    @SerializedName("personSellnum")
    private Integer personSellnum;

    @SerializedName("personBuynum")
    private Integer personBuynum;

    @SerializedName("goodName")
    private String goodName;

    @SerializedName("goodNum")
    private Integer goodNum;

    @SerializedName("goodPrice")
    private Long goodPrice;

    @SerializedName("goodOriginalPrice")
    private Long goodOriginalPrice;

    @SerializedName("goodTime")
    private Date goodTime;

    @SerializedName("goodDetail")
    private String goodDetail;

    @SerializedName("goodViews")
    private Integer goodViews;

    @SerializedName("goodUpvote")
    private Integer goodUpvote;

    @SerializedName("goodImagelist")
    private String goodImagelist;

    @SerializedName("goodKind")
    private Integer goodKind;

    @SerializedName("goodDone")
    private Boolean goodDone;

    public GoodDetail(){

    }

    public GoodDetail(Integer goodId, Integer personId, String personName, Boolean personSex, String personIcon,
                      Double personGrade, Integer personSellnum, Integer personBuynum, String goodName, Integer goodNum,
                      Long goodPrice, Long goodOriginalPrice, Date goodTime, String goodDetail, Integer goodViews,
                      Integer goodUpvote, String goodImagelist, Integer goodKind, Boolean goodDone) {
        super();
        this.goodId = goodId;
        this.personId = personId;
        this.personName = personName;
        this.personSex = personSex;
        this.personIcon = personIcon;
        this.personGrade = personGrade;
        this.personSellnum = personSellnum;
        this.personBuynum = personBuynum;
        this.goodName = goodName;
        this.goodNum = goodNum;
        this.goodPrice = goodPrice;
        this.goodOriginalPrice = goodOriginalPrice;
        this.goodTime = goodTime;
        this.goodDetail = goodDetail;
        this.goodViews = goodViews;
        this.goodUpvote = goodUpvote;
        this.goodImagelist = goodImagelist;
        this.goodKind = goodKind;
        this.goodDone = goodDone;
    }

    public GoodDetail(Good good , Person person){
        this.goodId = good.getGoodId();
        this.personId = person.getPersonId();
        this.personName = person.getPersonName();
        this.personSex = person.getPersonSex();
        this.personIcon = person.getPersonIcon();
        this.personGrade = person.getPersonGrade();
        this.personSellnum = person.getPersonSellnum();
        this.personBuynum = person.getPersonBuynum();
        this.goodName = good.getGoodName();
        this.goodNum = good.getGoodNum();
        this.goodPrice = good.getGoodPrice();
        this.goodOriginalPrice = good.getGoodOriginalPrice();
        this.goodTime = good.getGoodTime();
        this.goodDetail = good.getGoodDetail();
        this.goodViews = good.getGoodViews();
        this.goodUpvote = good.getGoodUpvote();
        this.goodImagelist = good.getGoodImagelist();
        this.goodKind = good.getGoodKind();
        this.goodDone = good.getGoodDone();
    }

    public Good changeToGood(GoodDetail goodDetail){
        Good good = new Good();
        good.setGoodId(goodDetail.getGoodId());
        good.setPersonId(goodDetail.getPersonId());
        good.setGoodName(goodDetail.getGoodName());
        good.setGoodNum(goodDetail.getGoodNum());
        good.setGoodPrice(goodDetail.getGoodPrice());
        good.setGoodOriginalPrice(goodDetail.getGoodOriginalPrice());
        good.setGoodTime(goodDetail.getGoodTime());
        good.setGoodDetail(goodDetail.getGoodDetail());
        good.setGoodViews(goodDetail.getGoodViews());
        good.setGoodUpvote(goodDetail.getGoodUpvote());
        good.setGoodImagelist(goodDetail.getGoodImagelist());
        good.setGoodKind(goodDetail.getGoodKind());
        good.setGoodDone(goodDetail.getGoodDone());
        return good;
    }

    public Person changeToPerson(GoodDetail goodDetail){
        Person person = new Person();
        person.setPersonId(goodDetail.getPersonId());
        person.setPersonName(goodDetail.getPersonName());
        person.setPersonSex(goodDetail.getPersonSex());
        person.setPersonIcon(goodDetail.getPersonIcon());
        person.setPersonGrade(goodDetail.getPersonGrade());
        person.setPersonSellnum(goodDetail.getPersonSellnum());
        person.setPersonBuynum(goodDetail.getPersonBuynum());
        return person;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Boolean getPersonSex() {
        return personSex;
    }

    public void setPersonSex(Boolean personSex) {
        this.personSex = personSex;
    }

    public String getPersonIcon() {
        return personIcon;
    }

    public void setPersonIcon(String personIcon) {
        this.personIcon = personIcon;
    }

    public Double getPersonGrade() {
        return personGrade;
    }

    public void setPersonGrade(Double personGrade) {
        this.personGrade = personGrade;
    }

    public Integer getPersonSellnum() {
        return personSellnum;
    }

    public void setPersonSellnum(Integer personSellnum) {
        this.personSellnum = personSellnum;
    }

    public Integer getPersonBuynum() {
        return personBuynum;
    }

    public void setPersonBuynum(Integer personBuynum) {
        this.personBuynum = personBuynum;
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

    public Long getGoodOriginalPrice() {
        return goodOriginalPrice;
    }

    public void setGoodOriginalPrice(Long goodOriginalPrice) {
        this.goodOriginalPrice = goodOriginalPrice;
    }

    public Date getGoodTime() {
        return goodTime;
    }

    public void setGoodTime(Date goodTime) {
        this.goodTime = goodTime;
    }

    public String getGoodDetail() {
        return goodDetail;
    }

    public void setGoodDetail(String goodDetail) {
        this.goodDetail = goodDetail;
    }

    public Integer getGoodViews() {
        return goodViews;
    }

    public void setGoodViews(Integer goodViews) {
        this.goodViews = goodViews;
    }

    public Integer getGoodUpvote() {
        return goodUpvote;
    }

    public void setGoodUpvote(Integer goodUpvote) {
        this.goodUpvote = goodUpvote;
    }

    public String getGoodImagelist() {
        return goodImagelist;
    }

    public void setGoodImagelist(String goodImagelist) {
        this.goodImagelist = goodImagelist;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getGoodKind() {
        return goodKind;
    }

    public void setGoodKind(Integer goodKind) {
        this.goodKind = goodKind;
    }

    public Boolean getGoodDone() {
        return goodDone;
    }

    public void setGoodDone(Boolean goodDone) {
        this.goodDone = goodDone;
    }

}
