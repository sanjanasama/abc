/* abc - The AspectBench Compiler
 * Copyright (C) 2004 Aske Simon Christensen
 * Copyright (C) 2004 Ganesh Sittampalam
 *
 * This compiler is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this compiler, in the file LESSER-GPL;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package abc.weaving.aspectinfo;

import polyglot.util.Position;

import soot.*;
import abc.weaving.aspectinfo.AdviceDecl;
import abc.weaving.matching.ShadowMatch;
import abc.weaving.matching.WeavingEnv;
import abc.weaving.residues.Residue;
import abc.weaving.residues.AlwaysMatch;
import abc.weaving.weaver.WeavingContext;
import abc.weaving.weaver.AdviceWeavingContext;

/** Advice specification for after throwing advice with exception variable binding.
 *  @author Aske Simon Christensen
 *  @author Ganesh Sittampalam
 */
public class AfterThrowingArgAdvice extends AfterThrowingAdvice {
    private Formal formal;

    public AfterThrowingArgAdvice(Formal formal, Position pos) {
	super(pos);
	this.formal = formal;
    }

    public Formal getFormal() {
	return formal;
    }

    public String toString() {
	return "after throwing arg";
    }

    // We inherit the matchesAt method from AfterThrowingAdvice,
    // because the binding of the formal is best done as a special
    // case in the weaver for after throwing advice

    public RefType getCatchType() {
	return (RefType) (formal.getType().getSootType());
    }

    public void bindException(WeavingContext wc,AbstractAdviceDecl ad,Local exception) {
	((AdviceWeavingContext) wc).arglist.setElementAt
	    (exception,((AdviceDecl) ad).getFormalIndex(formal.getName()));
    }
}
