package facade;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class PageMaker {
	private PageMaker() {
	}

	public static void makeWelcomePage(String mailAddress, String fileName) {
		try {
			Properties mailProp = Database.getProperties("mailData");
			String userName = mailProp.getProperty(mailAddress);
			HtmlWriter writer = new HtmlWriter(new FileWriter(fileName));
			writer.title("Welcome to " + userName + "'s page!");
			writer.paragraph(userName + "のページへようこそ。");
			writer.paragraph("メール待っていますね。");
			writer.mailTo(mailAddress, userName);
			writer.close();
			System.out.println(fileName + " is created for " + mailAddress + " (" + userName + ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void makeLinkPage(String fileName) {
		try {
			Properties mailProp = Database.getProperties("mailData");
			HtmlWriter writer = new HtmlWriter(new FileWriter(fileName));
			writer.title("Link page");
			Set<String> keys = mailProp.stringPropertyNames();
			for (String key : keys) {
				writer.mailTo(key, mailProp.getProperty(key));
			}
			writer.close();
			System.out.println(fileName + " is created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
