/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://code.google.com/p/geobatch/
 *  Copyright (C) 2007-2008-2009 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.actions.freemarker;

import it.geosolutions.geobatch.catalog.Configuration;
import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;

import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamInclude;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 
 * @author Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 * 
 */
@XStreamAlias("FreeMarker")
@XStreamInclude(FreeMarkerConfiguration.class)
public class FreeMarkerConfiguration extends ActionConfiguration implements Configuration {
    
    /**
     * Used as key into the map for the incoming event.
     * ${event.FILE}
     * 
     * TODO: changing adapter
     * It is concat using the integer representing
     * the position into the event queue.
     * To use it into a template you have to use:
     * ${event.FILE_0} -> first file into the queue
     * ${event.FILE_(N-1)} -> (N)th file into the queue
     */
    @XStreamOmitField
    protected static final String FILE_EVENT_KEY="FILE"; 

    public FreeMarkerConfiguration(String id, String name, String description) {
        super(id, name, description);
    }

    /**
     * 
     * @param id
     * @param name
     * @param description
     * @param dirty
     * @param in
     *            the input template file
     * @param out
     *            the output filtered (resulting) file
     * @param r
     *            the root data model to use as root
     */
    public FreeMarkerConfiguration(String id, String name, String description, boolean dirty,
            String in, String out, Map<String, Object> r) {
        super(id, name, description);
        input = in;
        output = out;
        root = r;
    }

    /**
     * 
     * @param in
     *            the input template file
     * @param out
     *            the output filtered (resulting) file
     * @param r
     *            the root data model to use as root
     */
//    public FreeMarkerConfiguration(String in, String out, Map<String, Object> r) {
//        super();
//        input = in;
//        output = out;
//        root = r;
//    }

    // path where to find the template
    @XStreamAlias("input")
    private String input;

    // path where to write
    @XStreamAlias("output")
    private String output;

    // Create a data-model
    @XStreamAlias("root")
    private Map<String, Object> root = null;

    public void setInput(String s) {
        input = s;
    }

    public void setOutput(String s) {
        output = s;
    }

    public void setRoot(Map<String, Object> m) {
        root = m;
    }

    /**
     * @return the substitution data structure
     * @note this object can be null
     */
    public Map<String, Object> getRoot() {
        return root;
    }

    /**
     * return the template path
     * 
     * @return the template path
     * @note this object can be null anyway the templateIn should be always present into the
     *       configuration.
     */
    public final String getInput() {
        return input;
    }

    /**
     * return the template path
     * 
     * @return the template path
     * @note this object can be null anyway the templateOut should be always present into the
     *       configuration.
     */
    public final String getOutput() {
        return output;
    }

}