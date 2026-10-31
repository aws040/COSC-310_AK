package project;

import structure.BinaryTree;

/**
 * Main class for making a Tennis Serving Outcome tree.
 * @author Alejandro Borroni
 * @version 2026.10.17.11
 */
public class TennisServingPuzzle {
    public final double ProbA;
    public final double ProbB;
    public BinaryTree<Round> TennisPuzzleOutcome;

    /**
     * Constructor that takes both the prob of A and B and an int that it will play the game too. both probs must be withing
     * 0 and 1 and the firstToN must be withing 1 and 8.
     * @param ProbA taken as server A prob to win on their serve
     * @param ProbB taken as server B prob to win on their serve
     * @param firstToN taken as an int and how far the game should go
     */
    public TennisServingPuzzle(double ProbA, double ProbB, int firstToN) {
        if ((ProbA > 1 || ProbA < 0) || (ProbB > 1 || ProbB < 0)) {
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        }
        this.ProbA = ProbA;
        this.ProbB = ProbB;
        if (firstToN >= 1 && firstToN <= 8) {
            playGame(firstToN);
        } else {
            throw new IllegalArgumentException("First to N must be between 1 and 8");
        }
    }

    /**
     * This method starts the game. It only takes the number that the game is going to.
     * @param firstToN taken to limit size of tree
     */
    private void playGame(int firstToN) {
        String server = "A";
        Round round = new Round(0, 0, server, 1, false, "");
        BinaryTree<Round> binTree = new BinaryTree<>(round);
        BinaryTree.BinTreeNode<Round> currentRound = binTree.getRoot();
        playGame(binTree, firstToN, server, round, currentRound);
        TennisPuzzleOutcome = binTree;
    }
    private void playGame(BinaryTree<Round> binTree, int depthMax, String server, Round currentRound, BinaryTree.BinTreeNode<Round> lastRound) {
        if (currentRound.aScore() < depthMax && currentRound.bScore() < depthMax) {
            double currProb = currentRound.probability();
            String nextServer = server.equals("A") ? "B" : "A";
            //A wins this branch
            Round aWin = new Round(currentRound.aScore() + 1, currentRound.bScore(), nextServer, server.equals("A") ? currProb * ProbA : currProb * (1 - ProbB), true, currentRound.sequenceWon() + "A");
            binTree.addLeftChild(lastRound, aWin);
            playGame(binTree, depthMax, server.equals("A") ? "B" : "A", aWin, lastRound.left());
            //B wins this branch
            Round bWin = new Round(currentRound.aScore(), currentRound.bScore() + 1, nextServer, server.equals("B") ? currProb * ProbB : currProb * (1 - ProbA), false, currentRound.sequenceWon() + "B");
            binTree.addRightChild(lastRound, bWin);
            playGame(binTree, depthMax, server.equals("A") ? "B" : "A", bWin, lastRound.right());
        }
    }

    /**
     * Method prints the game outcome tree by stating the probs for the player on their
     * serve and then printing every terminal path. While it is going through the terminal paths recursively, it is also
     * gathering the probability of the users winning and returns that to print.
     */
    public void printGame() {
        System.out.printf("A wins on own serve: %s%nB wins on own serve: %s%n", ProbA, ProbB);
        double aTotalProb = printGame(TennisPuzzleOutcome.getRoot());
        double bTotalProb = 1 - aTotalProb;
        System.out.printf("Total tree nodes: %d; Leaf nodes: %d%n", TennisPuzzleOutcome.count(), TennisPuzzleOutcome.countLeafs());
        System.out.printf("Probability A wins: %s%n", aTotalProb);
        System.out.printf("Probability B wins: %s%n", bTotalProb);
        System.out.printf("Sum of all terminal-path probabilities: %s%n", bTotalProb + aTotalProb);
    }

    private double printGame(BinaryTree.BinTreeNode<Round> currRound) {
        double finalProbability = 0.0;
        if (currRound.left() == null && currRound.right() == null) {
            Round temp = currRound.data();
            System.out.printf("Terminal path: %s, %s, %s%n", temp.sequenceWon(), temp.aScore() > temp.bScore() ? "A" : "B", temp.probability());
            return temp.aScore() > temp.bScore() ? temp.probability() : 0;
        }
        if (currRound.left() != null) {
            finalProbability += printGame(currRound.left());
        }
        if (currRound.right() != null) {
            finalProbability += printGame(currRound.right());
        }
        return finalProbability;
    }
}
