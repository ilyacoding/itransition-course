package com.ilyacoding;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.math.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilya on 27.6.17.
 */
public class Game {
    private List<String> OptionsList;
    private int ComputerAnswer;
    private int UserAnswer;
    private String Key;

    public Game()
    {
        OptionsList = new ArrayList<String>();
        OptionsList.add("камень");
        OptionsList.add("ножницы");
        OptionsList.add("бумага");
        //OptionsList.add("ящерица");
        //OptionsList.add("спок");
        generateAnswer();
    }

    private String getRandomString()
    {
        SecureRandom random = new SecureRandom();

        return new BigInteger(130, random).toString(32);
    }

    private void generateAnswer()
    {
        SecureRandom secureRandom = new SecureRandom();
        ComputerAnswer = secureRandom.nextInt(OptionsList.size());
        Key = getRandomString();
    }

    public boolean setUserAnswer(int i)
    {
        if (i < 0 || i >= OptionsList.size())
        {
            return false;
        }
        else
        {
            UserAnswer = i;
            return true;
        }
    }

    public void printOptions()
    {
        for (int i = 0; i < OptionsList.size(); i++)
        {
            System.out.println(Integer.toString(i) + ") " + OptionsList.get(i));
        }
    }

    private String getHash(String key, String data) throws Exception
    {
        String algo = "HmacSHA256";
        Mac mac = Mac.getInstance(algo);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), algo);
        mac.init(secretKey);
        return DatatypeConverter.printHexBinary(mac.doFinal(data.getBytes()));
    }

    public void printFairPlay()
    {
        System.out.println("Game hash: ");
        try
        {
            System.out.println(getHash(Key, String.valueOf(ComputerAnswer)));
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private void printKey()
    {
        System.out.println("Computer choice: " + Integer.toString(ComputerAnswer) + "; Key: " + Key);
        System.out.println("To prove you need to use HmacSHA256 algorithm.");
    }

    public Winner getWinner()
    {
        long d = (OptionsList.size() + UserAnswer - ComputerAnswer) % OptionsList.size();
        printKey();
        if (d == 0)
        {
            return Winner.DRAW;
        }
        if (d % 2 == 1)
        {
            return Winner.COMPUTER;
        }
        else if (d % 2 == 0)
        {
            return Winner.USER;
        }
        return null;
    }
}