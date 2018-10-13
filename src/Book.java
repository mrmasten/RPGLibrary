import java.io.Serializable;

public abstract class Book implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String publisher;
	private String category;
	private String path;

	public Book(String name, String publisher) {
		this.name = name;
		this.publisher = publisher;
		this.category = ""+this.getClass();
		this.category = this.category.substring(5);
	}
	
	public Book(String name, String publisher, String path) {
		this.name = name;
		this.publisher = publisher;
		this.category = ""+this.getClass();
		this.category = this.category.substring(5);
		this.path = path;
	}
	
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public void setType(String type)
	{}
	
	public void setStartLvl(int startLvl)
	{}
	
	public void setEndLvl(int endLvl)
	{}
	
	public void setSetting(String setting) 
	{}
	
	public String getCategory() {
		return category;
	}
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", publisher=" + publisher + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(String o) {
		
		if (name == null) {
			if (o != null)
				return false;
		} else if (!name.equalsIgnoreCase(o))
			return false;
		return true;
	}
	
}
