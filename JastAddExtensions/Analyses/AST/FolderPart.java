
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.FileNotFoundException;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import sun.text.normalizer.UTF16;


  // load files from a folder
  public class FolderPart extends PathPart {
    // Declared in ClassPath.jrag at line 366
    private HashMap map = new HashMap();

    // Declared in ClassPath.jrag at line 367
    private File folder;

    // Declared in ClassPath.jrag at line 369

    public FolderPart(File folder) {
      this.folder = folder;
    }

    // Declared in ClassPath.jrag at line 373

    public boolean hasPackage(String name) {
      return filesInPackage(name) != null;
    }

    // Declared in ClassPath.jrag at line 377

    public boolean hasCompilationUnit(String canonicalName) {
      int index = canonicalName.lastIndexOf('.');
      String packageName = index == -1 ? "" : canonicalName.substring(0, index);
      String typeName = canonicalName.substring(index + 1, canonicalName.length());
      Collection c = filesInPackage(packageName);
      boolean result = c != null && c.contains(typeName + fileSuffix());
      return result;
    }

    // Declared in ClassPath.jrag at line 386
    
    private Collection filesInPackage(String packageName) {
      if(!map.containsKey(packageName)) {
        File f = new File(folder, packageName.replace('.', File.separatorChar));
        Collection c = Collections.EMPTY_LIST;
        if(f.exists() && f.isDirectory()) {
          String[] files = f.list();
          if(files.length > 0) {
            c = new HashSet();
            for(int i = 0; i < files.length; i++)
              c.add(files[i]);
          }
        }
        else
          c = null;
        map.put(packageName, c);
      }
      return (Collection)map.get(packageName);
    }

    // Declared in ClassPath.jrag at line 405
    
    public boolean selectCompilationUnit(String canonicalName) throws IOException {
      if(hasCompilationUnit(canonicalName)) {
        String fileName = canonicalName.replace('.', File.separatorChar);
        File classFile = new File(folder, fileName + fileSuffix());
        if(classFile.isFile()) {
          is = new FileInputStream(classFile);
          age = classFile.lastModified();
          pathName = classFile.getAbsolutePath();
          relativeName = fileName + fileSuffix();
          fullName = canonicalName;
          return true;
        }
      }
      return false;
    }


}
