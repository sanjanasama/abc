/**
 *  ServingXML
 *  
 *  Copyright (C) 2006  Daniel Parker
 *    daniel.parker@servingxml.com 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 * 
 **/

package com.servingxml.components.property;

import com.servingxml.ioc.components.ConfigurationContext;
import com.servingxml.util.ServingXmlException;
import com.servingxml.util.MessageFormatter;
import com.servingxml.util.ServingXmlMessages;

/**
 *  
 * @author Daniel A. Parker (daniel.parker@servingxml.com)
 */

public class SystemPropertyAssembler {

  private String name = null;
  private String value = null;
  
  public void setKey(String name) {
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(String value) {
    this.value = value;
  }
  
  public SystemProperty assemble(ConfigurationContext context) {

    if (name == null) {
      String message = MessageFormatter.getInstance().getMessage(ServingXmlMessages.COMPONENT_ATTRIBUTE_VALUE_INVALID,
        context.getElement().getTagName(),"name");
      throw new ServingXmlException(message);
    }

    if (value == null) {
      String message = MessageFormatter.getInstance().getMessage(ServingXmlMessages.COMPONENT_ATTRIBUTE_VALUE_INVALID,
        context.getElement().getTagName(),"value");
      throw new ServingXmlException(message);
    }

    return new SystemProperty(name,value);
  }
}
