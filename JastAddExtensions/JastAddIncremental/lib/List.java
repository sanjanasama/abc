package AST;
public class List<T extends ASTNode> extends ASTNode<T> implements Cloneable {
  public List() {
    super();
  }

  public List<T> add(T node) {
    addChild(node);
    return this;
  }
  public void insertChild(T node, int i) {
    list$touched = true;
    super.insertChild(node, i);
  }

  public void addChild(T node) {
    list$touched = true;
    super.addChild(node);
  }

  public void removeChild(int i) {
    list$touched = true;
    super.removeChild(i);
  }
  public int getNumChild() {
    if(list$touched) {
      for(int i = 0; i < getNumChildNoTransform(); i++)
        getChild(i);
      list$touched = false;
    }
    int res = getNumChildNoTransform();
    state().registerDependency(new Dependency(this, 2), res);
    return res;
  }
  private boolean list$touched = true;
}
