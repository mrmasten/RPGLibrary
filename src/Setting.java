
public class Setting extends Book {
	
	private static final long serialVersionUID = 1L;
	private String type;
	private String setting;
	
	public Setting(String name, String publisher, String path) {
		super(name, publisher, path);
	}

	public Setting(String name, String publisher, String path, String type, String setting) {
		super(name, publisher, path);
		this.type = type;
		this.setting = setting;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}
	
	
}
