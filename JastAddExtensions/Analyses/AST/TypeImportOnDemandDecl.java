
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.FileNotFoundException;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import sun.text.normalizer.UTF16;

public class TypeImportOnDemandDecl extends ImportDecl implements Cloneable {
    public void flushCache() {
        super.flushCache();
        importedTypes_String_values = null;
    }
    public Object clone() throws CloneNotSupportedException {
        TypeImportOnDemandDecl node = (TypeImportOnDemandDecl)super.clone();
        node.importedTypes_String_values = null;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
    public ASTNode copy() {
      try {
          TypeImportOnDemandDecl node = (TypeImportOnDemandDecl)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
    public ASTNode fullCopy() {
        TypeImportOnDemandDecl res = (TypeImportOnDemandDecl)copy();
        for(int i = 0; i < getNumChild(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in NameCheck.jrag at line 18

  public void nameCheck() {
    if(getAccess().lastAccess().isTypeAccess() && !getAccess().type().typeName().equals(typeName()))
      error("On demand type import " + typeName() + ".* is not the canonical name of type " + getAccess().type().typeName());
  }

    // Declared in PrettyPrint.jadd at line 59


  public void toString(StringBuffer s) {
    s.append("import ");
    getAccess().toString(s);
    s.append(".*;\n");
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 9

    public TypeImportOnDemandDecl() {
        super();

        setChild(null, 0);

    }

    // Declared in java.ast at line 11


    // Declared in java.ast line 9
    public TypeImportOnDemandDecl(Access p0) {
        setChild(p0, 0);
    }

    // Declared in java.ast at line 15


  protected int numChildren() {
    return 1;
  }

    // Declared in java.ast at line 18

  public boolean mayHaveRewrite() { return false; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 7
    public void setAccess(Access node) {
        setChild(node, 0);
    }

    // Declared in java.ast at line 5

    public Access getAccess() {
        return (Access)getChild(0);
    }

    // Declared in java.ast at line 9


    public Access getAccessNoTransform() {
        return (Access)getChildNoTransform(0);
    }

    // Declared in LookupType.jrag at line 438
    public SimpleSet importedTypes(String name) {
        Object _parameters = name;
if(importedTypes_String_values == null) importedTypes_String_values = new java.util.HashMap(4);
        if(importedTypes_String_values.containsKey(_parameters))
            return (SimpleSet)importedTypes_String_values.get(_parameters);
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        SimpleSet importedTypes_String_value = importedTypes_compute(name);
        if(isFinal && num == boundariesCrossed)
            importedTypes_String_values.put(_parameters, importedTypes_String_value);
        return importedTypes_String_value;
    }

    private SimpleSet importedTypes_compute(String name)  {
    SimpleSet set = SimpleSet.emptySet;
    if(getAccess() instanceof PackageAccess) {
      String packageName = ((PackageAccess)getAccess()).getPackage();
      TypeDecl typeDecl = lookupType(packageName, name);
      if(typeDecl != null && typeDecl.accessibleFromPackage(packageName()) &&
         typeDecl.typeName().equals(packageName + "." + name)) // canonical names match
        set = set.add(typeDecl);
    }
    else {
      for(Iterator iter = getAccess().type().memberTypes(name).iterator(); iter.hasNext(); ) {
        TypeDecl decl = (TypeDecl)iter.next();
        if(decl.accessibleFromPackage(packageName()) &&
           decl.typeName().equals(getAccess().typeName() + "." + name)) // canonical names match
          set = set.add(decl);
      }
    }
    return set;
  }

    // Declared in LookupType.jrag at line 461
    public boolean isOnDemand() {
        boolean isOnDemand_value = isOnDemand_compute();
        return isOnDemand_value;
    }

    private boolean isOnDemand_compute() {  return  true;  }

    // Declared in LookupType.jrag at line 457
    public TypeDecl lookupType(String packageName, String typeName) {
        TypeDecl lookupType_String_String_value = getParent().Define_TypeDecl_lookupType(this, null, packageName, typeName);
        return lookupType_String_String_value;
    }

    // Declared in SyntacticClassification.jrag at line 97
    public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
        if(caller == getAccessNoTransform()) {
            return  NameType.PACKAGE_OR_TYPE_NAME;
        }
        return getParent().Define_NameType_nameType(this, caller);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
