package org.songbai.mutual.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.songbai.mutual.model.dictionary.AdminDictionaryModel;

public interface AdminDictionaryDao {

	List<AdminDictionaryModel> findDictionaryByPage(@Param(value="limit")Integer limit, @Param(value="pageSize")Integer pageSize,@Param(value="status") Integer status,@Param(value="type") String type);

	Integer findDictionaryCount(@Param(value="status")Integer status,@Param(value="type") String type);

	void addDictionary(AdminDictionaryModel dictionaryModel);

	void updateDictionary(AdminDictionaryModel dictionaryModel);

}
