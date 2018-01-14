package cn.zhouyafeng.itchat4j.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import cn.zhouyafeng.itchat4j.utils.enums.OsNameEnum;

public class EWM {

	// \033[31;4m*\033[0m"
	public static String white_block = "\033[0;37;47m  ";
	public static String black_block = "\033[0;37;40m  ";
	public static String new_line = "\n";

	public static void main(String[] args) throws Exception {

		String qpath = "";
		switch (Config.getOsNameEnum()) {
		case WINDOWS:
			if (Config.getOsNameEnum().equals(OsNameEnum.WINDOWS)) {
				qpath = "D://Work/Java/workspace/sts-3.8.4/QR2.jpg";
			}
			break;
		case LINUX:
			if (Config.getOsNameEnum().equals(OsNameEnum.LINUX)) {
				qpath = "/opt/QR.jpg";
			}
			break;
		default:
			break;
		}
		String text = "https://login.weixin.qq.com/l/QZFTg54b5g==";
		encodeImg(qpath, text);
		// getImagePixel(qpath);
		// decodeImg(qpath);
	}

	public static void getImagePixel(String image) throws Exception {
		int[] rgb = new int[3];
		File file = new File(image);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		System.out.println("width=" + width + ",height=" + height + ".");
		System.out.println("minx=" + minx + ",miniy=" + miny + ".");
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				if (rgb[0] == 0 && rgb[1] == 0 && rgb[2] == 0) {
					System.out.printf(black_block);
				} else {
					System.out.printf(white_block);
				}
			}
			System.out.println();
		}
	}

	public static void encodeImg(String qpath, String text) throws IOException {
		int width = 100;
		int height = 100;
		String format = "png";
		// Hashtable hints = new Hashtable();
		// hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		// hints.put(EncodeHintType.MARGIN, 2);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
			Path file = new java.io.File(qpath).toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	public static String decodeImg(String qpath) throws NotFoundException {
		MultiFormatReader formatReader = new MultiFormatReader();
		File file = new File(qpath);
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
		// Hashtable hints = new Hashtable();
		// hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		Result result = formatReader.decode(binaryBitmap);
		System.err.println("解析结果：" + result.toString());
		System.out.println(result.getBarcodeFormat());
		System.out.println(result.getText());
		return result.toString();
	}
}
