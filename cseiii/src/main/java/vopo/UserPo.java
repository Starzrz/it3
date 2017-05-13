package vopo;

public class UserPo {
	private String id;
	private String passWord;
	private String phoneNumber;
	public UserPo(String id, String passWord, String phoneNumber) {
		this.id = id;
		this.passWord = passWord;
		this.phoneNumber = phoneNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	

}
