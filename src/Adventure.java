
public class Adventure extends Book {
	
	private static final long serialVersionUID = 1L;
	private String type;
	private int startLvl;
	private int endLvl;
	private String setting;

	public Adventure(String name, String publisher) {
		super(name, publisher);
	}
	public Adventure(String name, String publisher, String path) {
		super(name, publisher, path);
	}
	
	public Adventure(String name, String publisher, String path, String type, String setting, int startLvl, int endLvl)
	{
		super(name, publisher, path);
		this.type = type;
		this.setting = setting;
		this.startLvl = startLvl;
		this.endLvl = endLvl;

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStartLvl() {
		return startLvl;
	}

	public void setStartLvl(int startLvl) {
		this.startLvl = startLvl;
	}

	public int getEndLvl() {
		return endLvl;
	}

	public void setEndLvl(int endLvl) {
		this.endLvl = endLvl;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

	@Override
	public String toString() {
		return "Adventure [type=" + type + ", startLvl=" + startLvl + ", endLvl=" + endLvl + ", setting="
				+ setting + ", getName()=" + getName() + ", getPublisher()=" + getPublisher() + "]";
	}
	
	

}
