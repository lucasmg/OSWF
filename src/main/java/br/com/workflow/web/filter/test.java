/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.web.filter;

import br.com.workflow.model.entities.Usuario;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.InvalidRoleException;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 13, 2013
 * @version 0.1
 */
public class test {

    public static void main(String[] args) throws InvalidRoleException, InvalidInputException, WorkflowException {
        Workflow wf = new BasicWorkflow("admin");
        long id = wf.initialize("example", 100, null);
    }
}
