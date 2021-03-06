/*
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by the
 * Apache Software Foundation (http://www.apache.org/)."
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 * "Apache JMeter" must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 * "Apache JMeter", nor may "Apache" appear in their name, without
 * prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package org.apache.jmeter.protocol.ldap.sampler;

import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.config.LoginConfig;
import org.apache.jmeter.protocol.ldap.config.LdapConfig;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.log.Hierarchy;
import org.apache.log.Logger;


/****************************************
 * Title: JMeter Description: Copyright: Copyright (c) 2000 Company: Apache
 *
 *@author    T.Elanjchezhiyan(chezhiyan@siptech.co.in)
 *@created   Apr 29 2003 11:00 AM
 *@company   Sip Technologies and Exports Ltd.
 *@version   1.0
 ***************************************/

/*****************************************************
 * Ldap Sampler class is main class for the LDAP test.
 * This will control all the test available in the LDAP Test.
 *****************************************************/

public class LDAPSampler extends AbstractSampler  {

    transient private static Logger log = Hierarchy.getDefaultHierarchy()
    .getLoggerFor("jmeter.protocol.ldap");

    public final static String SERVERNAME = "servername";
    public final static String PORT = "port";
    public final static String ROOTDN = "rootdn";
    public final static String TEST = "test";
    public final static String ADD = "add";
    public final static String MODIFY = "modify";
    public final static String DELETE = "delete";             
    public final static String SEARCHBASE = "search";
    public final static String SEARCHFILTER = "searchfilter";
    public final static String USER_DEFINED = "user_defined";
    public final static String ARGUMENTS = "arguments";
    public final static String BASE_ENTRY_DN = "base_entry_dn";
    
    //For In build test case using this counter 
    //create the new entry in the server
    public static int counter=0;
    /************************************************************
     *  !ToDo (Constructor description)
     ***********************************************************/
    public LDAPSampler()
    {
    }


    /************************************************************
     *  
     ***********************************************************/

    public void addCustomTestElement(TestElement element)
    {
        if(element instanceof LdapConfig || element instanceof LoginConfig)
        {
            mergeIn(element);
        }
    }

    /************************************************************
     *  Gets the username attribute of the LDAP object
     *
     *@return    The username
     *************************************************************/

    public String getUsername()
    {
        return getPropertyAsString(ConfigTestElement.USERNAME);
    }
	
    /************************************************************
     *  Gets the password attribute of the LDAP object
     *
     *@return    The password
     *************************************************************/

    public String getPassword()
    {
        return getPropertyAsString(ConfigTestElement.PASSWORD);
    }

    /************************************************************
     *  Sets the Servername attribute of the ServerConfig object
     *
     *@param  servername  The new servername value
     ***********************************************************/
    public void setServername(String servername) {
        setProperty(new StringProperty(SERVERNAME, servername));
    }

    /************************************************************
     *  Sets the Port attribute of the ServerConfig object
     *
     *@param  port  The new Port value
     ***********************************************************/
    public void setPort(String port) {
        setProperty(new StringProperty(PORT, port));
    }


    /************************************************************
     *  Gets the servername attribute of the LDAPSampler object
     *
     *@return    The Servername value
     ***********************************************************/

    public String getServername()
    {
        return getPropertyAsString(SERVERNAME);
    }
	
    /************************************************************
     *  Gets the Port attribute of the LDAPSampler object
     *
     *@return    The Port value
     *************************************************************/

    public String getPort()
    {
        return getPropertyAsString(PORT);
    }
	
    /************************************************************
     *  Sets the Rootdn attribute of the LDAPSampler object
     *
     *@param  rootdn  The new rootdn value
     ***********************************************************/
    public void setRootdn(String newRootdn)
    {
        this.setProperty(ROOTDN,newRootdn);
    }
    /************************************************************
     *  Gets the Rootdn attribute of the LDAPSampler object
     *
     *@return    The Rootdn value
     ***********************************************************/
    public String getRootdn() {
        return getPropertyAsString(ROOTDN);
    }

    /************************************************************
     *  Sets the Test attribute of the LdapConfig object
     *
     *@param  Test  The new test value(Add,Modify,Delete  and search)
     ***********************************************************/
    public void setTest(String newTest) {
        this.setProperty(TEST,newTest);
    }
    
    /************************************************************
     *  Gets the test attribute of the LDAPSampler object
     *
     *@return    The test value (Add,Modify,Delete  and search)
     ***********************************************************/
    public String getTest()
    {
        return getPropertyAsString(TEST);
    }

    /************************************************************
     *  Sets the UserDefinedTest attribute of the LDAPSampler object
     *
     *@param  value  The  new UserDefinedTest value 
     ***********************************************************/
    public void setUserDefinedTest(boolean value)
    {
        setProperty(new BooleanProperty(USER_DEFINED, value));
    }

    /************************************************************
     *  Gets the UserDefinedTest attribute of the LDAPSampler object
     *
     *@return    The test value true or false
     *           if true it will do the UserDefinedTest else our own  
     *           inbuild test case
     ***********************************************************/
    public boolean getUserDefinedTest() {
        return getPropertyAsBoolean(USER_DEFINED);
    }


    /************************************************************
     *  Sets the Base Entry DN attribute of the LDAPSampler object
     *
     *@param  value  The  new Base entry DN value 
     ***********************************************************/
    public void setBaseEntryDN(String  newbaseentry) {
        setProperty(new StringProperty(BASE_ENTRY_DN, newbaseentry));
    }

    /************************************************************
     *  Gets the BaseEntryDN attribute of the LDAPSampler object
     *
     *@return    The  Base entry DN value
     ***********************************************************/
    public String getBaseEntryDN() {
        return getPropertyAsString(BASE_ENTRY_DN);
    }

    /************************************************************
     *  Sets the Arguments attribute of the LdapConfig object
     *  This will collect values from the table for user defined test
     *  case 
     *@param  value  The  arguments 
     ***********************************************************/
    public void setArguments(Arguments value)
    {
        setProperty(new TestElementProperty(ARGUMENTS, value));
    }

    /************************************************************
     *  Gets the Arguments attribute of the LdapConfig object
     *
     *@return    The  arguments
     *           user defined test  case
     ***********************************************************/
    public Arguments getArguments()
    {
        return (Arguments) getProperty(ARGUMENTS).getObjectValue();
    }
	

    /************************************************************
     * Collect all the value from the table (Arguments), using  this
     * create the basicAttributes 
     * This will create the Basic Attributes for the User defined 
     * TestCase  for Add Test
     *@return    The  BasicAttributes
     ***********************************************************/
    public BasicAttributes getUserAttributes() {
        boolean add =true;
        BasicAttribute basicattribute = new BasicAttribute("objectclass");
        basicattribute.add("top");
        basicattribute.add("person");
        basicattribute.add("organizationalPerson");
        basicattribute.add("inetOrgPerson");
        BasicAttributes attrs = new BasicAttributes(true);
        attrs.put(basicattribute);
        BasicAttribute attr;
        PropertyIterator iter = getArguments().iterator();
         
        while (iter.hasNext()) {
            Argument item = (Argument) iter.next().getObjectValue();
            attr = getBasicAttribute( item.getName(),item.getValue());
            attrs.put(attr);
        }
        return attrs;
    }


    /************************************************************
     * Collect all the value from the table (Arguments), using  this
     * create the basicAttributes 
     * This will create the Basic Attributes for the User defined 
     * TestCase for Modify test
     *@return    The  BasicAttributes
     ***********************************************************/
    public ModificationItem[] getUserModAttributes() {
        ModificationItem[] mods =new  ModificationItem[getArguments().getArguments().size()];
        boolean add =true;
        BasicAttributes attrs = new BasicAttributes(true);
        BasicAttribute attr;
        PropertyIterator iter = getArguments().iterator();
        int count =0;
        while (iter.hasNext()) {
            Argument item = (Argument) iter.next().getObjectValue();
            attr = getBasicAttribute( item.getName(),item.getValue());
            mods[count ] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
            count=+1;
        }
        return mods;
    }


    /************************************************************
     * This will create the Basic Attributes for the Inbuilt  
     * TestCase  for Modify test
     *@return    The  BasicAttributes
     ***********************************************************/
    public ModificationItem[] getModificationItem() {
        ModificationItem[] mods = new ModificationItem[2];
        // replace (update)  attribute
        Attribute mod0 = new BasicAttribute("userpassword",
                                            "secret");
        // add mobile phone number attribute
        Attribute mod1 = new BasicAttribute("mobile", 
                                            "123-456-1234");

        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
        mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);

        return mods;
    }

    /************************************************************
     * This will create the Basic Attributes for the In build  
     * TestCase for Add Test
     *@return    The  BasicAttributes
     ***********************************************************/
    public  BasicAttributes getBasicAttributes() {
        BasicAttributes basicattributes = new BasicAttributes();
        BasicAttribute basicattribute = new BasicAttribute("objectclass");
        basicattribute.add("top");
        basicattribute.add("person");
        basicattribute.add("organizationalPerson");
        basicattribute.add("inetOrgPerson");
        basicattributes.put(basicattribute);
        String s1 = "User";
        String s3 = "Test";
        String s5 = "user";
        String s6 = "test";
        counter+=1;                    
        basicattributes.put(new BasicAttribute("givenname", s1));
        basicattributes.put(new BasicAttribute("sn", s3));
        basicattributes.put(new BasicAttribute("cn","TestUser"+counter));
        basicattributes.put(new BasicAttribute("uid", s5));
        basicattributes.put(new BasicAttribute("userpassword", s6));
        setProperty(new StringProperty(ADD,"cn=TestUser"+counter));
        return basicattributes;
    }

    /************************************************************
     * This will create the Basic Attribute for the give name 
     * value pair
     *@return    The  BasicAttribute
     ***********************************************************/
    public BasicAttribute getBasicAttribute(String name,String value)
    {
        BasicAttribute attr = new BasicAttribute(name,value);
        return attr;
    }

    /**
     * Returns a formatted string label describing this sampler
     * Example output:
     *
     * @return a formatted string label describing this sampler
     */
    public String getLabel() {
        return ("ldap://" + this.getServername() +":"+getPort()+ "/" + this.getRootdn());
    }


    /************************************************************
     * This will do the add test  for the User defined 
     * TestCase  as well as inbuilt test case
     *@return    executed time for the give test case
     ***********************************************************/
    public long addTest(LdapClient ldap)throws NoPermissionException,NamingException{
        long start =0L;
        long end =0L;
        if (getPropertyAsBoolean(USER_DEFINED)){
            start = System.currentTimeMillis();
            ldap.createTest(getUserAttributes(), getPropertyAsString(BASE_ENTRY_DN));
            end = System.currentTimeMillis();
        } else{
            start = System.currentTimeMillis();
            ldap.createTest(getBasicAttributes(), getPropertyAsString(ADD));
            end = System.currentTimeMillis();
            ldap.deleteTest(getPropertyAsString(ADD));
        }
        return (end-start);
    }
         

    /************************************************************
     * This will do the delete test  for the User defined 
     * TestCase  as well as inbuilt test case
     *@return    executed time for the give test case
     ***********************************************************/
    public long deleteTest(LdapClient ldap)throws NoPermissionException,NamingException {
        long start =0L;
        long end =0L;
        if (! getPropertyAsBoolean(USER_DEFINED)){
            ldap.createTest(getBasicAttributes(), getPropertyAsString(ADD));
            setProperty(new StringProperty(DELETE,getPropertyAsString(ADD)));
        }
        start = System.currentTimeMillis();
        ldap.deleteTest(getPropertyAsString(DELETE));
        end = System.currentTimeMillis();
        return (end-start);
    }

    /************************************************************
     * This will do the search test  for the User defined 
     * TestCase  as well as inbuilt test case
     *@return    executed time for the give test case
     ***********************************************************/
    public long searchTest(LdapClient ldap)throws NoPermissionException,NamingException {
        long start =0L;
        long end =0L;

        if (! getPropertyAsBoolean(USER_DEFINED)){
            ldap.createTest(getBasicAttributes(), getPropertyAsString(ADD));
            setProperty(new StringProperty(SEARCHBASE,getPropertyAsString(ADD)));
            setProperty(new StringProperty(SEARCHFILTER,getPropertyAsString(ADD)));
        }
        start = System.currentTimeMillis();
        ldap.searchTest(getPropertyAsString(SEARCHBASE),getPropertyAsString(SEARCHFILTER));
        end = System.currentTimeMillis();
        if (! getPropertyAsBoolean(USER_DEFINED)){
            ldap.deleteTest(getPropertyAsString(ADD));
        }
        return (end-start);
    }

    /************************************************************
     * This will do the search test  for the User defined 
     * TestCase  as well as inbuilt test case
     *@return    executed time for the give test case
     ***********************************************************/
    public long modifyTest(LdapClient ldap)throws NoPermissionException,NamingException{
        long start =0L;
        long end =0L;
        if (getPropertyAsBoolean(USER_DEFINED)){
            start = System.currentTimeMillis();
            ldap.modifyTest(getUserModAttributes(), getPropertyAsString(BASE_ENTRY_DN));
            end = System.currentTimeMillis();
        } else{
            ldap.createTest(getBasicAttributes(), getPropertyAsString(ADD));
            setProperty(new StringProperty(MODIFY,getPropertyAsString(ADD)));
            start = System.currentTimeMillis();
            ldap.modifyTest(getModificationItem(), getPropertyAsString(MODIFY));
            end = System.currentTimeMillis();
            ldap.deleteTest(getPropertyAsString(ADD));
        }
        return (end-start);
    }
         
    /************************************************************
     *  !ToDo (Method description)
     *
     *@param  e  !ToDo (Parameter description)
     *@return    !ToDo (Return description)
     ***********************************************************/
    public SampleResult sample(Entry e)
    {
        SampleResult res = new SampleResult();
        boolean isSuccessful = false;
        res.setSampleLabel(getLabel()+": "+getPropertyAsString(TEST));
        long time=0L;
        LdapClient ldap = new LdapClient();
        try
        {
            ldap.connect(getServername(),getPort(),getRootdn(),getUsername(),getPassword());
            if (getPropertyAsString(TEST).equals("add")) {
                time =addTest(ldap);
            }else if (getPropertyAsString(TEST).equals("delete")) {
                time = deleteTest(ldap);
            }else if (getPropertyAsString(TEST).equals("modify")) {
                time = modifyTest(ldap);
            }else if (getPropertyAsString(TEST).equals("search")) {
                time = searchTest(ldap);
            }
            res.setResponseData("success full".getBytes());
            isSuccessful = true;
            ldap.disconnect();
        }
        catch (Exception ex) {
            res.setResponseData(ex.toString().getBytes());
            log.error("Ldap client - ",ex);
            ldap.disconnect();
            isSuccessful = false;
            time = 0L;
        }
        // Calculate response time
        res.setTime(time);
        // Set if we were successful or not
        res.setSuccessful(isSuccessful);
        return res;
    }
}