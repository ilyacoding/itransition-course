package com.ilyacoding;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GameHandler gameHandler = new GameHandler(new Game());
        gameHandler.Handle();
    }
}
