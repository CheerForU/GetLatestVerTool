package function;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class compare {

	/**
	 * 比较日期
	 * 
	 * @param DATE1
	 * @param DATE2
	 * @return 最新日期
	 */

	String lastErrInfo = null;

	public void setLastErrInfo(final String lastErrInfo) {
		this.lastErrInfo = lastErrInfo;
	}

	public String getLastErrInfo() {
		return lastErrInfo;
	}

	public int compareDate(final String DATE1, final String DATE2) {

		final DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			final Date dt1 = df.parse(DATE1);
			final Date dt2 = df.parse(DATE2);
			if (dt1.getTime() < dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() > dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (final Exception e) {
			e.printStackTrace();
			Logger.getLogger("compare.class").error(e);
			return 2;
		}
	}

	/**
	 * 是否是合法日期
	 * 
	 * @param str字符串
	 * @return 是否是合法日期
	 */
	public boolean isValidDate(final String str) {
		final boolean convertSuccess = true;
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (final Exception e) {
			setLastErrInfo("版本目录时间非法");
			Logger.getLogger("compare.class").error(lastErrInfo);
			e.printStackTrace();
			Logger.getLogger("compare.class").error(e);
			return false;
		}
		return convertSuccess;
	}
}
