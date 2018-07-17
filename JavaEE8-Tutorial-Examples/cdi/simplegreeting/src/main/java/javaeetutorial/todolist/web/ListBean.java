package javaeetutorial.todolist.web;

import java.io.Serializable;
import java.util.List;

import javaeetutorial.todolist.ejb.RequestBean;
import javaeetutorial.todolist.entity.ToDo;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

@Named
@ConversationScoped
public class ListBean implements Serializable {
    
    private static final long serialVersionUID = 8751711591138727525L;
    
    @EJB
    private RequestBean requestBean;
    
    @NotNull 
    @Getter @Setter
    private String inputString;

    @Getter @Setter
    private ToDo toDo;
    @Setter
    private List<ToDo> toDos;

    public void createTask() {
        this.toDo = requestBean.createToDo(inputString);
    }
    
    public List<ToDo> getToDos() {
        return requestBean.getToDos();
    }
    
}
