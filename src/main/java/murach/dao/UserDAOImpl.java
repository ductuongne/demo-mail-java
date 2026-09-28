package murach.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import murach.exception.DAOException;
import murach.model.User;
import murach.util.DBUtil;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean emailExists(String email) {
        User u = selectUser(email);
        return u != null;
    }

    @Override
    public User selectUser(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            return em.find(User.class, email);
        } catch (Exception e) {
            throw new DAOException("Lỗi khi lấy thông tin người dùng.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public int insert(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(user);
            trans.commit();
            return 1;
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw new DAOException("Lỗi khi thêm người dùng.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public int update(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.merge(user);
            trans.commit();
            return 1;
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw new DAOException("Lỗi khi cập nhật người dùng.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public int delete(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            User user = em.find(User.class, email);
            if (user != null) {
                em.remove(user);
            }
            trans.commit();
            return 1;
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw new DAOException("Lỗi khi xoá người dùng.", e);
        } finally {
            em.close();
        }
    }
}
