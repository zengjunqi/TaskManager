package com.zeng.yan.taskmanager.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zeng.yan.taskmanager.bean.FileInfo;

public class FileUtils {
	public static List<FileInfo> scanDir(String srcPath) {
		List<FileInfo> infos = new ArrayList<FileInfo>();
		File dir = new File(srcPath);
		if (dir.exists()) {

			File[] files = dir.listFiles();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < files.length; i++) {
				FileInfo info = new FileInfo();
				info.setName(files[i].getName());
				info.setPath(files[i].getAbsolutePath());
				if (files[i].length() / 1024 == 0) {
					info.setSize(files[i].length() + "B");
				} else {
					info.setSize(files[i].length() / 1024 + "KB");
				}
				info.setCreateDate(dateFormat.format(new Date(files[i]
						.lastModified())));
				infos.add(info);
			}

		} 
		return infos;
	}

	public static void deleteFile(String path) {
		List<FileInfo> infos = new ArrayList<FileInfo>();
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}
}
