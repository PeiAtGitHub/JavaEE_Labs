package javaeetutorial.addressbook.ejb;

import javaeetutorial.addressbook.entity.Contact;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ContactFacade extends AbstractFacade<Contact> {
   
    public ContactFacade() {
        super(Contact.class);
    }
    
    @PersistenceContext(unitName = "address-bookPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


}
