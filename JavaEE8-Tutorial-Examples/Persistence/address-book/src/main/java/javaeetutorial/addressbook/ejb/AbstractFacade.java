package javaeetutorial.addressbook.ejb;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public abstract class AbstractFacade<T> {
    
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery cq = createNewCriteriaQuery();
        cq.select(cq.from(entityClass));
        return createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = createNewCriteriaQuery();
        cq.select(cq.from(entityClass));
        Query q = createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }


    public int count() {
        CriteriaQuery cq = createNewCriteriaQuery();
        cq.select(getEntityManager().getCriteriaBuilder().count((Root<T>) cq.from(entityClass)));
        return ((Long) createQuery(cq).getSingleResult()).intValue();
    }


    private CriteriaQuery createNewCriteriaQuery() {
        return getEntityManager().getCriteriaBuilder().createQuery();
    }

    private TypedQuery createQuery(CriteriaQuery cq) {
        return getEntityManager().createQuery(cq);
    }
    
}
