package com.type;

import com.data.Constants;

public class Instruction
{
    public Instruction(Command command, long addr)
    {
        this.instructionCode_ = command;
        this.address_ = addr;
    }

    public long getAddress()
    {
        return address_;
    }

    public String getAddressStr()
    {
        return Long.toString(address_, Constants.ADDRESS_RADIX);
    }

    public String getInstructionCode()
    {
        // TODO Should return enum type
        return instructionCode_.name();
    }

    public boolean isRead()
    {
        return (instructionCode_ == Command.READ);
    }

    public boolean isWrite()
    {
        return (instructionCode_ == Command.WRITE);
    }

    public String toString()
    {
        return instructionCode_ + " " + getAddressStr();
    }

    private long address_;
    private Command instructionCode_;


}
