package com.miracosta.cs210.cs210.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperBoardTest {

    /**
     * Could theoretically fail by chance, but odds are 1 in 64C10 = 151,473,214,816
     */
    @Test
    void randomizationTest() {
        MinesweeperBoard board1 = new MinesweeperBoard(10);
        MinesweeperBoard board2 = new MinesweeperBoard(10);
        System.out.println(board1.getBombMap());
        System.out.println("\n++++++++\n");
        System.out.println(board2.getBombMap());
        assertNotEquals(board1, board2);
    }


}