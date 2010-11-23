package it.geosolutions.geobatch.egeos.deployers.actions;

/**
 * 
 */

import it.geosolutions.filesystemmonitor.monitor.FileSystemMonitorEvent;
import it.geosolutions.geobatch.action.scripting.GroovyAction;
import it.geosolutions.geobatch.flow.event.action.Action;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Administrator
 * 
 */
public class EGEOSDeployerBaseAction extends GroovyAction implements Action<FileSystemMonitorEvent> {

    /**
     * Default Logger
     */
    @SuppressWarnings("unused")
    private final static Logger LOGGER = Logger.getLogger(EGEOSDeployerBaseAction.class.toString());

    public EGEOSDeployerBaseAction(EGEOSBaseDeployerConfiguration configuration) throws IOException {
        super(configuration);
    }

}