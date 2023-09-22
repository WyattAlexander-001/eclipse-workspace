package csi311.pro1.WyattBushman;

import java.util.LinkedList;

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

    public LinkedList<FunctionDefinitionNode> getFunctionDefinitions() {
        return functionDefinitions;
    }

    public LinkedList<BlockNode> getBeginBlocks() {
        return beginBlocks;
    }

    public LinkedList<BlockNode> getEndBlocks() {
        return endBlocks;
    }

    public LinkedList<BlockNode> getBlocks() {
        return blocks;
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
