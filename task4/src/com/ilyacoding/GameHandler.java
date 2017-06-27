package com.ilyacoding;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ilya on 27.6.17.
 */
public class GameHandler {
    private Game Game;

    public GameHandler(Game game)
    {
        Game = game;
    }

    public void Handle()
    {
        printOptions();
        printFairPlay();
        int userAnswer = -1;
        while (!Game.setUserAnswer(userAnswer))
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

        switch (Game.getWinner())
        {
            case USER:
                System.out.println("\nYou won! Congratulations!\n");
                break;
            case COMPUTER:
                System.out.println("\nComputer won. :(\n");
                break;
            case DRAW:
                System.out.println("\nDraw! Nobody won.\n");
                break;
        }

        printKey();
    }

    private void printKey()
    {
        System.out.println("Computer choice: " + Integer.toString(Game.getComputerAnswer()) + "; Key: " + Game.getKey());
        System.out.println("To prove you need to use HmacSHA256 algorithm.");
        System.out.println("Link: https://www.freeformatter.com/hmac-generator.html");
    }

    public void printFairPlay()
    {
        System.out.println("Game hash: ");
        try
        {
            System.out.println(Game.getGameHash(Game.getKey(), String.valueOf(Game.getComputerAnswer())));
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void printOptions()
    {
        List<String> optionsList = Game.getOptionsList();
        for (int i = 0; i <optionsList.size(); i++)
        {
            System.out.println(Integer.toString(i) + ") " + optionsList.get(i));
        }
    }
}
