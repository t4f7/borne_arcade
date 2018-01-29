import MG2D.geometrie.Rectangle;

public abstract class Boite {
	private Rectangle rectangle;
	
	Boite(Rectangle rectangle){
		this.rectangle = rectangle;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
}
