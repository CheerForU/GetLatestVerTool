package function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class copy {

	/**
	 * 拷贝目录
	 * 
	 * @param src源路径
	 * @param dest目标路径
	 */

	String lastErrInfo = null;

	public void setLastErrInfo(final String lastErrInfo) {
		this.lastErrInfo = lastErrInfo;
	}

	public String getLastErrInfo() {
		return lastErrInfo;
	}

	public boolean copyDir(final String src, final String dest) {
		if (src == null || dest == null) {
			setLastErrInfo("拷贝目录为空");
			Logger.getLogger("copy.class").error(lastErrInfo);
			return false;
		}
		final int index = src.indexOf("模块");
		if (index == -1) {
			setLastErrInfo("目录中不含模块文件夹");
			Logger.getLogger("copy.class").error(lastErrInfo);
			return false;
		}
		final String s = src.substring(index);
		final File file1 = new File(src);
		final File[] fs = file1.listFiles();
		if (fs == null) {
			setLastErrInfo("子目录获取失败");
			Logger.getLogger("copy.class").error(lastErrInfo);
			return false;
		}
		final File file2 = new File(dest + "\\" + s);
		if (!file2.exists()) {
			file2.mkdirs();
		}
		for (final File f : fs) {
			if (f.isFile()) {
				final boolean b1 = copyFile(f.getPath(), dest + "\\" + s + "\\" + f.getName());
				if (!b1) {
					return false;
				}
			} else if (f.isDirectory()) {
				final boolean b2 = copyDir(f.getPath(), dest + "\\" + s + "\\" + f.getName());
				if (!b2) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 拷贝文件
	 * 
	 * @param src源路径
	 * @param dest目标路径
	 */
	public boolean copyFile(final String src, final String dest) {

		InputStream is = null;
		OutputStream os = null;
		if (src == null) {
			setLastErrInfo("拷贝文件路径错误");
			Logger.getLogger("copy.class").error(lastErrInfo);
			return false;
		}
		try {
			is = new FileInputStream(src);
			os = new FileOutputStream(dest);
			final byte[] flush = new byte[1024];
			int len = 0;
			while (-1 != (len = is.read(flush))) {
				os.write(flush, 0, len);
			}
			os.flush();
		} catch (final Exception e) {
			setLastErrInfo("拷贝文件失败");
			Logger.getLogger("copy.class").error(lastErrInfo);
			e.printStackTrace();
			Logger.getLogger("copy.class").error(e);
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (final Exception e) {
				setLastErrInfo("拷贝文件失败");
				Logger.getLogger("copy.class").error(lastErrInfo);
				e.printStackTrace();
				Logger.getLogger("copy.class").error("关闭流失败");
				return false;
			}

			try {
				if (is != null) {
					is.close();
				}
			} catch (final Exception e) {
				setLastErrInfo("拷贝文件失败");
				Logger.getLogger("copy.class").error(lastErrInfo);
				e.printStackTrace();
				Logger.getLogger("copy.class").error("关闭流失败");
				return false;
			}
		}
		return true;
	}
}
