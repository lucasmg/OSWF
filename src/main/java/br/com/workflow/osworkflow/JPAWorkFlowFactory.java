/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.workflow.osworkflow;

import com.opensymphony.workflow.FactoryException;
import com.opensymphony.workflow.InvalidWorkflowDescriptorException;
import com.opensymphony.workflow.loader.AbstractWorkflowFactory;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.hibernate.WorkflowName;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 8, 2013
 * @version 0.1
 */
public class JPAWorkFlowFactory extends AbstractWorkflowFactory {
    
    private static boolean forceReload;

    //~ Instance fields ////////////////////////////////////////////////////////

    private Map workflows;
    private boolean reload = false;
    private boolean validate = false;
    
    
    @Override
    public void setLayout(String string, Object o) {
        
    }

    @Override
    public Object getLayout(String string) {
        return null;
    }

    @Override
    public boolean isModifiable(String string) {
        return true;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
     public WorkflowDescriptor getWorkflow(String name, boolean validate) throws FactoryException {
        if (!workflows.containsKey(name)) {
            throw new FactoryException("Unknown workflow name \"" + name + '\"');
        }

        if (reload || forceReload) {
            forceReload = false;
            loadWorkflow(name, validate);
        }

        return (WorkflowDescriptor) workflows.get(name);
    }

    @Override
    public String[] getWorkflowNames() throws FactoryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createWorkflow(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeWorkflow(String string) throws FactoryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void renameWorkflow(String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean saveWorkflow(String string, WorkflowDescriptor wd, boolean bln) throws FactoryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private synchronized void loadWorkflow(final String workflowName, final boolean validate) throws FactoryException {
        try {
            new HibernateTemplate(sessionFactory).execute(new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException, SQLException {
                        try {
                            WorkflowName wfn = (WorkflowName) session.load(WorkflowName.class, workflowName);

                            if (validate) {
                                wfn.getWorkflowDescriptor().validate();
                            }

                            workflows.put(wfn.getWorkflowName(), wfn.getWorkflowDescriptor());

                            return null;
                        } catch (InvalidWorkflowDescriptorException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        } catch (Exception e) {
            throw new FactoryException(e);
        }
    }
    private class WfConfig {
        String wfName;
        WorkflowDescriptor descriptor;
        long lastModified;

        public WfConfig(String name) {
            wfName = name;
        }
    }
}
