package csi311.pro1.WyattBushman;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ProgramNode extends Node {
    private LinkedList<FunctionDefinitionNode> functionDefinitions;
    private LinkedList<BlockNode> beginBlocks;
    private LinkedList<BlockNode> endBlocks;
    private LinkedList<BlockNode> blocks;

    public ProgramNode() {
        functionDefinitions = new LinkedList<>();
        beginBlocks = new LinkedList<>();
        endBlocks = new LinkedList<>();
        blocks = new LinkedList<>();
    }

    public List<FunctionDefinitionNode> getFunctionDefinitions() {
        return Collections.unmodifiableList((List<FunctionDefinitionNode>) functionDefinitions);
    }

    public List<BlockNode> getBeginBlocks() {
        return Collections.unmodifiableList((List<BlockNode>) beginBlocks);
    }

    public List<BlockNode> getEndBlocks() {
        return Collections.unmodifiableList((List<BlockNode>) endBlocks);
    }

    public List<BlockNode> getBlocks() {
        return Collections.unmodifiableList((List<BlockNode>) blocks);
    }

    
    // Helper methods to add nodes to each list
    public void addFunctionDefinition(FunctionDefinitionNode functionDefinition) {
        this.functionDefinitions.add(functionDefinition);
    }

    public void addBeginBlock(BlockNode block) {
        this.beginBlocks.add(block);
    }

    public void addEndBlock(BlockNode block) {
        this.endBlocks.add(block);
    }

    public void addBlock(BlockNode block) {
        this.blocks.add(block);
    }
       
    @Override
    public String toString() {
        StringBuilder programRepresentation = new StringBuilder();

        // Append BEGIN blocks
        for (BlockNode block : beginBlocks) {
            programRepresentation.append("BEGIN ").append(block.toString()).append("\n");
        }

        // Append general blocks (those without specific BEGIN/END)
        for (BlockNode block : blocks) {
            programRepresentation.append(block.toString()).append("\n");
        }

        // Append function definitions
        for (FunctionDefinitionNode function : functionDefinitions) {
            programRepresentation.append(function.toString()).append("\n");
        }

        // Append END blocks
        for (BlockNode block : endBlocks) {
            programRepresentation.append("END ").append(block.toString()).append("\n");
        }

        return programRepresentation.toString();
    }



}
