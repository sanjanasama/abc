
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.FileNotFoundException;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import sun.text.normalizer.UTF16;


  public class BytecodeParser extends java.lang.Object implements  BytecodeReader {
    // Declared in BytecodeReader.jrag at line 4
    public CompilationUnit read(InputStream is, String fullName, Program p) throws FileNotFoundException, IOException {
      return new BytecodeParser(is, fullName).parse(null, null, p);
    }

    // Declared in BytecodeReader.jrag at line 8

    public static final boolean VERBOSE = false;

    // Declared in BytecodeReader.jrag at line 10

    private DataInputStream is;

    // Declared in BytecodeReader.jrag at line 11
    public CONSTANT_Class_Info classInfo;

    // Declared in BytecodeReader.jrag at line 12
    public CONSTANT_Class_Info outerClassInfo;

    // Declared in BytecodeReader.jrag at line 13
    public String name;

    // Declared in BytecodeReader.jrag at line 15

    public BytecodeParser(byte[] buffer, int size, String name) {
      //this.is = new DataInputStream(new DummyInputStream(buffer, size));
      this.is = new DataInputStream(new ByteArrayInputStream(buffer, 0, size));
      this.name = name;
    }

    // Declared in BytecodeReader.jrag at line 20
    public BytecodeParser(InputStream in, String name) {
      //this.is = new DataInputStream(new DummyInputStream(buffer, size));
      this.is = new DataInputStream(new DummyInputStream(in));
      this.name = name;
    }

    // Declared in BytecodeReader.jrag at line 26

    public BytecodeParser() {
      this("");
    }

    // Declared in BytecodeReader.jrag at line 29
    public BytecodeParser(String name) {
      if (!name.endsWith(".class")) {
        //name = name.replaceAll("\\.", "/") + ".class";
        name = name.replace('.', '/') + ".class";
      }
      this.name = name;
    }

    // Declared in BytecodeReader.jrag at line 37

    private static class DummyInputStream extends InputStream {
      byte[] bytes;
      int pos;
      int size;
      public DummyInputStream(byte[] buffer, int size) {
        bytes = buffer;
        this.size = size;
      }
      public DummyInputStream(InputStream is) {
        bytes = new byte[1024];
        int index = 0;
        size = 1024;
        try {
          int status;
          do {
            status = is.read(bytes, index, size - index);
            if(status != -1) {
              index += status;
              if(index == size) {
                byte[] newBytes = new byte[size*2];
                System.arraycopy(bytes, 0, newBytes, 0, size);
                bytes = newBytes;
                size *= 2;
              }
            }
          } while (status != -1);
        } catch (IOException e) {
          System.err.println("Something went wrong trying to read " + is);
          //System.exit(1);
        }
        size = index;
        pos = 0;
      }

      public int available() {
        return size - pos;
      }

      public void close() {
      }

      public void mark(int readlimit) {
      }

      public boolean markSupported() {
        return false;
      }

      public int read(byte[] b) {
        int actualLength = Math.min(b.length, size-pos);
        System.arraycopy(bytes, pos, b, 0, actualLength);
        pos += actualLength;
        return actualLength;
      }

      public int read(byte[] b, int offset, int length) {
        int actualLength = Math.min(length, size-pos);
        System.arraycopy(bytes, pos, b, offset, actualLength);
        pos += actualLength;
        return actualLength;
      }

      public void reset() {
      }

      public long skip(long n) {
        if(size == pos)
          return -1;
        long skipSize = Math.min(n, size-pos);
        pos += skipSize;
        return skipSize;
      }

      public int read() throws IOException {
        if(pos < size) {
          int i = bytes[pos++];
          if(i < 0)
            i = 256 + i;
          return i;
        }
        return -1;
      }
    }

    // Declared in BytecodeReader.jrag at line 121

    public int next() {
      try {
        return is.read();
      } catch (IOException e) {
        System.exit(1);
      }
      return -1;
    }

    // Declared in BytecodeReader.jrag at line 130

    public int u1() {
      try {
        return is.readUnsignedByte();
      } catch (IOException e) {
        System.exit(1);
      }
      return -1;
    }

    // Declared in BytecodeReader.jrag at line 139

    public int u2() {
      try {
        return is.readUnsignedShort();
      } catch (IOException e) {
        System.exit(1);
      }
      return -1;
    }

    // Declared in BytecodeReader.jrag at line 148

    public int u4() {
      try {
        return is.readInt();
      } catch (IOException e) {
        System.exit(1);
      }
      return -1;
    }

    // Declared in BytecodeReader.jrag at line 157

    public int readInt() {
      try {
        return is.readInt();
      } catch (IOException e) {
        System.exit(1);
      }
      return -1;
    }

    // Declared in BytecodeReader.jrag at line 166

    public float readFloat() {
      try {
        return is.readFloat();
      } catch (IOException e) {
        System.exit(1);
      }
      return -1;
    }

    // Declared in BytecodeReader.jrag at line 175

    public long readLong() {
      try {
        return is.readLong();
      } catch (IOException e) {
        System.exit(1);
      }
      return -1;
    }

    // Declared in BytecodeReader.jrag at line 184

    public double readDouble() {
      try {
        return is.readDouble();
      } catch (IOException e) {
        System.exit(1);
      }
      return -1;
    }

    // Declared in BytecodeReader.jrag at line 193

    public String readUTF() {
      try {
        return is.readUTF();
      } catch (IOException e) {
        System.exit(1);
      }
      return "";
    }

    // Declared in BytecodeReader.jrag at line 202

    public void skip(int length) {
      try {
        is.skip(length);
      } catch (IOException e) {
        System.exit(1);
      }
    }

    // Declared in BytecodeReader.jrag at line 210

    public void error(String s) {
      throw new RuntimeException(s);
    }

    // Declared in BytecodeReader.jrag at line 214

    public void print(String s) {
      //System.out.print(s);
    }

    // Declared in BytecodeReader.jrag at line 218

    public void println(String s) {
      print(s + "\n");
    }

    // Declared in BytecodeReader.jrag at line 222

    public void println() {
      print("\n");
    }

    // Declared in BytecodeReader.jrag at line 226

    public CompilationUnit parse(TypeDecl outerTypeDecl, CONSTANT_Class_Info outerClassInfo, Program classPath) 
      throws FileNotFoundException, IOException {
        //InputStream file = ClassLoader.getSystemResourceAsStream(name);

        if(is == null) {
          FileInputStream file = new FileInputStream("/home/torbjorn/sandbox/jdk/" + name);
          //System.err.println("/home/torbjorn/sandbox/jdk/" + name);

          if(file == null) {
            throw new FileNotFoundException(name);
          }

          // // Does not work without DummyInputStream. Why?
          //is = new DataInputStream(new DummyInputStream(new BufferedInputStream(file)));
          is = new DataInputStream(new BufferedInputStream(file));
        }
        if(BytecodeParser.VERBOSE) 
          println("Parsing byte codes in " + name);

        this.outerClassInfo = outerClassInfo;
        parseMagic();
        parseMinor();
        parseMajor();
        parseConstantPool();
        CompilationUnit cu = new CompilationUnit();
        TypeDecl typeDecl = parseTypeDecl();
        cu.setPackageDecl(classInfo.packageDecl());
        cu.addTypeDecl(typeDecl);
        parseFields(typeDecl);
        parseMethods(typeDecl);
        new Attributes(this, typeDecl, outerTypeDecl, classPath);

        is.close();
        is = null;
        return cu;
      }

    // Declared in BytecodeReader.jrag at line 263

    public void parseMagic() {
      if (next() != 0xca || next() != 0xfe || next() != 0xba || next() != 0xbe)
        error("magic error");
    }

    // Declared in BytecodeReader.jrag at line 268

    public void parseMinor() {
      int low = u1();
      int high = u1();
      if(BytecodeParser.VERBOSE) 
        println("Minor: " + high + "." + low);
    }

    // Declared in BytecodeReader.jrag at line 275

    public void parseMajor() {
      int low = u1();
      int high = u1();
      if(BytecodeParser.VERBOSE) 
        println("Major: " + high + "." + low);
    }

    // Declared in BytecodeReader.jrag at line 282

    public static boolean isInnerClass = false;

    // Declared in BytecodeReader.jrag at line 284

    public TypeDecl parseTypeDecl() {
      isInnerClass = false;

      int flags = u2();
      Modifiers modifiers = modifiers(flags & 0xfddf);
      if ((flags & 0x0200) == 0) {
        ClassDecl decl = new ClassDecl();
        decl.setModifiers(modifiers);
        decl.setID(parseThisClass());
        Access superClass = parseSuperClass();
        decl.setSuperClassAccessOpt(superClass == null ? new Opt()
            : new Opt(superClass));
        decl.setImplementsList(parseInterfaces(new List()));

        if((flags & 0x0008) == 0 && outerClassInfo != null)
          isInnerClass = true;
        return decl;
      } else {
        InterfaceDecl decl = new InterfaceDecl();
        decl.setModifiers(modifiers);
        decl.setID(parseThisClass());
        Access superClass = parseSuperClass();
        decl.setSuperInterfaceIdList(
            parseInterfaces(
              superClass == null ? new List()
              : new List().add(superClass)));
        return decl;
      }
    }

    // Declared in BytecodeReader.jrag at line 315


    public String parseThisClass() {
      int index = u2();
      CONSTANT_Class_Info info = (CONSTANT_Class_Info) constantPool[index];
      classInfo = info;
      return info.simpleName();
    }

    // Declared in BytecodeReader.jrag at line 322

    public Access parseSuperClass() {
      int index = u2();
      if (index == 0)
        return null;
      CONSTANT_Class_Info info = (CONSTANT_Class_Info) constantPool[index];
      return info.access();
    }

    // Declared in BytecodeReader.jrag at line 330

    public List parseInterfaces(List list) {
      int count = u2();
      for (int i = 0; i < count; i++) {
        CONSTANT_Class_Info info = (CONSTANT_Class_Info) constantPool[u2()];
        list.add(info.access());
      }
      return list;
    }

    // Declared in BytecodeReader.jrag at line 340


    public Access fromClassName(String s) {
      // Sample ClassName: a/b/c$d$e
      // the package name ends at the last '/'
      // after that follows a list of type names separated by '$'
      // all except the first are nested types

      String packageName = "";
      int index = s.lastIndexOf('/');
      if(index != -1)
        packageName = s.substring(0, index).replace('/', '.');
      String[] typeNames = s.substring(index + 1).split("\\$");
      Access result = new TypeAccess(packageName, typeNames[0]);
      for(int i = 1; i < typeNames.length; i++)
        result = result.qualifiesAccess(new TypeAccess(typeNames[i]));
      return result;

    }

    // Declared in BytecodeReader.jrag at line 358

    public static Modifiers modifiers(int flags) {
      Modifiers m = new Modifiers();
      if ((flags & 0x0001) != 0)
        m.addModifier(new Modifier("public"));
      if ((flags & 0x0002) != 0)
        m.addModifier(new Modifier("private"));
      if ((flags & 0x0004) != 0)
        m.addModifier(new Modifier("protected"));
      if ((flags & 0x0008) != 0)
        m.addModifier(new Modifier("static"));
      if ((flags & 0x0010) != 0)
        m.addModifier(new Modifier("final"));
      if ((flags & 0x0020) != 0)
        m.addModifier(new Modifier("synchronized"));
      if ((flags & 0x0040) != 0)
        m.addModifier(new Modifier("volatile"));
      if ((flags & 0x0080) != 0)
        m.addModifier(new Modifier("transient"));
      if ((flags & 0x0100) != 0)
        m.addModifier(new Modifier("native"));
      if ((flags & 0x0400) != 0)
        m.addModifier(new Modifier("abstract"));
      if ((flags & 0x0800) != 0)
        m.addModifier(new Modifier("strictfp"));
      return m;
    }

    // Declared in BytecodeReader.jrag at line 385

    public void parseFields(TypeDecl typeDecl) {
      int count = u2();
      if(BytecodeParser.VERBOSE) 
        println("Fields (" + count + "):");
      for (int i = 0; i < count; i++) {
        if(BytecodeParser.VERBOSE) 
          print(" Field nbr " + i + " ");
        FieldInfo fieldInfo = new FieldInfo(this);
        if(!fieldInfo.isSynthetic())
          typeDecl.addBodyDecl(fieldInfo.bodyDecl());
      }
    }

    // Declared in BytecodeReader.jrag at line 399


    public void parseMethods(TypeDecl typeDecl) {
      int count = u2();
      if(BytecodeParser.VERBOSE) 
        println("Methods (" + count + "):");
      for (int i = 0; i < count; i++) {
        if(BytecodeParser.VERBOSE) 
          print("  Method nbr " + i + " ");
        MethodInfo info = new MethodInfo(this);
        if(!info.isSynthetic() && !info.name.equals("<clinit>")) {
          typeDecl.addBodyDecl(info.bodyDecl());
        }
      }
    }

    // Declared in BytecodeReader.jrag at line 414


    public CONSTANT_Info[] constantPool = null;

    // Declared in BytecodeReader.jrag at line 416

    private void checkLengthAndNull(int index) {
      if(index >= constantPool.length) {
        throw new Error("Trying to access element " + index  + " in constant pool of length " + constantPool.length);
      }
      if(constantPool[index] == null)
        throw new Error("Unexpected null element in constant pool at index " + index);
    }

    // Declared in BytecodeReader.jrag at line 423
    public boolean validConstantPoolIndex(int index) {
      return index < constantPool.length && constantPool[index] != null;
    }

    // Declared in BytecodeReader.jrag at line 426
    public CONSTANT_Info getCONSTANT_Info(int index) {
      checkLengthAndNull(index);
      return constantPool[index];
    }

    // Declared in BytecodeReader.jrag at line 430
    public CONSTANT_Utf8_Info getCONSTANT_Utf8_Info(int index) {
      checkLengthAndNull(index);
      CONSTANT_Info info = constantPool[index];
      if(!(info instanceof CONSTANT_Utf8_Info))
        throw new Error("Expected CONSTANT_Utf8_info at " + index + " in constant pool but found " + info.getClass().getName());
      return (CONSTANT_Utf8_Info)info;
    }

    // Declared in BytecodeReader.jrag at line 437
    public CONSTANT_Class_Info getCONSTANT_Class_Info(int index) {
      checkLengthAndNull(index);
      CONSTANT_Info info = constantPool[index];
      if(!(info instanceof CONSTANT_Class_Info))
        throw new Error("Expected CONSTANT_Class_info at " + index + " in constant pool but found " + info.getClass().getName());
      return (CONSTANT_Class_Info)info;
    }

    // Declared in BytecodeReader.jrag at line 445

    public void parseConstantPool() {
      int count = u2();
      if(BytecodeParser.VERBOSE) 
        println("constant_pool_count: " + count);
      constantPool = new CONSTANT_Info[count + 1];
      for (int i = 1; i < count; i++) {
        parseEntry(i);
        if (constantPool[i] instanceof CONSTANT_Long_Info
            || constantPool[i] instanceof CONSTANT_Double_Info)
          i++;
      }

      //println("ConstantPool: ");
      //for(int i = 1; i < count; i++) {
      //  println(i + ", " + constantPool[i]);
      //}

    }

    // Declared in BytecodeReader.jrag at line 464

    private static final int CONSTANT_Class = 7;

    // Declared in BytecodeReader.jrag at line 465
    private static final int CONSTANT_FieldRef = 9;

    // Declared in BytecodeReader.jrag at line 466
    private static final int CONSTANT_MethodRef = 10;

    // Declared in BytecodeReader.jrag at line 467
    private static final int CONSTANT_InterfaceMethodRef = 11;

    // Declared in BytecodeReader.jrag at line 468
    private static final int CONSTANT_String = 8;

    // Declared in BytecodeReader.jrag at line 469
    private static final int CONSTANT_Integer = 3;

    // Declared in BytecodeReader.jrag at line 470
    private static final int CONSTANT_Float = 4;

    // Declared in BytecodeReader.jrag at line 471
    private static final int CONSTANT_Long = 5;

    // Declared in BytecodeReader.jrag at line 472
    private static final int CONSTANT_Double = 6;

    // Declared in BytecodeReader.jrag at line 473
    private static final int CONSTANT_NameAndType = 12;

    // Declared in BytecodeReader.jrag at line 474
    private static final int CONSTANT_Utf8 = 1;

    // Declared in BytecodeReader.jrag at line 476

    public void parseEntry(int i) {
      int tag = u1();
      switch (tag) {
        case CONSTANT_Class:
          constantPool[i] = new CONSTANT_Class_Info(this);
          break;
        case CONSTANT_FieldRef:
          constantPool[i] = new CONSTANT_Fieldref_Info(this);
          break;
        case CONSTANT_MethodRef:
          constantPool[i] = new CONSTANT_Methodref_Info(this);
          break;
        case CONSTANT_InterfaceMethodRef:
          constantPool[i] = new CONSTANT_InterfaceMethodref_Info(this);
          break;
        case CONSTANT_String:
          constantPool[i] = new CONSTANT_String_Info(this);
          break;
        case CONSTANT_Integer:
          constantPool[i] = new CONSTANT_Integer_Info(this);
          break;
        case CONSTANT_Float:
          constantPool[i] = new CONSTANT_Float_Info(this);
          break;
        case CONSTANT_Long:
          constantPool[i] = new CONSTANT_Long_Info(this);
          break;
        case CONSTANT_Double:
          constantPool[i] = new CONSTANT_Double_Info(this);
          break;
        case CONSTANT_NameAndType:
          constantPool[i] = new CONSTANT_NameAndType_Info(this);
          break;
        case CONSTANT_Utf8:
          constantPool[i] = new CONSTANT_Utf8_Info(this);
          break;
        default:
          println("Unknown entry: " + tag);
      }
    }


}
