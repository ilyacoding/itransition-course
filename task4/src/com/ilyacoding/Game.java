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
        OptionsList.add("stone");
        OptionsList.add("scissors");
        OptionsList.add("paper");
        OptionsList.add("lizard");
        OptionsList.add("spock");
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

    public int getComputerAnswer()
    {
        return ComputerAnswer;
    }

    public List<String> getOptionsList()
    {
        return OptionsList;
    }

    public String getKey()
    {
        return Key;
    }

    public String getGameHash(String key, String data) throws Exception
    {
        String algo = "HmacSHA256";
        Mac mac = Mac.getInstance(algo);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), algo);
        mac.init(secretKey);
        return DatatypeConverter.printHexBinary(mac.doFinal(data.getBytes()));
    }

    public Winner getWinner()
    {
        long d = (OptionsList.size() + UserAnswer - ComputerAnswer) % OptionsList.size();
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