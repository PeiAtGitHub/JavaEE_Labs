package javaeetutorial.addressbook.web;

import java.io.Serializable;
import java.util.ResourceBundle;
import javaeetutorial.addressbook.ejb.ContactFacade;
import javaeetutorial.addressbook.entity.Contact;
import javaeetutorial.addressbook.web.util.JsfUtil;
import javaeetutorial.addressbook.web.util.PaginationHelper;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

@Named
@SessionScoped
public class ContactController implements Serializable {

    private static final long serialVersionUID = -8163374738411860012L;
    
    private static final String RESOURCE_BUNDLE_BASE = "/Bundle";
    
    private Contact currentContact;
    private DataModel<Contact> items = null;
    
    @EJB 
    private ContactFacade ejbFacade;
    
    private PaginationHelper paginationHelper;
    
    private int selectedItemIndex;

    public Contact getSelected() {
        if (currentContact == null) {
            currentContact = new Contact();
            selectedItemIndex = -1;
        }
        return currentContact;
    }

    private ContactFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (paginationHelper == null) {
            paginationHelper = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }
                @Override
                public DataModel<Contact> createPageDataModel() {
                    return new ListDataModel<Contact>(getFacade().findRange(
                            new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return paginationHelper;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        currentContact = (Contact)getItems().getRowData();
        selectedItemIndex = paginationHelper.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        currentContact = new Contact();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(currentContact);
            addSuccessMessage("ContactCreated");
            return prepareCreate();
        } catch (Exception e) {
            addErrorMessage(e, "PersistenceErrorOccured");
            return null;
        }
    }

    public String prepareEdit() {
        currentContact = (Contact)getItems().getRowData();
        selectedItemIndex = paginationHelper.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(currentContact);
            addSuccessMessage("ContactUpdated");
            return "View";
        } catch (Exception e) {
            addErrorMessage(e, "PersistenceErrorOccured");
            return null;
        }
    }

    public String destroy() {
        currentContact = (Contact)getItems().getRowData();
        selectedItemIndex = paginationHelper.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(currentContact);
            addSuccessMessage("ContactDeleted");
        } catch (Exception e) {
            addErrorMessage(e, "PersistenceErrorOccured");
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            selectedItemIndex = count-1; 
            if (paginationHelper.getPageFirstItem() >= count) {// go to previous page if last page disappeared
                paginationHelper.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            currentContact = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass=Contact.class)
    public static class ContactControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            ContactController controller = (ContactController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contactController");
            return controller.ejbFacade.find(Long.valueOf(value));
        }


        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Contact) {
                return ((Contact) object).getId().toString();
            } else {
                throw new IllegalArgumentException(String.format("Object %s is of type %s; expected type: %s",  
                                object, object.getClass().getName(), ContactController.class.getName()));
            }
        }
    }
    
    private void addSuccessMessage(String propKey) {
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE).getString(propKey));
    }

    private void addErrorMessage(Exception e, String propKey) {
        JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE).getString(propKey));
    }

}
