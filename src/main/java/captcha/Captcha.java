package captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Captcha {

	private static CaptchaConfigBean config = ConfigGetter.loadResourceFile();
	private static BufferedImage image = new BufferedImage(config.getWidth(), config.getHeight(), BufferedImage.TYPE_INT_RGB);
	private static char[] captchars = new char[]{};
	private static boolean relativelyColor;
	private static String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

	private Captcha() {}

	static void captcha() throws IOException {
		captchars = config.getCaptchars();
		for (int i = config.getImgCountStart(); i <= config.getImgCountEnd(); i++) {
			System.out.println(i + " - " + captcha(i));
		}
	}

	/**
	 * 產生驗證圖
	 */
	private static String captcha(int imgCount) throws IOException {
		if (!config.isRelativelyColor()) relativelyColor = randomByTime().nextBoolean();

		// 產生圖片緩衝區
		Graphics graphics = image.getGraphics();

		// 設定底色
		drawRandomColor(graphics, !relativelyColor);
//		graphics.setColor(new Color(255,255,255));
		graphics.fillRoundRect(0, 0, config.getWidth(), config.getHeight(), 0, 0);

		// 產生驗證文字
		List<String> strs = newString();

		// 加入文字與干擾
		interferenceBackground(graphics);
		String result = drawString(graphics, strs);
		interferenceOnStrs(graphics);
		if (config.isUseAlphaStr()) drawAlphaString(graphics);

		// 釋放資源並停止繪圖
		graphics.dispose();


		ImageIO.write(image, "png", new File(config.getStoredPath() +"jr_image_demo"+ imgCount +".jpg"));
		return result;
	}

	// XXX 資料來源

	// 字串組合方式
	private static List<String> newString() {
		List<String> result = new ArrayList<>();
		for (int y = 0; y < (config.getStrs() + randomNumber(config.getStrRandomRange())); y++) {
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < config.getStrLength(); i++) {
				str.append( captchars[randomNumber(captchars.length - 1)] );
			}
			result.add(str.toString());
		}
		return result;
	}

	// XXX 圖片調整

	private static void interferenceBackground(Graphics graphics) {
		drawNodes(graphics);
		drawVerticalLine(graphics);
		drawHorizontalLine(graphics);
		for (int i = 0; i < randomNumber(captchars.length - 1); i++) {
			shear(graphics, config.getWidth(), config.getHeight(), randomByTime().nextBoolean());
		}
		drawNodes(graphics);
		drawWords(graphics);
		drawVerticalLine(graphics);
		drawHorizontalLine(graphics);
		for (int i = 0; i < randomNumber(captchars.length - 1); i++) {
			shear(graphics, config.getWidth(), config.getHeight(), randomByTime().nextBoolean());
		}
	}

	private static String drawString(Graphics graphics, List<String> strs) {
		// 調整文字起始的水平位置與垂直位置
		int horizontalPosition = 0;
		int verticalPosition = (config.getHeight() - 5);

		StringBuilder result = new StringBuilder();
		for (String str : strs) {
			graphics.setFont(randomFont());
			drawRandomColor(graphics, relativelyColor);

			// 文字水平位置與垂直位置加權

			// 加入文字
			graphics.drawString(str, horizontalPosition, verticalPosition);
			result.append(str);

			// 水平位置要累加
			horizontalPosition += config.getFontHorizontalInterval();
		}
		return result.toString();
	}

	/**
	 * 建立一組透明但不會回傳的數字
	 */
	private static String drawAlphaString(Graphics graphics) {
		// 調整文字起始的水平位置與垂直位置
		int horizontalPosition = 0;
		int verticalPosition = (config.getHeight() - 5);

		StringBuilder result = new StringBuilder();
		for (String str : newString()) {
			graphics.setFont( new Font(fonts[0] + " Inline", 0, config.getFontSize()).deriveFont(fontRotate()) );
			drawAlphaColor(graphics);

			// 文字水平位置與垂直位置加權

			// 加入文字
			graphics.drawString(str, horizontalPosition, verticalPosition);
			result.append(str);

			// 水平位置要累加
			horizontalPosition += config.getFontHorizontalInterval();
		}
		return result.toString();
	}

	/**
	 * 在驗證文字上方覆蓋干擾
	 */
	private static void interferenceOnStrs(Graphics graphics) {
//		for (int i = 0; i < randomNumber(config.getStrShearRange()); i++) {
//			shear(graphics, config.getWidth(), config.getHeight(), randomByTime().nextBoolean());
//		}
		drawNodes(graphics);
		drawWords(graphics);
		drawVerticalLine(graphics);
		drawHorizontalLine(graphics);
	}

	// XXX Lib

	/**
	 * 設定隨機字形
	 */
	private static Font randomFont() {
		return new Font(
				fonts[randomNumber(fonts.length - 1)] + " Inline",
				randomNumber(2),
				config.getFontSize()
		).deriveFont(fontRotate());
	}

	/**
	 *  加入垂直線
	 */
	private static void drawVerticalLine(Graphics graphics) {
		for (int i = 0; i < (config.getLines() + randomNumber(config.getLineRandomRange())); i++) {
			drawLine(graphics, (randomNumber(config.getWidth()) + 1), 0, (randomNumber(config.getWidth()) + 1), config.getHeight());
		}
	}

	/**
	 *  加入水平線
	 */
	private static void drawHorizontalLine(Graphics graphics) {
		for (int i = 0; i < (config.getLines() + randomNumber(config.getLineRandomRange())); i++) {
			drawLine(graphics, 0, (randomNumber(config.getHeight()) + 1), config.getWidth(), (randomNumber(config.getHeight()) + 1));
		}
	}

	/**
	 *  加入雜訊點
	 */
	private static void drawNodes(Graphics graphics) {
		for (int i = 0; i < config.getNodes(); i++) {
			int sw = randomNumber(config.getWidth());
			int sh = randomNumber(config.getHeight());
			drawLine(graphics, sw, sh, (sw + 1), (sh + 1));
		}
	}

	/**
	 *  扭曲圖片
	 */
	private static void shear(Graphics graphics, int width, int height, boolean wayto) {
		Random rand = randomByTime();
		int period = rand.nextInt(10) + 10;
		int frames = rand.nextInt(2);
		int phase = rand.nextInt(2);

		for (int i = 0; i < (wayto ? width : height); i++) {
			double d = (period >> 1)
						* Math.sin(
							(double) i / (double) period
							+ (6.2831853071795862D * phase) / frames
						);
			graphics.copyArea(
					(wayto ? i : 0), (wayto ? 0 : i),
					(wayto ? 1 : width), (wayto ? height : 1),
					(wayto ? 0 : (int) d), (wayto ? (int) d : 0)
					);
		}
	}

	/**
	 *  加入隨機字母背景
	 */
	private static void drawWords(Graphics graphics) {
		for (int i = 0; i < config.getWords(); i++) {
			drawRandomColor(graphics);
			graphics.setFont(new Font(
					fonts[randomNumber(fonts.length - 1)] + " Inline",
					randomNumber(2),
					(randomNumber(config.getFontSize()/4) + (config.getFontSize()/10)) )
					.deriveFont(fontRotate())
					);
			graphics.drawString(
					String.valueOf(captchars[randomNumber(captchars.length - 1)]),
					randomNumber(config.getWidth()),
					randomNumber(config.getHeight())
					);
		}
	}

	/**
	 *  製造隨機旋轉
	 */
	private static AffineTransform fontRotate() {
		Random rand = randomByTime();
		AffineTransform fontAT = new AffineTransform();
		int rotate = rand.nextInt(config.getFontRotate());
		fontAT.rotate(rand.nextBoolean() ? Math.toRadians(rotate) : (-1 * Math.toRadians(rotate)) );
		return fontAT;
	}

	/**
	 *  畫線
	 */
	private static void drawLine(Graphics graphics, int swidth, int sheight, int ewidth, int eheight) {
		drawRandomColor(graphics);
		graphics.drawLine(swidth, sheight, ewidth, eheight);
	}

	/**
	 *  相對隨機色彩
	 */
	private static void drawRandomColor(Graphics graphics, boolean weight) {
		int base = 0;
		if (weight) {
			base = 185;
		}
		drawColor(graphics, (randomNumber(70) + base), (randomNumber(70) + base), (randomNumber(70) + base), 255);
	}

	/**
	 *  隨機色彩
	 */
	private static void drawRandomColor(Graphics graphics) {
		drawColor(graphics, randomNumber(255), randomNumber(255), randomNumber(255), 255);
	}

	/**
	 *  弄成透明的
	 */
	private static void drawAlphaColor(Graphics graphics) {
		drawColor(graphics, 255, 255, 255, 1);
	}

	/**
	 *  上色彩
	 */
	private static void drawColor(Graphics graphics, int r, int b, int g, int a) {
		graphics.setColor(new Color(r, b, g, a));
	}

	/**
	 *  隨機取數字
	 *  使用程式執行時的時間
	 */
	private static Random randomByTime() {
		return new Random(System.currentTimeMillis());
	}

	/**
	 *  隨機取數字
	 *  從 0 開始
	 */
	private static int randomNumber(int range) {
		return (int) (Math.random() * range);
	}

}
