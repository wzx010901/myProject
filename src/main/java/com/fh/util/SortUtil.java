package com.fh.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 * 说明：list排序 创建人：WZX Q149156999 修改时间：2015年11月24日
 * 
 * @version
 */
public class SortUtil {
	public static void main(String[] args) {
		System.out.println(testMapSort());
	}

	/**
	 * 对list进行排序
	 * 
	 * @param sortList
	 *            需要排序的list
	 * @param param1
	 *            排序的参数名称
	 * @param orderType
	 *            排序类型：正序-asc；倒序-desc
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List sort(List sortList, String param1, String orderType) {
		Comparator mycmp1 = ComparableComparator.getInstance();
		if ("desc".equals(orderType)) {
			mycmp1 = ComparatorUtils.reversedComparator(mycmp1); // 逆序（默认为正序）
		}

		ArrayList<Object> sortFields = new ArrayList<Object>();
		sortFields.add(new BeanComparator(param1, mycmp1)); // 主排序（第一排序）

		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort(sortList, multiSort);

		return sortList;
	}

	/**
	 * 对list进行排序
	 * 
	 * @param sortList
	 *            需要排序的list
	 * @param param1
	 *            排序的参数名称:参数长度
	 * @param param2
	 *            排序的参数名称:排序参数
	 * @param orderType
	 *            排序类型：正序-asc；倒序-desc
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List sortParam2(List sortList, String param1, String param2, String orderType) {
		Comparator mycmp1 = ComparableComparator.getInstance();
		Comparator mycmp2 = ComparableComparator.getInstance();
		if ("desc".equals(orderType)) {
			mycmp1 = ComparatorUtils.reversedComparator(mycmp1); // 逆序（默认为正序）
		}

		ArrayList<Object> sortFields = new ArrayList<Object>();
		sortFields.add(new BeanComparator(param1, mycmp1)); // 主排序（第一排序）
		sortFields.add(new BeanComparator(param2, mycmp2)); // 主排序（第一排序）

		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort(sortList, multiSort);

		return sortList;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> testMapSort() {
//		List<Map<String,Object>> sortList = new ArrayList<Map<String,Object>>();

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "1");
		map.put("age", "1");

		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("name", "2");
		map2.put("age", "13");

		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("name", "2");
		map1.put("age", "12");

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(map);
		list.add(map1);
		list.add(map2);

		// return sort(list, "age", "asc");
		return sortParam2(list, "name", "age", "asc");
	}

}
