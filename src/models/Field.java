package models;

public class Field implements IField{
	
	private String title;
	private String detail;
	private int value;

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDetail() {
		return this.detail;
	}

	@Override
	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public int getValue() {
		return this.value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
	}

}
