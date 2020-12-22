
public class Coordinates {
	private String type;
	private String owner;
	private boolean isCalled;
	
	//constructors
	Coordinates(){
		this.type = null;
		this.owner = null;
		this.isCalled = false;
	}
	
	Coordinates(String type, String owner, boolean isCalled){
		this.type = type;
		this.owner = owner;
		this.isCalled = isCalled;
		
	}
	
	// Accessors
	public String getType() {
		return type;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public boolean getCalled() {
		return isCalled;
	}

	
	// Mutators
	
	public void setType(String type) {
		this.type = type;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setCalled(boolean isCalled) {
		this.isCalled = isCalled;
	}
	
	
}