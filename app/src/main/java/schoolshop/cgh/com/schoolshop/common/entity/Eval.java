package schoolshop.cgh.com.schoolshop.common.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Eval implements Serializable{
    @SerializedName("evalId")
    private Integer evalId;
    @SerializedName("goodId")
    private Integer goodId;
    @SerializedName("personId")
    private Integer personId;
    @SerializedName("evalGrade")
    private Double evalGrade;
    @SerializedName("evalDetail")
    private String evalDetail;
    @SerializedName("evalTime")
    private Date evalTime;

    public Eval(){
    	
    }

    public Eval(Integer goodId, Integer personId, Double evalGrade, String evalDetail, Date evalTime){
        this.evalId = 0;
        this.goodId = goodId;
        this.personId = personId;
        this.evalGrade = evalGrade;
        this.evalDetail = evalDetail;
        this.evalTime = evalTime;
    }

    public Eval(Integer evalId, Integer goodId, Integer personId, Double evalGrade, String evalDetail, Date evalTime) {
		super();
		this.evalId = evalId;
		this.goodId = goodId;
		this.personId = personId;
		this.evalGrade = evalGrade;
		this.evalDetail = evalDetail;
		this.evalTime = evalTime;
	}

	public Integer getEvalId() {
        return evalId;
    }

    public void setEvalId(Integer evalId) {
        this.evalId = evalId;
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

    public Double getEvalGrade() {
        return evalGrade;
    }

    public void setEvalGrade(Double evalGrade) {
        this.evalGrade = evalGrade;
    }

    public String getEvalDetail() {
        return evalDetail;
    }

    public void setEvalDetail(String evalDetail) {
        this.evalDetail = evalDetail == null ? null : evalDetail.trim();
    }

    public Date getEvalTime() {
        return evalTime;
    }

    public void setEvalTime(Date evalTime) {
        this.evalTime = evalTime;
    }
}