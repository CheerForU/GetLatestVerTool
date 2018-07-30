package function;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class list {

	String lastErrInfo = null;

	public void setLastErrInfo(final String lastErrInfo) {
		this.lastErrInfo = lastErrInfo;
	}

	public String getLastErrInfo() {
		return lastErrInfo;
	}

	public ArrayList<String> listFile(final String src, final ArrayList<String> fileList) {
		final File f = new File(src);
		final File[] files = f.listFiles();
		if (files == null) {
			setLastErrInfo("子目录获取失败");
			Logger.getLogger("list.class").error(lastErrInfo);
			return null;
		}
		for (final File temp : files) {
			fileList.add(temp.getAbsolutePath().toString());
		}
		return fileList;
	}

	public ArrayList<String> listSearch(final String s) {
		final File f = new File(s);
		final File[] in = f.listFiles();
		final ArrayList<String> dateList = new ArrayList<String>();
		if (in == null) {
			setLastErrInfo("子目录获取失败");
			Logger.getLogger("list.class").error(lastErrInfo);
			return null;
		}
		for (int i = 0; i < in.length; i++) {
			final String regEx = "[0-9]{8}";
			final Matcher m = Pattern.compile(regEx).matcher(in[i].toString());
			if (m.find()) {
				dateList.add(m.group());
			} else {
				setLastErrInfo(s + "版本目录时间非法");
				Logger.getLogger("list.class").error(lastErrInfo);
				return null;
			}
		}
		return dateList;
	}
}
