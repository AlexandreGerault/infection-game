package io.github.alexandregerault.infectiongame;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @Test
    void pawns() {
        GameBoard board = new GameBoard(4, 4);

        assertEquals(1, board.pawns(Colors.BLACK), "Le nombre de pions du joueur noir n'est pas égal à 1 lors de la création du plateau");
        assertEquals(1, board.pawns(Colors.WHITE), "Le nombre de pions du joueur blanc n'est pas égal à 1 lors de la création du plateau");
    }

    @Test
    void width() {
        GameBoard board = new GameBoard(4, 5);

        assertEquals(4, board.width());
    }

    @Test
    void height() {
        GameBoard board = new GameBoard(5, 4);

        assertEquals(4, board.height());
    }

    @Test
    void isSquareFree() {
        GameBoard board = new GameBoard(4, 4);

        assertFalse(board.isSquareFree(new Point(0, 0)));
        assertTrue(board.isSquareFree(new Point(1, 0)));
    }

    @Test
    void evaluate() {
    	GameBoard board = new GameBoard(4, 4);
    	
    	assertEquals(0.5, board.evaluate(Colors.BLACK));
    	assertEquals(0.5, board.evaluate(Colors.WHITE));
    }

    @Test
    void gameOver() {
    	GameBoard board = new GameBoard(2, 2);
    	
    	board.addPawn(new Point(0,1), Colors.BLACK);
    	board.addPawn(new Point(1,1), Colors.BLACK);
    	
    	assertTrue(board.gameOver(Colors.WHITE));
    }

    @Test
    void movePossible() {
        GameBoard board1 = new GameBoard(1, 2);

        Move cantMoveOnOther = new Move(Directions.UP, 1, new Point(0, 0));

        assertFalse(board1.movePossible(cantMoveOnOther), "Un pion ne doit pas pouvoir écraser un autre pion ! movePossible() doit renvoyer false ici !");


        GameBoard board2 = new GameBoard(4, 4);

        Move impossibleMove = new Move(Directions.UP, 1, new Point(0, 0));
        Move possibleMove = new Move(Directions.DOWN, 1, new Point(0, 0));

        assertFalse(board2.movePossible(impossibleMove), "Le pion sort du plateau avec ce coup donc doit movePossible() doit retourner false !");
        assertTrue(board2.movePossible(possibleMove), "Le pion est toujours dans le plateau et n'est gêné par aucun obstacle donc movePossible doit retourner true !");
    }

    @Test
    void moveInBounds() {
        GameBoard board = new GameBoard(4, 5);

        Move outMove = new Move(Directions.UP, 1, new Point(0, 0));
        Move inMove = new Move(Directions.DOWN, 1, new Point(0, 0));

        assertFalse(board.moveInBounds(outMove), "Le pion sort du plateau avec ce coup donc doit movePossible() doit retourner false !");
        assertTrue(board.moveInBounds(inMove), "Le pion est toujours dans le plateau et n'est gêné par aucun obstacle donc movePossible doit retourner true !");
    }

    @Test
    void allMoves() {
        GameBoard board = new GameBoard(2, 2);

        Set<Move> allBlackKnownMoves = new HashSet<>();
        allBlackKnownMoves.add(new Move(Directions.DOWN, 1, new Point(0,0)));
        allBlackKnownMoves.add(new Move(Directions.RIGHT, 1, new Point(0,0)));

        Set<Move> allWhiteKnownMoves = new HashSet<>();
        allWhiteKnownMoves.add(new Move(Directions.UP, 1, new Point(0,0)));
        allWhiteKnownMoves.add(new Move(Directions.LEFT, 1, new Point(0,0)));

        assertEquals(allBlackKnownMoves.size(), board.allMoves(Colors.BLACK).size());
        assertEquals(allWhiteKnownMoves.size(), board.allMoves(Colors.WHITE).size());
    }

    @Test
    void infectAdjacentCells() {
    	GameBoard board = new GameBoard(2,1);
    	
    	board.infectAdjacentCells(new Point(0,0));
    	
    	assertEquals(Colors.BLACK, board.pawnAt(new Point(0,0)));
    	assertEquals(Colors.BLACK, board.pawnAt(new Point(1,0)));
    }

    @Test
    void addPawn() {
    	GameBoard board = new GameBoard(3,3);
    	
    	board.addPawn(new Point(1,1), Colors.BLACK);
    	
    	assertEquals(Colors.BLACK, board.pawnAt(new Point(1,1)));
    }

    @Test
    void movePawn() {
    	GameBoard board = new GameBoard(3,3);
    	
    	board.movePawn(new Move(Directions.DOWN, 2, new Point(0,0)));
    	
    	assertEquals(null, board.pawnAt(new Point(0,0)));
    	assertEquals(Colors.BLACK, board.pawnAt(new Point(0,2)));
    }

    @Test
    void testToString() {
    }
}