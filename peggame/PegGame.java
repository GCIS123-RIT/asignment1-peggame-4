package peggame;

import java.util.Collection;

public interface PegGame {
    Collection<Move> getPossibleMoves();
    GameState getGameState();
    void makeMove(Move move) throws PegGameException;
}