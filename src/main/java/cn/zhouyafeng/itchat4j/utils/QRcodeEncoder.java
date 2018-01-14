package cn.zhouyafeng.itchat4j.utils;

import com.swetake.util.Qrcode;

public class QRcodeEncoder {

	public static void qRCodeCommon(String content, int size) throws Exception {
		Qrcode qrcodeHandler = new Qrcode();
		// 設置二維碼的容錯率，可選L(7%)、M(15%)、Q(25%)、H(30%)，容錯率越高可儲存內容越少，但是對二維碼清晰度要求越小
		qrcodeHandler.setQrcodeErrorCorrect('M');
		qrcodeHandler.setQrcodeEncodeMode('B');
		// 設置二維碼尺寸,取值範圍1-40,取值越大尺寸越大,即可儲存信息量越多
		qrcodeHandler.setQrcodeVersion(size);
		// 分解文字爲字節數組
		byte[] contentBytes = content.getBytes("utf-8");
		// 輸出
		if (contentBytes.length > 0 && contentBytes.length < 800) {
			boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
			for (int i = 0; i < codeOut.length; i++) {
				for (int j = 0; j < codeOut.length; j++) {
					if (codeOut[j][i])
						System.out.print("██");
					else
						System.out.print("  ");
				}
				System.out.println("");
			}
		} else {
			throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
		}
	}

	public static void main(String[] args) throws Exception {
		String encodeStr = "https://login.weixin.qq.com/l/QZFTg54b5g==";
		System.out.println("開始生成二維碼,內容爲\n" + encodeStr);
		QRcodeEncoder.qRCodeCommon(encodeStr, 3);
	}
}
