package org.songbai.mutual.admin.service;

import org.songbai.mutual.model.dictionary.AdminDictionaryModel;
import org.songbai.mutual.utils.mvc.Page;

public interface AdminDictionaryService {

	public Page<AdminDictionaryModel> findDictionaryByPage(Integer page, Integer pageSize, Integer status, String type);

	public void addDictionary(AdminDictionaryModel dictionaryModel);

	public void updateDictionary(AdminDictionaryModel dictionaryModel);

}
