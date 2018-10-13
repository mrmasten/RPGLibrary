
public class Rules extends Book {
	
	
	private static final long serialVersionUID = 1L;
	private String type;
	private String setting;
	
	public Rules(String name, String publisher) {
		super(name, publisher);
	}
	
	public Rules(String name, String publisher, String path) {
		super(name, publisher, path);
	}
	public Rules(String name, String publisher, String path, String type) {
		super(name, publisher, path);
		this.type = type;
	}
	
	public Rules(String name, String publisher, String path, String type, String setting) {
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

	@Override
	public String toString() {
		return "Rules [type=" + type + ", getName()=" + getName() + ", getPublisher()=" + getPublisher() + "]";
	}

	
}
