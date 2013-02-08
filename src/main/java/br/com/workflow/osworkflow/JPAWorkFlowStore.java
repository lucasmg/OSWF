/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.osworkflow;

import br.com.workflow.model.dao.impl.WorkFlowDAO;
import br.com.workflow.model.entities.WorkFlow;
import br.com.workflow.model.entities.WorkflowStep;
import br.com.workflow.model.entities.WorkflowStepHist;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.StoreException;
import com.opensymphony.workflow.query.WorkflowExpressionQuery;
import com.opensymphony.workflow.query.WorkflowQuery;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.opensymphony.workflow.spi.WorkflowStore;
import com.opensymphony.workflow.util.PropertySetDelegate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 8, 2013
 * @version 0.1
 */
public class JPAWorkFlowStore implements WorkflowStore {
    
    private WorkFlowDAO dao = new WorkFlowDAO();
    private PropertySetDelegate propertySetDelegate;
    
    @Override
    public void setEntryState(long entryId, int state) throws StoreException {
        loadEntry(entryId).setState(state);
    }
    
    private WorkFlow loadEntry(long entryId) {
        return dao.getEm().find(WorkFlow.class, entryId);
    }
    
    @Override
    public PropertySet getPropertySet(long entryId) throws StoreException {
        if (getPropertySetDelegate() == null) {
            throw new StoreException("PropertySetDelegate is not properly configured");
        }
        
        return getPropertySetDelegate().getPropertySet(entryId);
    }
    
    @Override
    public Step createCurrentStep(final long entryId, final int stepId, final String owner, final Date startDate, final Date dueDate, final String status, final long[] previousIds) throws StoreException {
        final WorkFlow entry = loadEntry(entryId);
        final WorkflowStep step = new WorkflowStep();

        //step.setOwner(owner);
        //step.setStartDate(startDate);
        //step.setDueDate(dueDate);
        step.setStatus(status);
        
        List previousSteps = new ArrayList(previousIds.length);
        
        for (int i = 0; i < previousIds.length; i++) {
            WorkflowStep previousStep = new WorkflowStep();
            previousSteps.add(previousStep);
        }

        //step.setPreviousSteps(previousSteps);

        entry.addCurrentStep(step);

        // We need to save here because we soon will need the stepId
        // that hibernate calculate on save or flush
        dao.save(entry);
        return step;
    }
    
    @Override
    public WorkflowEntry createEntry(String workflowName) throws StoreException {
        final WorkFlow entry = new WorkFlow();
        entry.setState(WorkflowEntry.CREATED);
        entry.setWorkflowName(workflowName);
        new WorkFlowDAO().persist(entry);
        return entry;
    }
    
    @Override
    public List findCurrentSteps(long entryId) throws StoreException {
        return loadEntry(entryId).getCurrentWorkflowStep();
    }
    
    @Override
    public WorkflowEntry findEntry(long entryId) throws StoreException {
        return loadEntry(entryId);
    }
    
    @Override
    public List findHistorySteps(long entryId) throws StoreException {
        return loadEntry(entryId).getHistoryWorkflowStep();
    }
    
    @Override
    public void init(Map props) throws StoreException {
        setPropertySetDelegate((PropertySetDelegate) props.get("propertySetDelegate"));
    }
    
    public void setPropertySetDelegate(PropertySetDelegate propertySetDelegate) {
        this.propertySetDelegate = propertySetDelegate;
    }
    
    public PropertySetDelegate getPropertySetDelegate() {
        return propertySetDelegate;
    }
    
    @Override
    public Step markFinished(Step step, int actionId, Date finishDate, String status, String caller) throws StoreException {
        final WorkflowStep currentStep = (WorkflowStep) step;

        //currentStep.setActionId(actionId);
        //currentStep.setFinishDate(finishDate);
        currentStep.setStatus(status);
        //currentStep.setCaller(caller);

        return currentStep;
    }
    
    @Override
    public void moveToHistory(Step step) throws StoreException {
        final WorkflowStep currentStep = (WorkflowStep) step;
        final WorkFlow entry = currentStep.getCurrentWorkflow();
        entry.removeCurrentStep(currentStep);
        entry.addHistoryStep(new WorkflowStepHist(currentStep));
        dao.save(entry);
    }
    
    @Override
    public List query(WorkflowQuery wq) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List query(WorkflowExpressionQuery weq) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
