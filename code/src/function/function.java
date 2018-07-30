package function;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class function {

	/**
	 * 获取新版本，包括过滤、比较、拷贝功能
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */

	String lastErrInfo = null;

	public void setLastErrInfo(final String lastErrInfo) {
		this.lastErrInfo = lastErrInfo;
	}

	public String getLastErrInfo() {
		return lastErrInfo;
	}

	public boolean get(String src, final String dest) {
		try {
			if (src == null || dest == null) {
				setLastErrInfo("文件路径未选择");
				Logger.getLogger("function.class").error(lastErrInfo);
				return false;
			}
			final String xml = "filter.xml";
			final ArrayList<String> srcList = filterDir(src, xml);
			if (srcList == null) {
				return false;
			}
			for (int i = 0; i < srcList.size(); i++) {
				final list list = new list();
				final ArrayList<String> dateList = list.listSearch(srcList.get(i));
				if (dateList == null) {
					setLastErrInfo(list.getLastErrInfo());
					Logger.getLogger("function.class").error(lastErrInfo);
					return false;
				}
				final String result = compareDate(src, dateList);
				if (result == null) {
					return false;
				}
				src = srcList.get(i) + "\\" + result;
				final boolean b = copyDir(src, dest);
				if (!b) {
					return false;
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
			Logger.getLogger("function.class").error(e);
			return false;
		}
		return true;
	}

	/**
	 * 过滤
	 * 
	 * @param src源路径
	 * @return 过滤结果
	 */
	private ArrayList<String> filterDir(final String src, final String xml) {
		final filter filter = new filter();
		ArrayList<String> fileList = new ArrayList<String>();
		final list list = new list();
		fileList = list.listFile(src, fileList);
		if (fileList == null) {
			setLastErrInfo(list.getLastErrInfo());
			Logger.getLogger("function.class").error(lastErrInfo);
			return null;
		}
		fileList = filter.filterDir(src, fileList, xml);
		if (fileList == null) {
			setLastErrInfo(filter.getLastErrInfo());
			Logger.getLogger("function.class").error(lastErrInfo);
			return null;
		}
		return fileList;

	}

	/**
	 * 
	 * @param s路径名
	 * @return 比较结果
	 */
	private String compareDate(String src, final ArrayList<String> dateList) {
		try {
			final compare com = new compare();
			src = dateList.get(0);
			if (com.isValidDate(src) == false) {
				setLastErrInfo(com.getLastErrInfo());
				Logger.getLogger("function.class").error(lastErrInfo);
				return null;
			}
			for (int i = 0; i < dateList.size(); i++) {
				final int weight = com.compareDate(src, dateList.get(i));
				if (weight == 2) {
					setLastErrInfo(com.getLastErrInfo());
					Logger.getLogger("function.class").error(lastErrInfo);
					return null;
				}
				if (weight > 0) {
					src = dateList.get(i);
				}
			}
		} catch (final IndexOutOfBoundsException e) {
			setLastErrInfo(src + "版本目录时间非法");
			Logger.getLogger("function.class").error(lastErrInfo);
			e.printStackTrace();
			Logger.getLogger("function.class").error(e);
			return null;
		}
		return src;
	}

	/**
	 * 拷贝
	 * 
	 * @param src源路径
	 * @param dest目标路径
	 */
	private boolean copyDir(final String src, final String dest) {

		final copy copy = new copy();
		final boolean b = copy.copyDir(src, dest);
		if (!b) {
			setLastErrInfo(copy.getLastErrInfo());
			Logger.getLogger("function.class").error(lastErrInfo);
			return false;
		}
		return true;
	}
}
