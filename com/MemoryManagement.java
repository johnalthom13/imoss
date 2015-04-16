package com;

// The main MemoryManagement program, created by Alexander Reeder, 2000 Nov 19

import java.io.File;

import javax.swing.JFrame;

import com.ui.ControlPanel;

public class MemoryManagement
{
    public static void main(String[] args)
    {
        if ( args.length < 1 || args.length > 2 )
        {
            System.out.println( "Usage: 'java MemoryManagement <COMMAND FILE> <PROPERTIES FILE>'" );
            System.exit( -1 );
        }

        File f = new File( args[0] );

        if ( ! ( f.exists() ) )
        {
            System.err.println( "MemoryM: error, file '" + f.getName() + "' does not exist." );
            System.exit( -1 );
        }
        if ( ! ( f.canRead() ) )
        {
            System.err.println( "MemoryM: error, read of " + f.getName() + " failed." );
            System.exit( -1 );
        }

        if ( args.length == 2 )
        {
            f = new File( args[1] );

            if ( ! ( f.exists() ) )
            {
                System.err.println( "MemoryM: error, file '" + f.getName() + "' does not exist." );
                System.exit( -1 );
            }
            if ( ! ( f.canRead() ) )
            {
                System.err.println( "MemoryM: error, read of " + f.getName() + " failed." );
                System.exit( -1 );
            }
        }
        
        JFrame x = new JFrame();
        x.add(new ControlPanel(args[0] , args[1]));
        x.pack();
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        x.setVisible(true);
    }
}
