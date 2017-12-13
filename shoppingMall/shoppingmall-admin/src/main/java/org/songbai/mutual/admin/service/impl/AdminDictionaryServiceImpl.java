package org.songbai.mutual.admin.service.impl;

import java.util.List;

import org.songbai.mutual.admin.dao.AdminDictionaryDao;
import org.songbai.mutual.admin.service.AdminDictionaryService;
import org.songbai.mutual.model.dictionary.AdminDictionaryModel;
import org.songbai.mutual.utils.mvc.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDictionaryServiceImpl implements AdminDictionaryService {
	@Autowired
	AdminDictionaryDao dictionaryDao;
	@Override
	public Page<AdminDictionaryModel> findDictionaryByPage(Integer page, Integer pageSize, Integer status, String type) {
		//起始行
		Integer limit =((page-1)>0?(page-1)*pageSize:0);
		List<AdminDictionaryModel> list = dictionaryDao.findDictionaryByPage(limit, pageSize,status,type);
		Integer rowCount=dictionaryDao.findDictionaryCount(status,type);
		Page<AdminDictionaryModel> pageResult=new Page<AdminDictionaryModel>(limit,pageSize,rowCount);
		pageResult.setData(list);
		return pageResult;
	}
	/**
	 * 增加字典
	 */
	@Override
	public void addDictionary(AdminDictionaryModel dictionaryModel) {
		dictionaryDao.addDictionary(dictionaryModel);
	}
	@Override
	public void updateDictionary(AdminDictionaryModel dictionaryModel) {
		dictionaryDao.updateDictionary(dictionaryModel);
		
	}

}
