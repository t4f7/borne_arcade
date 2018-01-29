import MG2D.geometrie.Point;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Texture;

public class BoiteImage extends Boite{

	Texture image;

	BoiteImage(Rectangle rectangle, String image) {
		super(rectangle);
		this.image = new Texture(image+"/photo.png", new Point(760, 568));
	}

	public Texture getImage() {
		return this.image;
	}

	public void setImage(String chemin) {
		this.image.setImg(chemin+"/photo.png");
		this.image.setTaille(400, 400);
	}

}
