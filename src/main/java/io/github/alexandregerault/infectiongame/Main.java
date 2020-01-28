package io.github.alexandregerault.infectiongame;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        if (args.length < 6) {
            System.out.println("Not enough arguments!");
            System.exit(1);
        }

        try {
            int width = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);
            int aheadMoves = Integer.parseInt(args[2]);
            int whiteDepth = Integer.parseInt(args[3]);
            int blackDepth = Integer.parseInt(args[4]);
            boolean alphaBeta = Boolean.parseBoolean(args[5]);

            GameNode node = new GameNode(width, height, Colors.WHITE);

            System.out.println(node.board().toString());

            //Coups d'avance du joueur blanc
            System.out.print("Le joueur blanc joue " + aheadMoves + " coup");
            if (aheadMoves > 1) System.out.print("s");
            System.out.println(" d'avance.");

            for (int i = 0; i < aheadMoves; i++) {
                Map<Move, Float> playerPossibilities = new HashMap<Move, Float>();
                // On crée l'arbre des scores de chaque mouvement
                for (Move m : node.board().allMoves(Colors.WHITE)) {
                    if(alphaBeta)
                        playerPossibilities.put(m, minmax(node, whiteDepth, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY));
                    else
                        playerPossibilities.put(m, minmax(node, whiteDepth));
                }

                //On sélectionne le coup au meilleur score
                Map.Entry<Move, Float> toPlay = null;
                for (Map.Entry<Move, Float>entry : playerPossibilities.entrySet()) {
                    if (toPlay == null || entry.getValue().compareTo(toPlay.getValue()) > 0) {
                        toPlay = entry;
                    }
                }

                node = node.newState(toPlay.getKey(), Colors.WHITE);
                System.out.print(node.board().toString());
                System.out.println("----------------------------------");
            }

            System.out.println("La partie commence");
            //On lance la boucle
            while (! node.board().gameOver(node.player())) {
                Map<Move, Float> playerPossibilities = new HashMap<Move, Float>();

                // On crée l'arbre des scores de chaque mouvement
                for (Move m : node.board().allMoves(node.player())) {
                    if(alphaBeta)
                        playerPossibilities.put(m, minmax(node, node.player() == Colors.BLACK ? blackDepth : whiteDepth, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY));
                    else
                        playerPossibilities.put(m, minmax(node, node.player() == Colors.BLACK ? blackDepth : whiteDepth));
                }

                //On sélectionne le coup au meilleur score
                Map.Entry<Move, Float> toPlay = null;
                for (Map.Entry<Move, Float>entry : playerPossibilities.entrySet()) {
                    if (toPlay == null || entry.getValue().compareTo(toPlay.getValue()) > 0) {
                        toPlay = entry;
                    }
                }

                node = node.newState(toPlay.getKey());
                System.out.print(node.board().toString());
                System.out.println("----------------------------------");
            }

            System.out.println("Fin du jeu. Joueur perdant : " + node.player().toString());


        } catch (NumberFormatException e) {
            System.out.println("One of the first five parameters is not a number. Please re-run the program with correct inputs");
        }
    }


    /**
     * A basic implementation of the minmax algorithm without alpha-beta pruning
     *
     * @param node_   The node we're evaluating
     * @param depth_  The reasoning depth
     * @return evaluation (recursive function)
     */
    public static float minmax(GameNode node_, int depth_) {
        if (depth_ == 0 || node_.board().gameOver(node_.player())) {
            return node_.board().evaluate(node_.player());
        }

        if (node_.player().equals(Colors.WHITE)) {
            float maxEvaluation = Float.NEGATIVE_INFINITY;

            for (Move move : node_.board().allMoves(node_.player())) {
                float evaluation = minmax(node_.newState(move), depth_ - 1);

                maxEvaluation = Math.max(maxEvaluation, evaluation);
            }

            return maxEvaluation;
        } else {
            float minEvaluation = Float.POSITIVE_INFINITY;

            for (Move move : node_.board().allMoves(node_.player())) {
                float evaluation = minmax(node_.newState(move), depth_ - 1);

                minEvaluation = Math.min(minEvaluation, evaluation);
            }

            return minEvaluation;
        }
    }

    /**
     * A basic implementation of the minmax algorithm with alpha-beta pruning
     *
     * @param node_   The node we're evaluating
     * @param depth_  The reasoning depth
     * @param alpha_  Alpha from pruning algorithm
     * @param beta_   Beta from pruning algorithm
     * @return evaluation
     */
    public static float minmax(GameNode node_, int depth_, float alpha_, float beta_) {
        if (depth_ == 0 || node_.board().gameOver(node_.player())) {
            return node_.board().evaluate(node_.player());
        }

        if (node_.player().equals(Colors.WHITE)) {
            float maxEvaluation = Float.NEGATIVE_INFINITY;
            float alpha;

            for (Move move : node_.board().allMoves(node_.player())) {
                float evaluation = minmax(node_.newState(move), depth_ - 1, alpha_, beta_);

                maxEvaluation = Math.max(maxEvaluation, evaluation);
                alpha = Math.max(alpha_, evaluation);

                if (beta_ <= alpha) {
                    break;
                }
            }

            return maxEvaluation;
        } else {
            float minEvaluation = Float.POSITIVE_INFINITY;
            float beta;

            for (Move move : node_.board().allMoves(node_.player())) {
                float evaluation = minmax(node_.newState(move), depth_ - 1, alpha_, beta_);

                minEvaluation = Math.min(minEvaluation, evaluation);

                beta = Math.min(beta_, evaluation);

                if (beta <= alpha_) {
                    break;
                }
            }

            return minEvaluation;
        }
    }
}
