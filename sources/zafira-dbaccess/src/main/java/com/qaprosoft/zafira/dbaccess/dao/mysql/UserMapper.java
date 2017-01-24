package com.qaprosoft.zafira.dbaccess.dao.mysql;

import java.util.List;

import com.qaprosoft.zafira.dbaccess.dao.mysql.search.UserSearchCriteria;
import com.qaprosoft.zafira.models.db.User;

public interface UserMapper
{
	void createUser(User user);

	User getUserById(long id);

	User getUserByUserName(String username);

	void updateUser(User user);

	void deleteUserById(long id);

	void deleteUser(User user);

	List<User> searchUsers(UserSearchCriteria sc);

	Integer getUserSearchCount(UserSearchCriteria sc);
}
