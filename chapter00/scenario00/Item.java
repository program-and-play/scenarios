/**
 * Possible item of the characters.
 */
public enum Item {
	HEAD, SHOES, GLOVES, COAT, WAND;

	@Override
	public String toString() {
		switch (this) {
		case HEAD:
			return "Hut";
		case SHOES:
			return "Schue";
		case GLOVES:
			return "Handschue";
		case COAT:
			return "Mantel";
		case WAND:
			return "Zauberstab";
		}

		return "";
	};

	/**
	 * @return The folder in which files for this item can be found.
	 */
	public String toFolder() {
		switch (this) {
		case HEAD:
			return "head/";
		case SHOES:
			return "shoes/";
		case GLOVES:
			return "gloves/";
		case COAT:
			return "coat/";
		case WAND:
			return "wand/";
		}

		return "";
	}
}