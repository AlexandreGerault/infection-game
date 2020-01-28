package io.github.alexandregerault.infectiongame;

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

            Game game = new Game(new Player(Colors.BLACK, blackDepth), new Player(Colors.WHITE, whiteDepth), alphaBeta, width, height, aheadMoves);

            game.aheadMoves();            
            game.loop();


        } catch (NumberFormatException e) {
            System.out.println("One of the first five parameters is not a number. Please re-run the program with correct inputs");
        }
    }
}
