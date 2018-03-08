package captcha;

class CaptchaConfigBean {

	private char[] captchars = new char[] {
//		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
//		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	private String storedPath = "D:\\Captchas\\";
	private int imgCountStart = 1;
	private int imgCountEnd = 1;

	private int width = 100;
	private int height = 30;
	private int strs = 5;
	private int strRandomRange = 0;
	private int strLength = 1;
	private int strShearRange = 5;
	private int fontSize = height;
	private int fontRotate = 1;
	private int fontHorizontalInterval = fontSize * 2 / 3;
	private int words = width / 4;
	private int nodes = width * height / 50;
	private int lines = 1;
	private int lineRandomRange = 1;
	private boolean relativelyColor = false;
	private boolean useAlphaStr = true;

	public char[] getCaptchars() {
		return captchars;
	}

	public void setCaptchars(char[] captchars) {
		this.captchars = captchars;
	}

	public String getStoredPath() {
		return storedPath;
	}

	public void setStoredPath(String storedPath) {
		this.storedPath = storedPath;
	}

	public int getImgCountStart() {
		return imgCountStart;
	}

	public void setImgCountStart(int imgCountStart) {
		this.imgCountStart = imgCountStart;
	}

	public int getImgCountEnd() {
		return imgCountEnd;
	}

	public void setImgCountEnd(int imgCountEnd) {
		this.imgCountEnd = imgCountEnd;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getStrs() {
		return strs;
	}

	public void setStrs(int strs) {
		this.strs = strs;
	}

	public int getStrRandomRange() {
		return strRandomRange;
	}

	public void setStrRandomRange(int strRandomRange) {
		this.strRandomRange = strRandomRange;
	}

	public int getStrLength() {
		return strLength;
	}

	public void setStrLength(int strLength) {
		this.strLength = strLength;
	}

	public int getStrShearRange() {
		return strShearRange;
	}

	public void setStrShearRange(int strShearRange) {
		this.strShearRange = strShearRange;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getFontRotate() {
		return fontRotate;
	}

	public void setFontRotate(int fontRotate) {
		this.fontRotate = fontRotate;
	}

	public int getFontHorizontalInterval() {
		return fontHorizontalInterval;
	}

	public void setFontHorizontalInterval(int fontHorizontalInterval) {
		this.fontHorizontalInterval = fontHorizontalInterval;
	}

	public int getWords() {
		return words;
	}

	public void setWords(int words) {
		this.words = words;
	}

	public int getNodes() {
		return nodes;
	}

	public void setNodes(int nodes) {
		this.nodes = nodes;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public int getLineRandomRange() {
		return lineRandomRange;
	}

	public void setLineRandomRange(int lineRandomRange) {
		this.lineRandomRange = lineRandomRange;
	}

	public boolean isRelativelyColor() {
		return relativelyColor;
	}

	public void setRelativelyColor(boolean relativelyColor) {
		this.relativelyColor = relativelyColor;
	}

	public boolean isUseAlphaStr() {
		return useAlphaStr;
	}

	public void setUseAlphaStr(boolean useAlphaStr) {
		this.useAlphaStr = useAlphaStr;
	}
}
