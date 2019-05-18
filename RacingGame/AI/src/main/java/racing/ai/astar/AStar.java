package racing.ai.astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import racing.common.data.entityparts.PositionPart;

/**
 *
 * @author Victor Gram & Niclas Johansen
 */
public class AStar  {
    private static int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
    private static int DEFAULT_DIAGONAL_COST = 14;
    private int hvCost;
    private int diagonalCost;
    private AStarNode[][] searchArea;
    private PriorityQueue<AStarNode> openList;
    private Set<AStarNode> closedSet;
    private AStarNode initialNode;
    private AStarNode finalNode;

    public AStar(int rows, int cols, AStarNode initialNode, AStarNode finalNode, int hvCost, int diagonalCost) {
        this.hvCost = hvCost;
        this.diagonalCost = diagonalCost;
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new AStarNode[rows][cols];
        this.openList = new PriorityQueue<AStarNode>(new Comparator<AStarNode>() {
            @Override
            public int compare(AStarNode node0, AStarNode node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        this.closedSet = new HashSet<>();
    }

    public AStar(int rows, int cols, AStarNode initialNode, AStarNode finalNode) {
        this(rows, cols, initialNode, finalNode, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
    }

    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                AStarNode node = new AStarNode(i, j);
                int w = searchArea[i][j].getW();
                node.setW(w);
                if(searchArea[i][j].isBlock()) { 
                    node.setBlock(true);
                }
                node.calculateHeuristic(getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }

     public void setBlocks(int[][] blocksArray) {
        for (int i = 0; i < blocksArray.length; i++) {
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            setBlock(row, col);
        }
    }

    
    public PositionPart findNextPosition() { 
        List<AStarNode> path = findPath();
        int positionIndex = 1;
        if(path.size() == 1) { 
            positionIndex = 0;
        }
        AStarNode nextPositionNode = path.get(positionIndex);
        float x = nextPositionNode.getRow();
        float y = nextPositionNode.getCol();
        PositionPart nextPosition = new PositionPart(x, y, (3.1415f / 2));
        return nextPosition;
    }
    
    private List<AStarNode> findPath() {
        openList.add(initialNode);
        while (!isEmpty(openList)) {
            AStarNode currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<>();
    }

    private List<AStarNode> getPath(AStarNode currentNode) {
        List<AStarNode> path = new ArrayList<>();
        path.add(currentNode);
        AStarNode parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(AStarNode currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(AStarNode currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int lowerRow = row + 1;
        if (lowerRow < getSearchArea().length) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }
            checkNode(currentNode, col, lowerRow, getHvCost());
        }
    }

    private void addAdjacentMiddleRow(AStarNode currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, middleRow, getHvCost());
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 1, middleRow, getHvCost());
        }
    }

    private void addAdjacentUpperRow(AStarNode currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            checkNode(currentNode, col, upperRow, getHvCost());
        }
    }

    private void checkNode(AStarNode currentNode, int col, int row, int cost) { 
        AStarNode adjacentNode = getSearchArea()[row][col];
        if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(AStarNode currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<AStarNode> openList) {
        return openList.size() == 0;
    }

    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    public AStarNode getInitialNode() {
        return initialNode;
    }

    public void setInitialPosition(int x, int y) {
        AStarNode initNode = new AStarNode(x,y);
        setInitialNode(initNode);
        
    }
    private void setInitialNode(AStarNode initialNode) {
        this.initialNode = initialNode;
    }

    public AStarNode getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(AStarNode finalNode) {
        this.finalNode = finalNode;
    }

    public AStarNode[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(AStarNode[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<AStarNode> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<AStarNode> openList) {
        this.openList = openList;
    }

    public Set<AStarNode> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<AStarNode> closedSet) {
        this.closedSet = closedSet;
    }

    public int getHvCost() {
        return hvCost;
    }

    public void setHvCost(int hvCost) {
        this.hvCost = hvCost;
    }

    private int getDiagonalCost() {
        return diagonalCost;
    }

    private void setDiagonalCost(int diagonalCost) {
        this.diagonalCost = diagonalCost;
    }
    
    public void setSourceNode(AStarNode source) { 
        setInitialNode(source);
        setFinalNode(getFinalNode());
        setNodes();
   }

}