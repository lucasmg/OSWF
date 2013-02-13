/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.entities;

import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.hibernate.HibernateWorkflowEntry;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 13, 2013
 * @version 0.1
 */
@Entity
@Table(name = "CURRENT_STEP")
public class CurrentStep implements Serializable, Step {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_cstep_sq", sequenceName = "sq_id_cstep", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_cstep_sq")
    private long id;
    //
    @Column(name = "dtVencimento")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dueDate;
    @Column(name = "dtFim")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date finishDate;
    @Column(name = "dtInicio")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startDate;
    @JoinColumn(name = "id_wf", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkFlow entry;
    private transient List<Step> previousSteps;
    private String caller;
    private String owner;
    private String status;
    private int actionId;
    private int stepId;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurrentStep)) {
            return false;
        }
        CurrentStep other = (CurrentStep) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.workflow.model.entities.CurrentStep[ id=" + id + " ]";
    }

    @Override
    public int getActionId() {
        return this.actionId;
    }

    @Override
    public String getCaller() {
        return this.caller;
    }

    @Override
    public Date getDueDate() {
        return this.dueDate;
    }

    @Override
    public long getEntryId() {
        return this.entry.getId();
    }

    @Override
    public Date getFinishDate() {
        return this.finishDate;
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    @Override
    public long[] getPreviousStepIds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public int getStepId() {
        return this.stepId;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEntry(WorkFlow entry) {
        this.entry = entry;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }
    
    
}
