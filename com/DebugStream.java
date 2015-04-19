package com;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.MessageFormat;

import com.data.Constants;

/**
 * @author Jeeeyul 2011. 11. 1. 오후 4:36:51
 * @since M1.10
 */
public class DebugStream extends PrintStream
{
    private static DebugStream INSTANCE;

    @SuppressWarnings("resource")
    public static void activate() throws FileNotFoundException
    {
        if (INSTANCE == null)
        {
            INSTANCE = (Constants.USE_LOGFILE) ? new DebugStream("moss_log") :
                       new DebugStream();
        }
        System.setOut(INSTANCE);
    }

    private DebugStream()
    {
        super(System.out);
    }

    private DebugStream(String filename) throws FileNotFoundException
    {
        super(new File(filename));
    }

    @Override
    public void println(Object x)
    {
        //showLocation();
        super.println(x);
    }

    @Override
    public void println(String x)
    {
        //showLocation();
        super.println(x);
    }

    @SuppressWarnings("unused")
    private void showLocation()
    {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        super.print(MessageFormat.format("({0}:{1, number,#}) : ", element.getFileName(),
                                         element.getLineNumber()));
    }
}