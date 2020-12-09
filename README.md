# Compilers
CS 536 Introduction to Compilers, Spring 2020

Wumbo: name of language defined by the course instructor

## Projects 
### p1: Symbol Table (unavailable)
- Creates a Symbol Table (Symtable) to store symbols (Sym) for each local scope and for the global scope
- Checks for any duplicates within the scopes before adding a Syms to the Symtable of that scope
- Symtables can be added or removed

### p2: Scanner (partner programming, unavailable)
- Groups characters from input files into tokens and writes the tokens into an output file 
- Tokens are defined by regular expressions in Wumbo.jlex

### p3: Parser
- Builds an Abstract Syntax Tree to group tokens into sentences by adding tokens to their corresponding node in ASTnode class
- ast.java includes ASTnode class which defines the nodes that represent the Wumbo program
- Wumbo.cup is the Java CUP specification for the parser

### p4: Name analysis
- Semantic analysis with emphasis on name analysis
- Checks for errors such as undeclared identifiers and multiply declared identifiers within the local and global scope

### p5: Type check
- Stores the type of the declared variables, functions, and structs and checks if they are correctly used as operators and operands
- Outputs error messages to a text file for each instance that operators or operands are utilized wrong

### p6: Code generation
- Generates machine code compatible with MIPS for programs written in the Wumbo language
- Utilizes constants and operations defined in Codegen.java
