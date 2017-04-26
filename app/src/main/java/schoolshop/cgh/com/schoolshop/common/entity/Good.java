package schoolshop.cgh.com.schoolshop.common.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Good implements Serializable {
    @SerializedName("goodId")
    private Integer goodId;

    @SerializedName("personId")
    private Integer personId;

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

    public Good() {

    }

    public Good(Integer goodId){
        this.goodId = goodId;
    }

    public Good(Integer goodId, Integer personId, String goodName, Integer goodNum, Long goodPrice,
                Long goodOriginalPrice, Date goodTime, String goodDetail, String goodImagelist, Integer goodKind) {
        this.goodId = goodId;
        this.personId = personId;
        this.goodName = goodName;
        this.goodNum = goodNum;
        this.goodPrice = goodPrice;
        this.goodOriginalPrice = goodOriginalPrice;
        this.goodTime = goodTime;
        this.goodDetail = goodDetail;
        this.goodViews = 0;
        this.goodUpvote = 0;
        this.goodImagelist = goodImagelist;
        this.goodKind = goodKind;
        this.goodDone = false;
    }

    public Good(Integer goodId, Integer personId, String goodName, Integer goodNum, Long goodPrice,
                Long goodOriginalPrice, Date goodTime, String goodDetail, Integer goodViews, Integer goodUpvote,
                String goodImagelist, Integer goodKind, Boolean goodDone) {
        super();
        this.goodId = goodId;
        this.personId = personId;
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

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
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
        this.goodDetail = goodDetail == null ? null : goodDetail.trim();
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
        this.goodImagelist = goodImagelist == null ? null : goodImagelist.trim();
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