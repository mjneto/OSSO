/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author manoel.neto
 */

public class UsuarioBean {
    private boolean campocadastro;
    
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    
    public UsuarioBean() {
    }

    public boolean isCampocadastro() {
        return campocadastro;
    }

    public void setCampocadastro(boolean campocadastro) {
        boolean old = this.campocadastro;
        this.campocadastro = campocadastro;
        getChangeSupport().firePropertyChange("campocadastro", old, campocadastro);
    }

    public PropertyChangeSupport getChangeSupport() {
        return changeSupport;
    }

    public void setChangeSupport(PropertyChangeSupport changeSupport) {
        this.changeSupport = changeSupport;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getChangeSupport().addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getChangeSupport().removePropertyChangeListener(listener);
    }
}
