/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.workflow.model.dao.impl;

import br.com.workflow.model.dao._abstract.DataAccessObjectAbstract;
import br.com.workflow.model.entities.WorkFlow;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 8, 2013
 * @version 0.1
 */
public class WorkFlowDAO extends DataAccessObjectAbstract<WorkFlow> {
    public void save(WorkFlow entidade) {
        if(entidade.getId()>0){
            merge(entidade);
        }else{
            persist(entidade);
        }
    }
}
