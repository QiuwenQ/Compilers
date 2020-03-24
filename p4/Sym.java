public class Sym {
    private String type;

    /* table is used to separately store the symbol table 
    associated with each struct definition */
    private HashMap<String, Sym> table;
    
    public Sym(String type) {
        this.type = type;
    }
    //method to set the struct symbol table
    public void setTable(HashMap<String, Sym> tab){
        this.table = tab;
    }
    //accessor method for struct symbol table
    public HashMap<String, Sym> getTable(){
        return this.table;
    }
    
    public String getType() {
        return type;
    }
    
    public String toString() {
        return type;
    }
}
