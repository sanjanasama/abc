/* abc - The AspectBench Compiler
 * Copyright (C) 2005 Neil Ongkingco
 *
 * This compiler is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This compiler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this compiler, in the file LESSER-GPL;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package abc.om.weaving.aspectinfo;

import java.util.Hashtable;
import java.util.Set;

import polyglot.types.SemanticException;
import polyglot.util.Position;
import abc.aspectj.ast.ClassnamePatternExpr;
import abc.aspectj.visit.PCStructure;
import abc.om.weaving.matching.OMMatchingContext;
import abc.weaving.aspectinfo.Aspect;
import abc.weaving.aspectinfo.ClassnamePattern;
import abc.weaving.aspectinfo.Pointcut;
import abc.weaving.matching.MatchingContext;
import abc.weaving.residues.AlwaysMatch;
import abc.weaving.residues.NeverMatch;
import abc.weaving.residues.Residue;

/**
 * @author Neil Ongkingco
 *
 */
public class ThisAspectPointcut extends Pointcut {

    ClassnamePattern cp;
    
    public static ThisAspectPointcut construct(ClassnamePattern cp, Position pos) {
        return new ThisAspectPointcut(cp, pos);
    }
    
    private ThisAspectPointcut(ClassnamePattern cp, Position pos) {
        super(pos);
        this.cp = cp;
    }
    
    public String toString() {
        return "thisAspect(" + cp.toString() + ")";
    }

    public Residue matchesAt(MatchingContext mc) throws SemanticException {
        Residue ret = AlwaysMatch.v();
        OMMatchingContext omc = (OMMatchingContext) mc;
        Aspect currAspect = omc.getAspect();
        currAspect.getName();
        if (cp.matchesClass(currAspect.getInstanceClass().getSootClass())) {
            ret = AlwaysMatch.v();
        } else{ 
            ret = NeverMatch.v();
        }
        return ret;
    }

    protected Pointcut inline(Hashtable renameEnv, Hashtable typeEnv,
            Aspect context, int cflowdepth) {
        return this;
    }

    protected void registerSetupAdvice(Aspect context, Hashtable typeEnv) {}

    public void getFreeVars(Set result) {}

}
