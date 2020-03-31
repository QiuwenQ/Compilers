import java.util.*;
public class Sym {
    private String type;

    /* array to keep track of the id's line and char*/
    private int [] idLocation;

    /* array to keep track of function's formal's type */
    private List<String> fnFormals;

    /* table is used to separately store the symbol table 
    associated with each struct definition */
    private SymTable table;
    
    public Sym(String type) {
        this.type = type;
    }
    /* method to set the struct symbol table */
    public void setTable(SymTable tab){
        this.table = tab;
    }
    /* accessor method for struct symbol table */
    public SymTable getTable(){
        return this.table;
    }
    /* method to set the id's location */
    public void setIdLocation(int line, int ch){
        idLocation = new int [] {line, ch};
    }
    /* accessor method for id's location */
    public int [] getIdLocation(){
        return idLocation;
    }
    /* acessor method for getting this id's type */
    public String getType() {
        return type;
    }
    /* method to set the list of function's formals */
    public void setFnFormals(List <String> a){
        fnFormals = a;
    }
    /* accessor method to get the list of function's formals */
    public List<String> getFnFormals(){
        return fnFormals;
    }
    /* used to to print this sym's infomation for debugging purposes */
    public String toString() {
        if (fnFormals != null){
            int size = fnFormals.size();
            String listT = "";
            for (int i = 0; i<size; i++){
                listT+= " "+fnFormals.get(i);
            }
            return type +" "+ idLocation[0] + " "+idLocation[1] + listT;
        }
        return type +" "+ idLocation[0] + " "+idLocation[1];
    }
}