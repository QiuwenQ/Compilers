import java.io.*;
import java.util.*;

// **********************************************************************
// The ASTnode class defines the nodes of the abstract-syntax tree that
// represents a Wumbo program.
//
// Internal nodes of the tree contain pointers to children, organized
// either in a list (for nodes that may have a variable number of
// children) or as a fixed set of fields.
//
// The nodes for literals and ids contain line and character number
// information; for string literals and identifiers, they also contain a
// string; for integer literals, they also contain an integer value.
//
// Here are all the different kinds of AST nodes and what kinds of children
// they have.  All of these kinds of AST nodes are subclasses of "ASTnode".
// Indentation indicates further subclassing:
//
//     Subclass            Children
//     --------            ----
//     ProgramNode         DeclListNode
//     DeclListNode        linked list of DeclNode
//     DeclNode:
//       VarDeclNode       TypeNode, IdNode, int
//       FnDeclNode        TypeNode, IdNode, FormalsListNode, FnBodyNode
//       FormalDeclNode    TypeNode, IdNode
//       StructDeclNode    IdNode, DeclListNode
//
//     FormalsListNode     linked list of FormalDeclNode
//     FnBodyNode          DeclListNode, StmtListNode
//     StmtListNode        linked list of StmtNode
//     ExpListNode         linked list of ExpNode
//
//     TypeNode:
//       IntNode           -- none --
//       BoolNode          -- none --
//       VoidNode          -- none --
//       StructNode        IdNode
//
//     StmtNode:
//       AssignStmtNode      AssignNode
//       PostIncStmtNode     ExpNode
//       PostDecStmtNode     ExpNode
//       ReadStmtNode        ExpNode
//       WriteStmtNode       ExpNode
//       IfStmtNode          ExpNode, DeclListNode, StmtListNode
//       IfElseStmtNode      ExpNode, DeclListNode, StmtListNode,
//                                    DeclListNode, StmtListNode
//       WhileStmtNode       ExpNode, DeclListNode, StmtListNode
//       RepeatStmtNode      ExpNode, DeclListNode, StmtListNode
//       CallStmtNode        CallExpNode
//       ReturnStmtNode      ExpNode
//
//     ExpNode:
//       IntLitNode          -- none --
//       StrLitNode          -- none --
//       TrueNode            -- none --
//       FalseNode           -- none --
//       IdNode              -- none --
//       DotAccessNode       ExpNode, IdNode
//       AssignNode          ExpNode, ExpNode
//       CallExpNode         IdNode, ExpListNode
//       UnaryExpNode        ExpNode
//         UnaryMinusNode
//         NotNode
//       BinaryExpNode       ExpNode ExpNode
//         PlusNode
//         MinusNode
//         TimesNode
//         DivideNode
//         AndNode
//         OrNode
//         EqualsNode
//         NotEqualsNode
//         LessNode
//         GreaterNode
//         LessEqNode
//         GreaterEqNode
//
// Here are the different kinds of AST nodes again, organized according to
// whether they are leaves, internal nodes with linked lists of children, or
// internal nodes with a fixed number of children:
//
// (1) Leaf nodes:
//        IntNode,   BoolNode,  VoidNode,  IntLitNode,  StrLitNode,
//        TrueNode,  FalseNode, IdNode
//
// (2) Internal nodes with (possibly empty) linked lists of children:
//        DeclListNode, FormalsListNode, StmtListNode, ExpListNode
//
// (3) Internal nodes with fixed numbers of children:
//        ProgramNode,     VarDeclNode,     FnDeclNode,     FormalDeclNode,
//        StructDeclNode,  FnBodyNode,      StructNode,     AssignStmtNode,
//        PostIncStmtNode, PostDecStmtNode, ReadStmtNode,   WriteStmtNode
//        IfStmtNode,      IfElseStmtNode,  WhileStmtNode,  RepeatStmtNode,
//        CallStmtNode
//        ReturnStmtNode,  DotAccessNode,   AssignExpNode,  CallExpNode,
//        UnaryExpNode,    BinaryExpNode,   UnaryMinusNode, NotNode,
//        PlusNode,        MinusNode,       TimesNode,      DivideNode,
//        AndNode,         OrNode,          EqualsNode,     NotEqualsNode,
//        LessNode,        GreaterNode,     LessEqNode,     GreaterEqNode
//
// **********************************************************************

// **********************************************************************
// ASTnode class (base class for all other kinds of nodes)
// **********************************************************************

abstract class ASTnode {
    // every subclass must provide an unparse operation
    abstract public void unparse(PrintWriter p, int indent);
    /**
    * This method analyzes the name of various variable, struct, and function for proper 
    * declarations and usage 
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    abstract public void analysis(PrintWriter p, SymTable sTable);
    // this method can be used by the unparse methods to do indenting
    protected void addIndentation(PrintWriter p, int indent) {
        for (int k = 0; k < indent; k++) p.print(" ");
    }
}

// **********************************************************************
// ProgramNode,  DeclListNode, FormalsListNode, FnBodyNode,
// StmtListNode, ExpListNode
// **********************************************************************

class ProgramNode extends ASTnode {
    public ProgramNode(DeclListNode L) {
        myDeclList = L;
    }
    /**
    * This method analyzes the name of various variable, struct, and function for proper 
    * declarations and usage in the program node
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        mySymTable = new SymTable();
        myDeclList.analysis(p, mySymTable);
    }
    public void unparse(PrintWriter p, int indent) {
        myDeclList.unparse(p, indent);
    }

    private DeclListNode myDeclList;
    private SymTable mySymTable;
}

class DeclListNode extends ASTnode {
    public DeclListNode(List<DeclNode> S) {
        myDecls = S;
    }
    /**
    * This method analyzes the name of various variable, struct, and function for proper 
    * declarations in the declaration list
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        Iterator it = myDecls.iterator();
        int x = 0;
        try {
            while (it.hasNext()) { //at least 1 element
                ((DeclNode)it.next()).analysis(p, sTable);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }
    public void unparse(PrintWriter p, int indent) {
        Iterator it = myDecls.iterator();
        try {
            while (it.hasNext()) {
                ((DeclNode)it.next()).unparse(p, indent);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }

    private List<DeclNode> myDecls;
}

class FormalsListNode extends ASTnode {
    public FormalsListNode(List<FormalDeclNode> S) {
        myFormals = S;
    }
    /**
    * This method analyzes the name of various variable for proper 
    * declarations and usage in the formals list
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        fTypes = new ArrayList<>();
        //go through each formalDeclNode and analyze it
        Iterator<FormalDeclNode> it = myFormals.iterator();
        if (it.hasNext()){
            while(it.hasNext()){
                FormalDeclNode currNode = it.next();
                currNode.analysis(p, sTable);
                //store each formal's type if string is NOT empty (is a good decl)
                String currType = currNode.getFormalType();
                if (currType != ""){
                    fTypes.add(currType);
                }
            }
        }
    }
    public void unparse(PrintWriter p, int indent) {
        Iterator<FormalDeclNode> it = myFormals.iterator();
        if (it.hasNext()) { // if there is at least one element
            it.next().unparse(p, indent);
            while (it.hasNext()) {  // print the rest of the list
                p.print(", ");
                it.next().unparse(p, indent);
            }
        }
    }
    //accessor for fTypes field 
    public List<String> getFTypes(){
        return fTypes;
    }
    //holds the types of all correctly declared variables
    private List<String> fTypes; 
    private List<FormalDeclNode> myFormals;
}

class FnBodyNode extends ASTnode {
    public FnBodyNode(DeclListNode declList, StmtListNode stmtList) {
        myDeclList = declList;
        myStmtList = stmtList;
    }
    /**
    * This method analyzes the name of various variable, struct, and function for proper 
    * declarations and usage in the function
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //scope is within the function, also includes the formals
        myDeclList.analysis(p,sTable);
        myStmtList.analysis(p,sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        myDeclList.unparse(p, indent);
        myStmtList.unparse(p, indent);
    }

    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class StmtListNode extends ASTnode {
    public StmtListNode(List<StmtNode> S) {
        myStmts = S;
    }
    /**
    * This method analyzes the name used in stmts for proper usage
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        Iterator<StmtNode> it = myStmts.iterator();
        while (it.hasNext()) {
            it.next().analysis(p, sTable);
        }
    }
    public void unparse(PrintWriter p, int indent) {
        Iterator<StmtNode> it = myStmts.iterator();
        while (it.hasNext()) {
            it.next().unparse(p, indent);
        }
    }

    private List<StmtNode> myStmts;
}

class ExpListNode extends ASTnode {
    public ExpListNode(List<ExpNode> S) {
        myExps = S;
    }
    /**
    * This method analyzes the name in exp for proper usage 
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        Iterator<ExpNode> it = myExps.iterator();
        while (it.hasNext()) { // if there is at least one element
            it.next().analysis(p, sTable);
        }
    }
    public void unparse(PrintWriter p, int indent) {
        Iterator<ExpNode> it = myExps.iterator();
        if (it.hasNext()) { // if there is at least one element
            it.next().unparse(p, indent);
            while (it.hasNext()) {  // print the rest of the list
                p.print(", ");
                it.next().unparse(p, indent);
            }
        }
    }

    private List<ExpNode> myExps;
}

// **********************************************************************
// DeclNode and its subclasses
// **********************************************************************

abstract class DeclNode extends ASTnode {
}

class VarDeclNode extends DeclNode {
    public VarDeclNode(TypeNode type, IdNode id, int size) {
        myType = type;
        myId = id;
        mySize = size;
    }
    /**
    * This method analyzes the name of variable for proper declarations.
    * It calls a vAnalysis method to analyze a regular variable declaraion and the sAnalysis 
    * to analyze a struct declaration
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p , SymTable sTable){
        if (mySize != 0){//a regular variable declaration
            vAnalysis(p, sTable);
        } else{
            sAnalysis(p, sTable);
        }
    }
    /**
    * This method analyzes the name of a regular variable for proper declarations
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void vAnalysis(PrintWriter p , SymTable sTable){//regular var declaration 
        //TODO: check if type is void, if so, issue error: Non-function declared void
        int [] info = myId.getIdInfo();
        String name = myId.getName();
        if (myType.strName == "void"){
            String msg = "Non-function declared void";
            ErrMsg.fatal(info[0], info[1], msg);
        } //else{
            try{
                //check if in local scope, since not in local scope, then can declare this
                if (sTable.lookupLocal(name) == null) {
                    //create new Sym and add to symTable
                    Sym idSym = new Sym(myType.strName);
                    idSym.setIdLocation(info[0], info[1]); //add line and char of var
                    sTable.addDecl(name, idSym);
                } else{
                    String msg = "Multiply declared identifier";
                    ErrMsg.fatal(info[0], info[1], msg);
                }  
                //TODO: edit these error messages
            } catch(Exception e){
                System.err.println("unexpected Exception in VarDeclNode.analysis");
            }  
        //} 
    }
    
    /**
    * This method analyzes the name of struct variable for proper declarations
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void sAnalysis(PrintWriter p , SymTable sTable){ //is a struct declaration
        
        //struct idNode information example for: struct Point
        IdNode sId = ((StructNode)myType).getIdNode(); 
        int [] sInfo = sId.getIdInfo();
        String sName = sId.getName(); //name of the struct ex. Point
        Sym sSym = null;
        try{
            sSym = sTable.lookupGlobal(sName);
        } catch(Exception e){
            System.err.println("unexpected Exception in VarDeclNode.analysis");
        }
            
        //check if struct type has been declared and check if it is actually a struct type
        if(sSym != null){
            if (sSym.getType() == "struct"){ //name of a struct
                //get id information and check the variable name
                int [] info = myId.getIdInfo();
                String name = myId.getName();
                try{
                    //check if in local scope, since not in local scope, then can declare this
                    if (sTable.lookupLocal(name) == null) {
                        //create new Sym and add to symTable
                        Sym idSym = new Sym(sName); //type is type of struct
                        idSym.setIdLocation(info[0], info[1]); //add line and char of var
                        sTable.addDecl(name, idSym); //add to table
                    } else{ //var name exists, can't declare this!
                        String msg = "Multiply declared identifier";
                        ErrMsg.fatal(info[0], info[1], msg);
                    }  
                } catch(Exception e){
                    //TODO: add errors
                    System.err.println("unexpected Exception in VarDeclNode.analysis");
                }
            } else{ // name exist but isn't struct type
                String msg = "Invalid name of struct type";
                ErrMsg.fatal(sInfo[0], sInfo[1], msg);
            }
        } else { //struct type not declared
                String msg = "Invalid name of struct type";
                ErrMsg.fatal(sInfo[0], sInfo[1], msg);
        }
        
    }
    
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
        p.println(";");
    }

    private TypeNode myType;
    private IdNode myId;
    private int mySize;  // use value NOT_STRUCT if this is not a struct type

    public static int NOT_STRUCT = -1;
}

class FnDeclNode extends DeclNode {
    public FnDeclNode(TypeNode type,
                      IdNode id,
                      FormalsListNode formalList,
                      FnBodyNode body) {
        myType = type;
        myId = id;
        myFormalsList = formalList;
        myBody = body;
    }
    /**
    * This method analyzes for proper function declarations
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //TODO:check the type and ID for the function first before going into the formals and body!!
        int [] info = myId.getIdInfo();
        String name = myId.getName();
        Sym idSym = null;
        //function can be void so don't need to check for that
        try{
            //check if in local scope, if not, then add function name to local scope
            if (sTable.lookupLocal(name) == null) {
                //create new Sym and add to symTable
                idSym = new Sym(myType.strName);
                idSym.setIdLocation(info[0], info[1]); //add line and char of var
                sTable.addDecl(name, idSym);
            } else{ //in local scope: issue error
                String msg = "Multiply declared identifier";
                ErrMsg.fatal(info[0], info[1], msg);
            }
        } catch (Exception e){
            System.err.println("unexpected Exception in FnDeclNode.analysis");
        }

        //analyze formals and body (independent of of func name already exists)
        sTable.addScope();
        myFormalsList.analysis(p, sTable);
        if (idSym != null){
            idSym.setFnFormals(myFormalsList.getFTypes());
        }
        myBody.analysis(p, sTable);

        try{
            //remove scope of the function after finishing analyzing formals and body
            sTable.removeScope();
        } catch (Exception e){
            System.err.println("unexpected EmptySymTableException in VarDeclNode.analysis");
        }
        
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
        p.print("(");
        myFormalsList.unparse(p, 0);
        p.println(") {");
        myBody.unparse(p, indent+4);
        p.println("}\n");
    }

    private TypeNode myType;
    private IdNode myId;
    private FormalsListNode myFormalsList;
    private FnBodyNode myBody;
}

class FormalDeclNode extends DeclNode {
    public FormalDeclNode(TypeNode type, IdNode id) {
        myType = type;
        myId = id;
    }
    /**
    * This method analyzes formals within a function for proper declarations
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //same idea as VarDeclNode.vAnalysis
        int [] info = myId.getIdInfo();
        String name = myId.getName();
        if (myType.strName == "void"){
            String msg = "Non-function declared void";
            ErrMsg.fatal(info[0], info[1], msg);
        } else{
            try{
                //check if in local scope, since not in local scope, then can declare this
                if (sTable.lookupLocal(name) == null) {
                    //create new Sym and add to symTable
                    Sym idSym = new Sym(myType.strName);
                    idSym.setIdLocation(info[0], info[1]); //add line and char of var
                    sTable.addDecl(name, idSym);
                    //store the type of this formal so FormalsListNode can access
                    formalType = myType.strName;
                } else{
                    String msg = "Multiply declared identifier";
                    ErrMsg.fatal(info[0], info[1], msg);
                }  
                //TODO: edit these error messages
            } catch(Exception e){
                System.err.println("unexpected Exception in FormalDeclNode.analysis");
            }  
        } 
    }
    public void unparse(PrintWriter p, int indent) {
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
    }
    //accessor method for formalType
    public String getFormalType(){
        return formalType;
    }
    private String formalType = ""; //to store the type of this formal i.e. int
    private TypeNode myType;
    private IdNode myId;
}

class StructDeclNode extends DeclNode {
    public StructDeclNode(IdNode id, DeclListNode declList) {
        myId = id;
        myDeclList = declList;
    }
    /**
    * This method analyzes the name for proper struct declaration
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        int [] info = myId.getIdInfo(); //line and char
        String name = myId.getName(); //var name of struct
        //check if name of struct is in global scope: since can only be declared global
        try{
            if (sTable.lookupGlobal(name) == null) {
                
                //create new Sym and add to symTable
                Sym idSym = new Sym("struct"); //set type to struct
                idSym.setIdLocation(info[0], info[1]); //add line and char of var
                SymTable structTable = new SymTable();

                myDeclList.analysis(p,structTable);

                idSym.setTable(structTable); //set the struct table
                sTable.addDecl(name, idSym); //add the struct to this scope

            } else{
                String msg = "Multiply declared identifier";
                ErrMsg.fatal(info[0], info[1], msg);
            }
        } catch(Exception e){
            //TODO: edit errors
            System.err.println("unexpected Exception in StructDeclNode.analysis");
        }
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        p.print("struct ");
        myId.unparse(p, 0);
        p.println("{");
        myDeclList.unparse(p, indent+4);
        addIndentation(p, indent);
        p.println("};\n");

    }

    private IdNode myId;
    private DeclListNode myDeclList;
}

// **********************************************************************
// TypeNode and its Subclasses
// **********************************************************************

abstract class TypeNode extends ASTnode {
    public String strName;
}

class IntNode extends TypeNode {
    public IntNode() {
        strName = "int";
    }
    /**
    * This method is not used as an "int" does not need to be checked
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //empty
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("int");
    }
}

class BoolNode extends TypeNode {
    public BoolNode() {
        strName = "bool";
    }
    /**
    * This method is not used as an "bool" does not need to be checked
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //empty
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("bool");
    }
}

class VoidNode extends TypeNode {
    public VoidNode() {
        strName = "void";
    }
    /**
    * This method is not used as a "void" does not need to be checked
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //empty
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("void");
    }
}
    /**
    * This method is not used as an "struct" does not need to be checked
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
class StructNode extends TypeNode {
    public StructNode(IdNode id) {
        myId = id;
        strName = myId.getName();
    }
    public void analysis(PrintWriter p, SymTable sTable){
        //empty
    }
    public IdNode getIdNode(){
        return myId;
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("struct ");
        myId.unparse(p, 0);
    }

    private IdNode myId;
}

// **********************************************************************
// StmtNode and its subclasses
// **********************************************************************

abstract class StmtNode extends ASTnode {
}

class AssignStmtNode extends StmtNode {
    public AssignStmtNode(AssignNode assign) {
        myAssign = assign;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myAssign.analysis(p,sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        myAssign.unparse(p, -1); // no parentheses
        p.println(";");
    }

    private AssignNode myAssign;
}

class PostIncStmtNode extends StmtNode {
    public PostIncStmtNode(ExpNode exp) {
        myExp = exp;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        myExp.unparse(p, 0);
        p.println("++;");
    }

    private ExpNode myExp;
}

class PostDecStmtNode extends StmtNode {
    public PostDecStmtNode(ExpNode exp) {
        myExp = exp;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        myExp.unparse(p, 0);
        p.println("--;");
    }

    private ExpNode myExp;
}

class ReadStmtNode extends StmtNode {
    public ReadStmtNode(ExpNode e) {
        myExp = e;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        p.print("cin >> ");
        myExp.unparse(p, 0);
        p.println(";");
    }

    // 1 child (actually can only be an IdNode or an ArrayExpNode)
    private ExpNode myExp;
}

class WriteStmtNode extends StmtNode {
    public WriteStmtNode(ExpNode exp) {
        myExp = exp;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        p.print("cout << ");
        myExp.unparse(p, 0);
        p.println(";");
    }

    private ExpNode myExp;
}

class IfStmtNode extends StmtNode {
    public IfStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
        myDeclList = dlist;
        myExp = exp;
        myStmtList = slist;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
        try{
            sTable.addScope();
            myDeclList.analysis(p, sTable);
            myStmtList.analysis(p, sTable);
            sTable.removeScope();
        } catch (Exception e){
            System.err.println("unexpected Exception in IfStmtNode.analysis");
        }
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        p.print("if (");
        myExp.unparse(p, 0);
        p.println(") {");
        myDeclList.unparse(p, indent+4);
        myStmtList.unparse(p, indent+4);
        addIndentation(p, indent);
        p.println("}");
    }

    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class IfElseStmtNode extends StmtNode {
    public IfElseStmtNode(ExpNode exp, DeclListNode dlist1,
                          StmtListNode slist1, DeclListNode dlist2,
                          StmtListNode slist2) {
        myExp = exp;
        myThenDeclList = dlist1;
        myThenStmtList = slist1;
        myElseDeclList = dlist2;
        myElseStmtList = slist2;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
        //add if scope
        try{
            sTable.addScope();
            myThenDeclList.analysis(p, sTable);
            myThenStmtList.analysis(p, sTable);
            //remove if scope
            sTable.removeScope();
        } catch (Exception e){
            System.err.println("unexpected Exception in IfElseStmtNode.analysis (If)");
        }
        //add else scope
        try{
            sTable.addScope();
            myElseDeclList.analysis(p, sTable);
            myElseStmtList.analysis(p, sTable);
            //remove else scope
            sTable.removeScope();
        } catch (Exception e){
            System.err.println("unexpected Exception in IfElseStmtNode.analysis (else)");
        }
        
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        p.print("if (");
        myExp.unparse(p, 0);
        p.println(") {");
        myThenDeclList.unparse(p, indent+4);
        myThenStmtList.unparse(p, indent+4);
        addIndentation(p, indent);
        p.println("}");
        addIndentation(p, indent);
        p.println("else {");
        myElseDeclList.unparse(p, indent+4);
        myElseStmtList.unparse(p, indent+4);
        addIndentation(p, indent);
        p.println("}");
    }

    private ExpNode myExp;
    private DeclListNode myThenDeclList;
    private StmtListNode myThenStmtList;
    private StmtListNode myElseStmtList;
    private DeclListNode myElseDeclList;
}

class WhileStmtNode extends StmtNode {
    public WhileStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
        myExp = exp;
        myDeclList = dlist;
        myStmtList = slist;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
        try{
            sTable.addScope();
            myDeclList.analysis(p, sTable);
            myStmtList.analysis(p, sTable);
            //remove else scope
            sTable.removeScope();
        } catch (Exception e){
            System.err.println("unexpected Exception in WhileStmtNode.analysis");
        }
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        p.print("while (");
        myExp.unparse(p, 0);
        p.println(") {");
        myDeclList.unparse(p, indent+4);
        myStmtList.unparse(p, indent+4);
        addIndentation(p, indent);
        p.println("}");
    }

    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class RepeatStmtNode extends StmtNode {
    public RepeatStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
        myExp = exp;
        myDeclList = dlist;
        myStmtList = slist;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
        try{
            sTable.addScope();
            myDeclList.analysis(p, sTable);
            myStmtList.analysis(p, sTable);
            //remove else scope
            sTable.removeScope();
        } catch (Exception e){
            System.err.println("unexpected Exception in RepeatStmtNode.analysis");
        }
    }
    public void unparse(PrintWriter p, int indent) {
	addIndentation(p, indent);
        p.print("repeat (");
        myExp.unparse(p, 0);
        p.println(") {");
        myDeclList.unparse(p, indent+4);
        myStmtList.unparse(p, indent+4);
        addIndentation(p, indent);
        p.println("}");
    }

    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class CallStmtNode extends StmtNode {
    public CallStmtNode(CallExpNode call) {
        myCall = call;
    }
    /**
    * This method is used to analyze the correct usage in a function call
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myCall.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        myCall.unparse(p, indent);
        p.println(";");
    }

    private CallExpNode myCall;
}

class ReturnStmtNode extends StmtNode {
    public ReturnStmtNode(ExpNode exp) {
        myExp = exp;
    }
    /**
    * This method is used to analyze the correct usage of exp in stmts
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        if (myExp != null){
            myExp.analysis(p, sTable);
        }
    }
    public void unparse(PrintWriter p, int indent) {
        addIndentation(p, indent);
        p.print("return");
        if (myExp != null) {
            p.print(" ");
            myExp.unparse(p, 0);
        }
        p.println(";");
    }

    private ExpNode myExp; // possibly null
}

// **********************************************************************
// ExpNode and its subclasses
// **********************************************************************

abstract class ExpNode extends ASTnode {
}

class IntLitNode extends ExpNode {
    public IntLitNode(int lineNum, int charNum, int intVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myIntVal = intVal;
    }
    /**
    * This method not used. An intlit does not need to be analyzed
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //empty
    }
    public void unparse(PrintWriter p, int indent) {
        p.print(myIntVal);
    }

    private int myLineNum;
    private int myCharNum;
    private int myIntVal;
}

class StringLitNode extends ExpNode {
    public StringLitNode(int lineNum, int charNum, String strVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal;
    }
    /**
    * This method not used. A string literal does not need to be analyzed
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //empty
    }
    public void unparse(PrintWriter p, int indent) {
        p.print(myStrVal);
    }

    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
}

class TrueNode extends ExpNode {
    public TrueNode(int lineNum, int charNum) {
        myLineNum = lineNum;
        myCharNum = charNum;
    }
    /**
    * This method not used. An "true" does not need to be analyzed
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //empty
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("true");
    }

    private int myLineNum;
    private int myCharNum;
}

class FalseNode extends ExpNode {
    public FalseNode(int lineNum, int charNum) {
        myLineNum = lineNum;
        myCharNum = charNum;
    }
    /**
    * This method not used. A "false" does not need to be analyzed
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //empty
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("false");
    }

    private int myLineNum;
    private int myCharNum;
}

class IdNode extends ExpNode {
    public IdNode(int lineNum, int charNum, String strVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal; //name of the id
    }
    /**
    * This method is used to check if the name is previously declared locally 
    * (or globally, for a struct and function). If it is not, a sym is attached
    *  and the line number and char number that it appears in is stored
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //analysis of id usage
        //check local first then global if it is declared
        try{
            Sym idSym = sTable.lookupLocal(myStrVal);
            if (idSym == null){ //not in local, try global
                idSym = sTable.lookupGlobal(myStrVal);
                if (idSym == null){ //&& !isStructAccess
                    //id can't be find locally and globally: error
                    if (!isStructAccess){
                        String msg = "Undeclared identifier";
                        ErrMsg.fatal(myLineNum, myCharNum, msg);
                    }
                } else{ //id found globally
                    mySym = idSym;//link the sym to this idNode
                }
            } else{ // id found locally
                mySym = idSym;//link the sym to this idNode
            }
        } catch (Exception e){
            System.err.println("unexpected Exception in IdNode.analysis");
        }
    }
    //accessor method for Id's line, char, and value
    public int [] getIdInfo(){
        int [] i = new int [] {myLineNum, myCharNum};
        return i;
    }
    // accessor method for the name of this id
    public String getName(){
        return myStrVal;
    }
    // accessor method for the sym associated with this id
    public Sym getIdSym(){
        //for usage of an ID in dot access
        return mySym;
    }
    public void unparse(PrintWriter p, int indent) {
        p.print(myStrVal);
        if (mySym!=null && !noPrintType){
            p.print("("+mySym.getType()+")");
        } 
    }
    // used for functions, so the function type is not printed right after the name
    public void setNoPrintType(){
        noPrintType = true;
    }
    public void setIsStructAccess(){
        isStructAccess = true;
    }
    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
    private Sym mySym; //link to sym in symtable (has info on type)
    private boolean noPrintType = false; // keeps track if we don't want to print func type
    private boolean isStructAccess = false; // keeps track of if this id is a struct type
}

class DotAccessExpNode extends ExpNode {
    public DotAccessExpNode(ExpNode loc, IdNode id) {
        myLoc = loc;
        myId = id;
    }
    /**
    * This method is used to check if the struct access is proper usage 
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //TODO: incorporate complicated nesting situations
        //current assumption only pt.x access
        if (myLoc instanceof DotAccessExpNode){
            myLoc.analysis(p, sTable);
            //System.out.println("^^^^^^^DOTACCESSNODE^^^^^^^");
            if (((DotAccessExpNode)myLoc).getlhsSuccess()){
                int [] info = myId.getIdInfo();
                String msg = "Dot-access of non-struct type";
                ErrMsg.fatal(info[0], info[1], msg);
            }
            
        } else{
        myLoc.analysis(p, sTable);
        
        myLSym = ((IdNode)myLoc).getIdSym(); //sym associated with lhs
        
        if (myLSym != null){ //a sym was linked so lhs exists
            //check if lhs is a struct
            String lStruct = myLSym.getType(); //name of the struct
            //check globally if this matches a struct
            structSym = null;
            try{
                structSym = sTable.lookupGlobal(lStruct);
            } catch (Exception e){
                System.err.println("unexpected Exception in DotAccessExpNode.analysis lookupGlobal");
            }
            
            if (structSym != null && structSym.getType() == "struct"){
                //lhs is a struct so check right side is a field of struct
                SymTable structTable = structSym.getTable();
                myId.setIsStructAccess();
                myId.analysis(p, structTable);
                String rName = myId.getName();
                //check in symbol table of struct if var name exists
                try{
                    //look into struct table
                    myRSym = structSym.getTable().lookupLocal(rName);
                    if (myRSym == null){ //rhs is not a field in struct
                        int [] info = myId.getIdInfo();
                        String msg = "Invalid struct field name";
                        ErrMsg.fatal(info[0], info[1], msg);
                    }
                    lhsSuccess = true;
                } catch (Exception e){
                    System.err.println("unexpected Exception in DotAccessExpNode.analysis");
                }
            } else { //lhs exist but not a struct: error bad struct access
                int [] info = ((IdNode)myLoc).getIdInfo();
                String msg = "Dot-access of non-struct type";
                ErrMsg.fatal(info[0], info[1], msg);
            }
        }}
    }
    public void unparse(PrintWriter p, int indent) {
        //edit print to match output on assignment specification
        //p.print("(");
        myLoc.unparse(p, 0);
        //p.print(").");
        p.print(".");
        myId.unparse(p, 0);
    }
    //used to keep track for nested dot acceess
    public boolean getlhsSuccess(){
        return lhsSuccess;
    }
    private boolean lhsSuccess = false;
    private ExpNode myLoc;
    private IdNode myId;
    private Sym myLSym; //type is i.e. Point
    private Sym structSym; //type is struct
    private Sym myRSym; //type is i.e int/bool
}

class AssignNode extends ExpNode {
    public AssignNode(ExpNode lhs, ExpNode exp) {
        myLhs = lhs;
        myExp = exp;
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myLhs.analysis(p, sTable);
        myExp.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        if (indent != -1)  p.print("(");
        myLhs.unparse(p, 0);
        p.print(" = ");
        myExp.unparse(p, 0);
        if (indent != -1)  p.print(")");
    }

    private ExpNode myLhs;
    private ExpNode myExp;
}

class CallExpNode extends ExpNode {
    public CallExpNode(IdNode name, ExpListNode elist) {
        myId = name;
        myExpList = elist;
    }

    public CallExpNode(IdNode name) {
        myId = name;
        myExpList = new ExpListNode(new LinkedList<ExpNode>());
    }
    /**
    * This method is used to check if variables in function call is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        //using this method to not print the type of func after the var name
        myId.setNoPrintType(); 
        myId.analysis(p, sTable);
        if (myExpList != null){
            myExpList.analysis(p, sTable);
        }
    }
    public void unparse(PrintWriter p, int indent) {
        myId.unparse(p, 0);
        //TODO; add printout of function formals types
        List <String> formalTypeList = myId.getIdSym().getFnFormals();
        Iterator <String> it = formalTypeList.iterator();
        p.print("(");
        //print the function formals types
        if (it.hasNext()){
            p.print(it.next());
            while(it.hasNext()){
                p.print(",");
                p.print(it.next());
            }
        }
        //print arrow
        p.print("->");
        //print the function return type
        p.print(myId.getIdSym().getType());
        p.print(")");

        //print function parameters
        p.print("(");
        if (myExpList != null) {
            myExpList.unparse(p, 0);
        }
        p.print(")");
    }

    private IdNode myId;
    private ExpListNode myExpList;  // possibly null
}

abstract class UnaryExpNode extends ExpNode {
    public UnaryExpNode(ExpNode exp) {
        myExp = exp;
    }

    protected ExpNode myExp;
}

abstract class BinaryExpNode extends ExpNode {
    public BinaryExpNode(ExpNode exp1, ExpNode exp2) {
        myExp1 = exp1;
        myExp2 = exp2;
    }

    protected ExpNode myExp1;
    protected ExpNode myExp2;
}

// **********************************************************************
// Subclasses of UnaryExpNode
// **********************************************************************

class UnaryMinusNode extends UnaryExpNode {
    public UnaryMinusNode(ExpNode exp) {
        super(exp);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(-");
        myExp.unparse(p, 0);
        p.print(")");
    }
}

class NotNode extends UnaryExpNode {
    public NotNode(ExpNode exp) {
        super(exp);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(!");
        myExp.unparse(p, 0);
        p.print(")");
    }
}

// **********************************************************************
// Subclasses of BinaryExpNode
// **********************************************************************

class PlusNode extends BinaryExpNode {
    public PlusNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" + ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class MinusNode extends BinaryExpNode {
    public MinusNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" - ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class TimesNode extends BinaryExpNode {
    public TimesNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" * ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class DivideNode extends BinaryExpNode {
    public DivideNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" / ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class AndNode extends BinaryExpNode {
    public AndNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" && ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class OrNode extends BinaryExpNode {
    public OrNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" || ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class EqualsNode extends BinaryExpNode {
    public EqualsNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" == ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class NotEqualsNode extends BinaryExpNode {
    public NotEqualsNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" != ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class LessNode extends BinaryExpNode {
    public LessNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" < ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class GreaterNode extends BinaryExpNode {
    public GreaterNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" > ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class LessEqNode extends BinaryExpNode {
    public LessEqNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" <= ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class GreaterEqNode extends BinaryExpNode {
    public GreaterEqNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }
    /**
    * This method is used to check if exp is properly used
    *
    * @param p The PrintWriter which is used to print into an output file
    * @param sTable The symTable of this program which holds all the correctly declared names
    */
    public void analysis(PrintWriter p, SymTable sTable){
        myExp1.analysis(p, sTable);
        myExp2.analysis(p, sTable);
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" >= ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}
