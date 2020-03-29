import java.util.*;
public class Sym {
    private String type;
    //array to keep track of the id's line and char
    private int [] idLocation;

    //array to keep track of function's formal's type
    private List<String> fnFormals;

    /* table is used to separately store the symbol table 
    associated with each struct definition */
    private SymTable table;
    
    public Sym(String type) {
        this.type = type;
    }
    //method to set the struct symbol table
    public void setTable(SymTable tab){
        this.table = tab;
    }
    //accessor method for struct symbol table
    public SymTable getTable(){
        return this.table;
    }
    public void setIdLocation(int line, int ch){
        idLocation = new int [] {line, ch};
    }
    public int [] getIdLocation(){
        return idLocation;
    }
    public String getType() {
        return type;
    }
    public void setFnFormals(List <String> a){
        fnFormals = a;
    }
    public List<String> getFnFormals(){
        return fnFormals;
    }
    public String toString() {
        if (idLocation!=null){
            return type +" "+ idLocation[0] + " "+idLocation[1];
        } 
        if (table != null){
            return type +" "+ idLocation[0] + " "+idLocation[1];
        }
        if (fnFormals != null){
            return type +" "+ idLocation[0] + " "+idLocation[1] + fnFormals.toArray().toString();
        }
        return type;
    }
}
