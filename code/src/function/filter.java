package function;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class filter {

	/**
	 * 过滤模块
	 * 
	 * @param src源路径
	 * @return 过滤结果
	 */

	String lastErrInfo = null;

	public void setLastErrInfo(final String lastErrInfo) {
		this.lastErrInfo = lastErrInfo;
	}

	public String getLastErrInfo() {
		return lastErrInfo;
	}

	public ArrayList<String> filterDir(final String src, final ArrayList<String> fileList, final String xml) {
		final ArrayList<String> filterList = new ArrayList<String>();
		try {
			final File f = new File(xml);
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document doc = builder.parse(f);
			final NodeList nl = doc.getElementsByTagName("path");
			for (int i = 0; i < nl.getLength(); i++) {
				filterList.add(src + "\\" + doc.getElementsByTagName("path").item(i).getFirstChild().getNodeValue());
				for (int j = 0; j < fileList.size(); j++) {
					for (int k = 0; k < filterList.size(); k++) {
						if (fileList.get(j).equals((filterList).get(k))) {
							fileList.remove(j);
						}
					}
				}
			}
		} catch (final IOException e) {
			setLastErrInfo("配置文件未找到");
			Logger.getLogger("filter.class").error(lastErrInfo);
			e.printStackTrace();
			Logger.getLogger("filter.class").error(e);
			return null;
		} catch (final ParserConfigurationException e) {
			setLastErrInfo("配置文件解析错误");
			Logger.getLogger("filter.class").error(lastErrInfo);
			e.printStackTrace();
			Logger.getLogger("filter.class").error(e);
			return null;
		} catch (final SAXException e) {
			setLastErrInfo("配置文件解析错误");
			Logger.getLogger("filter.class").error(lastErrInfo);
			e.printStackTrace();
			Logger.getLogger("filter.class").error(e);
			return null;
		}

		return fileList;
	}

}
