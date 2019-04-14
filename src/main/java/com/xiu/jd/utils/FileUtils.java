package com.xiu.jd.utils;

public class FileUtils {
	/**
	 * 取得文件的扩展名
	 * @param pathName 文件的路径
	 * @return 文件的扩展名
	 */
	public static String getFileExt(String pathName){	
		String extension=pathName.substring(pathName.lastIndexOf('.')+1);
		return extension;
	}
}
