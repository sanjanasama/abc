
package abc.aspectj.visit;

import polyglot.frontend.*;
import polyglot.ast.*;
import polyglot.visit.*;
import polyglot.types.*;

import abc.aspectj.ast.*;
import abc.aspectj.ExtensionInfo;
import abc.weaving.aspectinfo.GlobalAspectInfo;

import java.util.*;

/** Compute the precedence relation between aspects from all
 *  <code>declare precedence</code> declarations in the program.
 *  @exception SemanticException if any aspect is matched by more than one pattern on the same list.
 */
public class ComputePrecedenceRelation extends ErrorHandlingVisitor {
    ExtensionInfo ext;

    public ComputePrecedenceRelation(Job job, TypeSystem ts, NodeFactory nf,
				     ExtensionInfo ext) {
	super(job, ts, nf);
	this.ext = ext;
    }

    protected NodeVisitor enterCall(Node n) throws SemanticException {
	if (n instanceof DeclarePrecedence) {
	    DeclarePrecedence dpr = (DeclarePrecedence)n;
	    List pats = dpr.pats();

	    // The aspects we have passed on this list
	    Set passed = new HashSet();

	    // Iterate through the list of patterns
	    Iterator pati = pats.iterator();
	    while (pati.hasNext()) {
		ClassnamePatternExpr pat = (ClassnamePatternExpr)pati.next();

		// The aspects that match the current pattern
		Set current = new HashSet();

		// Handle all aspects matched by the pattern
		Iterator ai = ext.aspect_names.iterator();
		while (ai.hasNext()) {
		    String a = (String)ai.next();

		    if (!ext.prec_rel.containsKey(a)) {
			ext.prec_rel.put(a, new HashSet());
		    }

		    LinkedList worklist = new LinkedList();
		    worklist.add(ext.hierarchy.getClass(ts.typeForName(a)));
		    Set seen = new HashSet();
		    boolean found = false;
		    while (!worklist.isEmpty()) {
			PCNode cl = (PCNode)worklist.removeFirst();
			if (!seen.contains(cl)) {
			    seen.add(cl);
			    /* FIXME: Is precedence really not inherited?
			    Iterator pi = cl.getParents().iterator();
			    while (pi.hasNext()) {
				PCNode p = (PCNode)pi.next();
				worklist.addLast(p);
			    }
			    */
			    if (pat.matches(PatternMatcher.v(), cl)) {
				// It is an error if an aspect is matched twice on the same list
				if (passed.contains(a)) {
				    throw new SemanticException("Aspect "+a+
								" is matched by more than one pattern on the precedence list",
								dpr.position());
				}
				// Mark this aspect as being preceded by all passed aspects
				Iterator pai = passed.iterator();
				while (pai.hasNext()) {
				    String pa = (String)pai.next();
				    ((Set)ext.prec_rel.get(pa)).add(a);
				    if (abc.main.Debug.v().precedenceRelation) {
					System.err.println("aspect "+pa+
							   " has precedence over aspect "+a);
				    }
				}
				// Add it to the current set
				current.add(a);
			    }
			}
		    }
		}
			    
		// All aspects matched by this pattern are now passed
		passed.addAll(current);
	    }

	}
	return this;
    }
}
