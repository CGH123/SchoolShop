package schoolshop.cgh.com.schoolshop.common.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDetail implements Serializable{
	@SerializedName("userAccount")
	private String userAccount;
	@SerializedName("userPassword")
    private String userPassword;
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

    public UserDetail(){
    	
    }
    
    public UserDetail(User user , Person person){
    	this.userAccount = user.getUserAccount();
    	this.userPassword = user.getUserPassword();
    	this.personId = person.getPersonId();
    	this.personName = person.getPersonName();
    	this.personSex = person.getPersonSex();
    	this.personIcon = person.getPersonIcon();
    	this.personGrade = person.getPersonGrade();
    	this.personSellnum = person.getPersonSellnum();
    	this.personBuynum = person.getPersonBuynum();
    }
    
	public UserDetail(String userAccount, String userPassword, Integer personId, String personName, Boolean personSex,
			String personIcon, Double personGrade, Integer personSellnum, Integer personBuynum) {
		super();
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.personId = personId;
		this.personName = personName;
		this.personSex = personSex;
		this.personIcon = personIcon;
		this.personGrade = personGrade;
		this.personSellnum = personSellnum;
		this.personBuynum = personBuynum;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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
    
}
