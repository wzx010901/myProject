package com.fh.service.${packageName}.${objectNameLower}.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.${packageName}.${objectNameLower}.${objectNameFirstUpper}Manager;

/** 
 * 说明： ${title}
 * 创建人：ZX Q149156999
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 * @version
 */
@Service("${objectNameFirstLower}Service")
public class ${objectNameFirstUpper}Service implements ${objectNameFirstUpper}Manager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("${objectNameFirstUpper}Mapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("${objectNameFirstUpper}Mapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("${objectNameFirstUpper}Mapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("${objectNameFirstUpper}Mapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("${objectNameFirstUpper}Mapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("${objectNameFirstUpper}Mapper.findById", pd);
	}
	
	/**批量删除
	 * @param arrayDataIds
	 * @throws Exception
	 */
	public void deleteAll(String[] arrayDataIds)throws Exception{
		dao.delete("${objectNameFirstUpper}Mapper.deleteAll", arrayDataIds);
	}
	
}

