/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.entities;

import com.opensymphony.workflow.spi.Step;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 8, 2013
 * @version 0.1
 */
@Entity(name = "WFStep")
public class WorkflowStep implements Serializable, Step {

    private static final long serialVersionUID = 1456897456132L;
    @Id
    @SequenceGenerator(name = "id_wfstep_sq", sequenceName = "sq_id_wfstep", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_wfstep_sq")
    private long id;
    @Column(name = "nome_step", length = 50, columnDefinition = "VARCHAR2(50)")
    private String nomePasso;
    @Column(name = "status", length = 50, columnDefinition = "VARCHAR2(50)")
    private String status;
    @JoinColumn(name = "id_current_wf", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private WorkFlow currentWorkflow;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id * 15 * 33);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkflowStep)) {
            return false;
        }
        WorkflowStep other = (WorkflowStep) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.workflow.model.entities.WorkflowStep[ id=" + id + " ]";
    }

    public String getNomePasso() {
        return nomePasso;
    }

    public void setNomePasso(String nomePasso) {
        this.nomePasso = nomePasso;
    }

    @Override
    public int getActionId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCaller() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getDueDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getEntryId() {
        return getCurrentWorkflow().getId();
    }

    @Override
    public Date getFinishDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getOwner() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long[] getPreviousStepIds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getStartDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getStepId() {
        return (int) this.id;
    }

    public WorkFlow getCurrentWorkflow() {
        return currentWorkflow;
    }

    public void setCurrentWorkflow(WorkFlow currentWorkflow) {
        this.currentWorkflow = currentWorkflow;
    }
}
