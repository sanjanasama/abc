package examples.adapter.java;

/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This file is part of the design patterns project at UBC
 *
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * either http://www.mozilla.org/MPL/ or http://aspectj.org/MPL/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is ca.ubc.cs.spl.patterns.
 *
 * Contributor(s):   
 */

/**
 * Represents an <i>Object Adapter</i>. Implements the <i>Target</i> interface
 * and stores a private variable of type <i>Adaptee</i> (here: <code>Screen
 * </code>) to which it forwards appropriate method calls. <p>
 * It is not possible to use a class adapter in Java as it requires multiple
 * inheritance. 
 *
 * @author Jan Hannemann
 * @author Gregor Kiczales
 * @version 1.0, 05/13/02
 *
 * @see Printer
 * @see Screen
 */
 
public class PrinterScreenAdapter implements Printer { 
    
    /**
     * the adaptee to forward appropriate messages to.
     */
     
	private Screen screen = new Screen();


    /**
     * Creates a new Adapter for a given Screen.
     *
     * @param screen the screen to adapt
     */

    public PrinterScreenAdapter(Screen screen) {
        this.screen = screen;
    }
	
	/**
	 * Implements the <i>Target</i> interface. 
     *
     * @param s the string to print
     * @see Printer#print(String)
     * @see Screen#printToStdOut(String)
	 */
	
	public void print(String s) {
		screen.printToStdOut(s);
	}
}