package murach.dao;

import murach.model.User;

public interface UserDAO {

    boolean emailExists(String email);
    User selectUser(String email);

    int insert(User user);
    int update(User user);
    int delete(String email);
}
