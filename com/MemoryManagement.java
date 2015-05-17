package com;

// The main MemoryManagement program, created by Alexander Reeder, 2000 Nov 19

import java.io.File;
import java.io.FileNotFoundException;

import com.data.CommandReader;
import com.ui.InitSettingsDialog;

public class MemoryManagement
{
    public static void main(String[] args) throws FileNotFoundException
    {
        if (args.length != 1)
        {
            System.out.println( "Usage: 'java MemoryManagement <COMMAND FILE>'" );
            System.exit( -1 );
        }

        File f = new File( args[0] );

        if (!(f.exists()))
        {
            System.err.println( "MemoryM: error, file '" + f.getName() + "' does not exist." );
            System.exit( -1 );
        }
        if (!(f.canRead()))
        {
            System.err.println( "MemoryM: error, read of " + f.getName() + " failed." );
            System.exit( -1 );
        }

        DebugStream.activate();
        CommandReader.FILENAME = args[0];
        InitSettingsDialog.display();
    }
}
