package abc.weaving.aspectinfo;

import soot.*;

import abc.weaving.matching.*;
import abc.weaving.residues.Residue;

/** A joinpoint shadow that applies at "preinitialization"
 *  @author Ganesh Sittampalam
 *  @date 29-Apr-04
 */
public class PreinitializationShadowType extends AbstractShadowType {
    public boolean couldMatch(MethodPosition pos) {
	return pos instanceof WholeMethodPosition;
    }

    public void addAdviceApplication(MethodAdviceList mal,
				     AdviceDecl ad,
				     Residue residue,
				     MethodPosition pos) {
	if(debugResidues) System.out.println("pos: "+pos.getClass());
	mal.preinitializationAdvice.add
	    (new PreinitializationAdviceApplication(ad,residue));
    }
}
