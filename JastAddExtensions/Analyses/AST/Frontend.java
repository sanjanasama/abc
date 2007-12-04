
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.FileNotFoundException;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import sun.text.normalizer.UTF16;

  public class Frontend extends java.lang.Object {
    // Declared in FrontendMain.jrag at line 3
    protected Program program;

    // Declared in FrontendMain.jrag at line 5

    protected Frontend() {
      program = new Program();
    }

    // Declared in FrontendMain.jrag at line 9

    public boolean process(String[] args, BytecodeReader reader, JavaParser parser) {
      program.initBytecodeReader(reader);
      program.initJavaParser(parser);

      initOptions();
      processArgs(args);

      Collection files = program.files();

      if(program.hasOption("-version")) {
        printVersion();
        return false;
      }
      if(program.hasOption("-help") || files.isEmpty()) {
        printUsage();
        return false;
      }

      try {
        for(Iterator iter = files.iterator(); iter.hasNext(); ) {
          String name = (String)iter.next();
          program.addSourceFile(name);
        }

        for(Iterator iter = program.compilationUnitIterator(); iter.hasNext(); ) {
          CompilationUnit unit = (CompilationUnit)iter.next();
          if(unit.fromSource()) {
            Collection errors = unit.parseErrors();
            Collection warnings = new LinkedList();
            // compute static semantic errors when there are no parse errors or 
            // the recover from parse errors option is specified
            if(errors.isEmpty() || program.hasOption("-recover"))
              unit.errorCheck(errors, warnings);
            if(!errors.isEmpty()) {
              processErrors(errors, unit);
              return false;
            }
            else {
              processWarnings(warnings, unit);
              processNoErrors(unit);
            }
          }
        }
      } catch (Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
      }
      return true;
    }

    // Declared in FrontendMain.jrag at line 59

    protected void initOptions() {
      program.initOptions();
      program.addKeyOption("-version");
      program.addKeyOption("-print");
      program.addKeyOption("-g");
      program.addKeyOption("-g:none");
      program.addKeyOption("-g:lines,vars,source");
      program.addKeyOption("-nowarn");
      program.addKeyOption("-verbose");
      program.addKeyOption("-deprecation");
      program.addKeyValueOption("-classpath");
      program.addKeyValueOption("-sourcepath");
      program.addKeyValueOption("-bootclasspath");
      program.addKeyValueOption("-extdirs");
      program.addKeyValueOption("-d");
      program.addKeyValueOption("-encoding");
      program.addKeyValueOption("-source");
      program.addKeyValueOption("-target");
      program.addKeyOption("-help");
      program.addKeyOption("-O");
      program.addKeyOption("-J-Xmx128M");
      program.addKeyOption("-recover");
    }

    // Declared in FrontendMain.jrag at line 82
    protected void processArgs(String[] args) {
      program.addOptions(args);
    }

    // Declared in FrontendMain.jrag at line 86

    protected void processErrors(Collection errors, CompilationUnit unit) {
      System.out.println("Errors:");
      for(Iterator iter2 = errors.iterator(); iter2.hasNext(); ) {
        System.out.println(iter2.next());
      }
    }

    // Declared in FrontendMain.jrag at line 92
    protected void processWarnings(Collection warnings, CompilationUnit unit) {
      for(Iterator iter2 = warnings.iterator(); iter2.hasNext(); ) {
        System.out.println(iter2.next());
      }
    }

    // Declared in FrontendMain.jrag at line 97
    protected void processNoErrors(CompilationUnit unit) {
    }

    // Declared in FrontendMain.jrag at line 100

    protected void printUsage() {
      printVersion();
      System.out.println(
          "\n" + name() + "\n\n" +
          "Usage: java " + name() + " <options> <source files>\n" +
          "  -verbose                  Output messages about what the compiler is doing\n" +
          "  -classpath <path>         Specify where to find user class files\n" +
          "  -sourcepath <path>        Specify where to find input source files\n" + 
          "  -bootclasspath <path>     Override location of bootstrap class files\n" + 
          "  -extdirs <dirs>           Override location of installed extensions\n" +
          "  -d <directory>            Specify where to place generated class files\n" +
          "  -help                     Print a synopsis of standard options\n" +
          "  -version                  Print version information\n"
          );
    }

    // Declared in FrontendMain.jrag at line 116

    protected void printVersion() {
      System.out.println(name() + " " + url() + " Version " + version());
    }

    // Declared in FrontendMain.jrag at line 120

    protected String name() {
      return "Java1.4Frontend";
    }

    // Declared in FrontendMain.jrag at line 123
    protected String url() {
      return "(http://jastadd.cs.lth.se)";
    }

    // Declared in FrontendMain.jrag at line 127

    protected String version() {
      return "R20070504";
    }


}
