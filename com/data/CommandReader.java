package com.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.Common;
import com.type.Command;
import com.type.Instruction;

public class CommandReader
{

    public static ArrayList<Instruction> readCommands(String commandFile, long addressLimit) throws NumberFormatException, IOException
    {
        ArrayList<Instruction> instructions = new ArrayList<>();

        HashMap<String, Integer> stringRadixMap = new HashMap<>();
        stringRadixMap.put("bin", 2);
        stringRadixMap.put("oct", 8);
        stringRadixMap.put("hex", 16);

        BufferedReader in = new BufferedReader(new FileReader(new File(commandFile)));
        String line = null;
        while ((line = in.readLine()) != null)
        {
            String[] tokens = line.split(" ");
            Command command = null;
            switch (tokens[0])
            {
            case "READ":
                command = Command.READ;
                break;
            case "WRITE":
                command = Command.WRITE;
                break;
            }
            if (command == null) continue;
            if (tokens[1].equals("random"))
            {
                instructions.add(new Instruction(command, Common.generateRandomLong(addressLimit)));
            }
            else
            {
                Integer radix = stringRadixMap.get(tokens[1]);
                long addr = (radix == null) ? Long.parseLong(tokens[1]) : Long.parseLong(tokens[2], radix);
                if (0 > addr || addr > addressLimit)
                {
                    System.err.println("MemoryManagement: " + addr + ", Address out of range in " + commandFile);
                    System.exit(-1);
                }
                instructions.add(new Instruction(command, addr));
            }
        }
        in.close();
        return instructions;
    }

}
