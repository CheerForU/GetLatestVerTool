package frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import function.function;

public class myFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	static String src = null;
	static String dest = null;

	/**
	 * 画出窗口，为按钮绑定事件
	 */
	public static void addFrame() {

		// 画出窗口
		final JFrame frame = new myFrame();
		frame.setLayout(null);
		frame.setTitle("模块获取工具");
		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 添加按钮
		final JButton btn1 = new JButton("浏览");
		final JButton btn2 = new JButton("浏览");
		final JButton btn3 = new JButton("获取");
		btn1.setBounds(270, 20, 58, 22);
		btn2.setBounds(270, 70, 58, 22);
		btn3.setBounds(270, 120, 58, 22);
		btn1.setVisible(true);
		btn2.setVisible(true);
		btn3.setVisible(true);
		frame.add(btn1);
		frame.add(btn2);
		frame.add(btn3);

		// 设置字体
		final Font font = new Font("宋体", Font.BOLD, 11);
		btn1.setFont(font);
		btn2.setFont(font);
		btn3.setFont(font);

		// 添加显示框
		final JLabel label1 = new JLabel("源路径:");
		final JLabel label2 = new JLabel("目标路径:");
		label1.setBounds(40, 20, 60, 20);
		label2.setBounds(40, 70, 60, 20);
		label1.setVisible(true);
		label2.setVisible(true);
		label1.setFont(font);
		label2.setFont(font);
		frame.add(label1);
		frame.add(label2);

		// 添加文本框
		final JTextField t1 = new JTextField();
		final JTextField t2 = new JTextField();
		t1.setBounds(120, 20, 120, 22);
		t2.setBounds(120, 70, 120, 22);
		t1.setVisible(true);
		t2.setVisible(true);
		frame.add(t1);
		frame.add(t2);
		frame.setVisible(true);

		// 为按钮添加事件
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				final int returnVal = chooser.showOpenDialog(btn1);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = chooser.getSelectedFile();
					src = file.getAbsolutePath();
					t1.setText(src);
				}
			}
		});

		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				final JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				final int returnVal = chooser.showOpenDialog(btn1);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = chooser.getSelectedFile();
					dest = file.getAbsolutePath();
					t2.setText(dest);
				}
			}

		});

		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final function f = new function();
				final boolean b = f.get(src, dest);
				String info = "文件获取成功";
				if (!b) {
					info = f.getLastErrInfo();
				}
				final myFrame myFrame = new myFrame();
				myFrame.addInfo(info);
			}
		});

		// 为文本框添加事件
		t1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (new File(t1.getText()).isDirectory()) {
					src = t1.getText();
				} else {
					final myFrame myFrame = new myFrame();
					myFrame.addInfo("路径名错误");
				}

			}
		});
		t2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (new File(t2.getText()).isDirectory()) {
					dest = t2.getText();
				} else {
					final myFrame myFrame = new myFrame();
					myFrame.addInfo("路径名错误");
				}
			}
		});

	}

	public void addInfo(final String string) {
		final JFrame f = new JFrame("提示");
		f.setLayout(null);
		f.setBounds(50, 50, 200, 100);
		f.setVisible(true);
		final JLabel label = new JLabel(string);
		label.setBounds(30, 20, 120, 20);
		final Font f1 = new Font("宋体", Font.BOLD, 12);
		label.setFont(f1);
		f.add(label);

	}

	public static void main(final String[] args) {
		addFrame();
	}
}
