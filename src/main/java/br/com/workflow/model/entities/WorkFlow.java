/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.entities;

import com.opensymphony.workflow.spi.WorkflowEntry;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 13, 2013
 * @version 0.1
 */
@Entity
@Table(name = "WF")
public class WorkFlow implements Serializable, WorkflowEntry {

    private static final long serialVersionUID = 146546578996456987L;
    @Id
    @SequenceGenerator(name = "id_wf_sq", sequenceName = "sq_id_wf", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_wf_sq")
    private long id;
    @Column(name = "NOME_WF", length = 60, columnDefinition = "VARCHAR2(60)")
    private String workFlowName;
    @Column(name = "estado")
    private int state;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id * 65);
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
    public boolean isInitialized() {
        return this.state >= 0;
    }

    @Override
    public int getState() {
        return this.state;
    }

    public WorkFlow setState(int state) {
        this.state = state;
        return this;
    }

    @Override
    public String getWorkflowName() {
        return this.workFlowName;
    }

    public WorkFlow setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
        return this;
    }
}
