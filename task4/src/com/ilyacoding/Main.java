package com.ilyacoding;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.printOptions();
        game.printFairPlay();
        int userAnswer = -1;
        while (!game.setUserAnswer(userAnswer))
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter choice: ");
            try
            {
                userAnswer = scanner.nextInt();
            }
            catch (Exception e)
            {
                continue;
            }
        }

        switch (game.getWinner())
        {
            case USER:
                System.out.println("\nYou won! Congratulations!");
                break;
            case COMPUTER:
                System.out.println("\nComputer won. :(");
                break;
            case DRAW:
                System.out.println("\nDraw! Nobody won.");
                break;
        }
    }
}
