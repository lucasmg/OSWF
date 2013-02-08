/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.entities;

import com.opensymphony.workflow.spi.Step;
import java.io.Serializable;
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
@Entity(name = "WFStepHist")
public class WorkflowStepHist extends WorkflowStep implements Serializable, Step {

    private static final long serialVersionUID = 98745632146798L;
    @Id
    @SequenceGenerator(name = "id_wfstep_hist_sq", sequenceName = "sq_id_wfstep_hist", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_wfstep_hist_sq")
    private long id;
    @Column(name = "nome_step", length = 50, columnDefinition = "VARCHAR2(50)")
    private String nomePasso;
    @Column(name = "status", length = 50, columnDefinition = "VARCHAR2(50)")
    private String status;
    @JoinColumn(name = "id_history_wf", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private WorkFlow currentWorkflow;

    public WorkflowStepHist() {
    }

    public WorkflowStepHist(WorkflowStep step) {
        //TODO: Implementar a copia de todos os atributos
        this.setStatus(step.getStatus());
    }

    @Override
    public String toString() {
        return "br.com.workflow.model.entities.WorkflowStep[ id=" + id + " ]";
    }
}
