package csi311.pro1.WyattBushman;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

public class BlockNode extends Node {
    private LinkedList<StatementNode> statements; //A Block in AWK Can have multiple statements
    private Optional<Node> condition;

    public BlockNode() {
        statements = new LinkedList<>();
        condition = Optional.empty(); // No condition by default.
    }
    
    public LinkedList<StatementNode> getStatements() {
        return (LinkedList<StatementNode>) Collections.unmodifiableList(statements); //Ensure immutability for AST
    }

    public Optional<Node> getCondition() {
        return condition;
    }
    
    public void setCondition(Node condition) {
        this.condition = Optional.ofNullable(condition);
    }
    
    public void addStatement(StatementNode statement) {
        if(statement == null) {
            throw new IllegalArgumentException("Statement cannot be null");
        }
        statements.add(statement);
    }
    
    public void addAllStatements(Collection<StatementNode> stmts) {
        statements.addAll(stmts);
    }

    @Override
    public String toString() {
        StringBuilder blockRepresentation = new StringBuilder();

        // Append condition if present
        condition.ifPresent(cond -> blockRepresentation.append(cond.toString()).append(" "));

        blockRepresentation.append("{ ");

        // Append each statement
        for (StatementNode statement : statements) {
            blockRepresentation.append(statement.toString()).append("; ");
        }

        blockRepresentation.append("}");

        return blockRepresentation.toString();
    }

}

