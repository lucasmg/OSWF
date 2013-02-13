/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.oswf;

import br.com.workflow.model.dao.impl.WorkFlowDAO;
import br.com.workflow.model.entities.WorkFlow;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.StoreException;
import com.opensymphony.workflow.query.WorkflowExpressionQuery;
import com.opensymphony.workflow.query.WorkflowQuery;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.opensymphony.workflow.spi.WorkflowStore;
import com.opensymphony.workflow.util.PropertySetDelegate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 13, 2013
 * @version 0.1
 */
public class JPAWorkFlowStore implements WorkflowStore {

    private WorkFlowDAO dao = new WorkFlowDAO();
    private PropertySetDelegate propertySetDelegate;

    @Override
    public PropertySet getPropertySet(long entryId) throws StoreException {
        if (getPropertySetDelegate() == null) {
            throw new StoreException("PropertySetDelegate is not properly configured");
        }

        return getPropertySetDelegate().getPropertySet(entryId);
    }

    public void setPropertySetDelegate(PropertySetDelegate propertySetDelegate) {
        this.propertySetDelegate = propertySetDelegate;
    }

    public PropertySetDelegate getPropertySetDelegate() {
        return propertySetDelegate;
    }

    private WorkFlow load(long entryId) {
        return dao.getEm().find(WorkFlow.class, entryId);
    }

    @Override
    public void setEntryState(long entryId, int state) throws StoreException {
        load(entryId).setState(state);
    }

    @Override
    public Step createCurrentStep(long entryId, int stepId, String owner, Date startDate, Date dueDate, String status, long[] previousIds) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(Map props) throws StoreException {

        setPropertySetDelegate((PropertySetDelegate) props.get("propertySetDelegate"));
    }

    @Override
    public WorkflowEntry createEntry(String workflowName) throws StoreException {
        WorkFlow workflow = new WorkFlow();
        workflow.setWorkFlowName(workflowName).setState(WorkflowEntry.CREATED);
        dao.save(workflow);
        return workflow;
    }

    @Override
    public List findCurrentSteps(long entryId) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WorkflowEntry findEntry(long entryId) throws StoreException {
        return load(entryId);
    }

    @Override
    public List findHistorySteps(long entryId) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Step markFinished(Step step, int actionId, Date finishDate, String status, String caller) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void moveToHistory(Step step) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List query(WorkflowQuery query) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List query(WorkflowExpressionQuery query) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List getWorkflowsByNamesAndSteps(Set nameAndSteps) throws StoreException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
