package com.capitalbiotech.drugdeafness;

public class DataUtil {
	/**
	 * �ָ�·��
	 * 
	 * @param path
	 * @return ���طָ���·��
	 */
	public static String[] separatePath(String path) {
		if ("".equals(path)||null==path) {
			return null;
		}
		String[] sep = path.split("\\.");
		return new String[] { sep[0], sep[1] };
	}
}
