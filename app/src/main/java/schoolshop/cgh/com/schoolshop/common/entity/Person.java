package schoolshop.cgh.com.schoolshop.common.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Person implements Serializable {
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

    public Person(){
    	
    }
    
    public Person(Integer personId, String personName, Boolean personSex, String personIcon, Double personGrade,
			Integer personSellnum, Integer personBuynum) {
		super();
		this.personId = personId;
		this.personName = personName;
		this.personSex = personSex;
		this.personIcon = personIcon;
		this.personGrade = personGrade;
		this.personSellnum = personSellnum;
		this.personBuynum = personBuynum;
	}
    
    

	public Person(String personName, Boolean personSex) {
		this.personId = 0;
		this.personName = personName;
		this.personSex = personSex;
		this.personIcon = "";
		this.personGrade = 5.0;
		this.personSellnum = 0;
		this.personBuynum = 0;
	}

	public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
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
        this.personIcon = personIcon == null ? null : personIcon.trim();
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
}