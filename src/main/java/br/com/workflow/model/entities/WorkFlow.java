/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.entities;

import com.opensymphony.workflow.spi.WorkflowEntry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 8, 2013
 * @version 0.1
 */
@Entity(name = "WorkFlow")
public class WorkFlow implements Serializable, WorkflowEntry {

    private static final long serialVersionUID = 145645123456789L;
    @Id
    @SequenceGenerator(name = "id_wf_sq", sequenceName = "sq_id_wf", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_wf_sq")
    private long id;
    @Column(name = "nome_workflow", length = 50, columnDefinition = "VARCHAR2(50)")
    private String workflowName;
    //
    @Column(name = "wf_state")
    private int state;
    //
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "currentWorkflow")
    private List<WorkflowStep> currentWorkflowStep;
    //
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "historyWorkflow")
    private List<WorkflowStepHist> historyWorkflowStep;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id * 13 * 11);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkFlow)) {
            return false;
        }
        WorkFlow other = (WorkFlow) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.workflow.model.entities.WorkFlow[ id=" + id + " ]";
    }

    @Override
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    @Override
    public boolean isInitialized() {
        return this.state > 0;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public int getState() {
        return state;
    }

    public void addCurrentStep(WorkflowStep workflowStep) {
        if (workflowStep != null) {
            if (this.currentWorkflowStep == null) {
                this.currentWorkflowStep = new ArrayList<>();
            }
            this.currentWorkflowStep.add(workflowStep);
            workflowStep.setCurrentWorkflow(this);
        }
    }

    public void removeCurrentStep(WorkflowStep workflowStep) {
        if (workflowStep != null && this.currentWorkflowStep != null && this.currentWorkflowStep.contains(workflowStep)) {
            this.currentWorkflowStep.remove(workflowStep);
            workflowStep.setCurrentWorkflow(null);
        }
    }

    public void addHistoryStep(WorkflowStepHist workflowStep) {
        if (workflowStep != null) {
            if (this.historyWorkflowStep == null) {
                this.historyWorkflowStep = new ArrayList<>();
            }
            this.historyWorkflowStep.add(workflowStep);
            workflowStep.setCurrentWorkflow(this);
        }
    }

    public void removeHistoryStep(WorkflowStepHist workflowStep) {
        if (workflowStep != null && this.historyWorkflowStep != null && this.historyWorkflowStep.contains(workflowStep)) {
            this.historyWorkflowStep.remove(workflowStep);
            workflowStep.setCurrentWorkflow(null);
        }
    }

    public List<WorkflowStep> getCurrentWorkflowStep() {
        return currentWorkflowStep;
    }

    public List<WorkflowStepHist> getHistoryWorkflowStep() {
        return historyWorkflowStep;
    }
}
