/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2007-2012 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.geosolutions.geobatch.catalog.impl;

import it.geosolutions.geobatch.catalog.Configuration;
import it.geosolutions.geobatch.catalog.dao.DAO;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author (r2) Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 * 
 * @param <C>
 */
public abstract class BasePersistentResource<C extends Configuration> extends BaseResource {

    public BasePersistentResource(final String id) {
        super(id);
    }

    /**
     * @deprecated name and description not needed here
     */
    public BasePersistentResource(final String id, final String name, final String description) {
        super(id);
        LoggerFactory.getLogger("ROOT").error("Deprecated constructor called from " + getClass().getName() , new Throwable("TRACE!") );
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(BasePersistentResource.class);

    
    private C configuration;

    
    private DAO dao;

    
    private boolean removed;

    public C getConfiguration() {
        return configuration;
    }

    public DAO getDAO() {
        return dao;
    }

    public void persist() throws IOException {
        if (configuration != null)
            if (configuration.isDirty()) {
                configuration = (C) dao.persist(configuration);
            } else {
                final String message = "BasePersistentResource: applying persist() using a null configuration";
                if (LOGGER.isErrorEnabled())
                    LOGGER.error(message);
                throw new IOException(message);
            }
    }

    public void load() throws IOException {
        final Configuration config = dao.find(this.getId(), false);
        setConfiguration((C) config);
        if (configuration != null) {
            configuration.setDirty(false);
            
            // enforce ID equivalence
            if(!this.getId().equals(configuration.getId())){
                final String message;
                if (configuration.getId()!=null)
                    message = "BasePersistentResource: The flow ID should be equals to the Flow file name: " + // FIXME: FLOW?!? bad message here!
                    		"ID: "+configuration.getId()+" Flow file name: "+this.getId();
                    else
                        message = "BasePersistentResource: The flow ID should not be null. Flow file name: "+this.getId();

                if (LOGGER.isErrorEnabled())
                    LOGGER.error(message);
                throw new IOException(message);
            }
        } else {
            final String message = "BasePersistentResource: applying load() with a null configuration";
            if (LOGGER.isErrorEnabled())
                LOGGER.error(message);
            throw new IOException(message);
        }
    }

    public boolean remove() throws IOException {
        removed = dao.remove(configuration);
        return removed;
    }

    public void setConfiguration(C configuration) {
        this.configuration = configuration;
    }

    public void setDAO(DAO flowConfigurationDAO) {
        this.dao = flowConfigurationDAO;
    }

}
