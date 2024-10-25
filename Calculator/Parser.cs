using System;

namespace Calculator;

public static class Parser
{

    public static void Main()
    {
        Console.WriteLine(Parser.Parse("1+2"));
    }

    private static string expression;

    private static int curIndex;

    public static bool IsBinaryOperation(char s)
    {

        switch (s)
        {
            case (char)Operation.Add:
            case (char)Operation.Subtract:
            case (char)Operation.Multiply:
            case (char)Operation.Divide:
                return true;
            default:
                return false;
        }
    }


    public static bool IsSymbol(char s)
    {
        switch (s)
        {
            case (char)Symbol.Space:
            case (char)Symbol.Tab:
            case (char)Symbol.NewLine:
            case (char)Symbol.CarriageReturn:
            case (char)Symbol.FormFeed:
                return true;
            default:
                return false;
        }
    }

    public static bool IsUnaryOperation(char s)
    {
        switch (s)
        {
            case (char)Operation.Add:
            case (char)Operation.Subtract:
                return true;
            default:
                return false;
        }
    }

    public static decimal Compile(string s)
    {
        expression = s;
        curIndex = 0;
        try
        {
            var result = ParseExpr();
            if (IsNotEnd())
            {
                return 555;
            }

            if (!result)
            {
                return 222;
            }

            Compiler.ExecuteMany((char)Operation.End);
            return Compiler.GetResult();
        }
        catch (Exception)
        {
            return 333;
        }
    }

    public static bool Parse(string s)
    {
        expression = s;
        curIndex = 0;
        try
        {
            // var result = ParseExpr();
            var result = ParseOperators();
            if (IsNotEnd())
            {
                return false;
            }
            return result;
        }
        catch (Exception)
        {
            return false;
        }
    }

    public static bool ParseOperators()
    {
        ParseAssign();
    }

    public static bool ParseAssign()
    {
        var name = ParseName();
        if (name == "")
        {
            return false;
        }

        if (!ParseChar('='))
        {
            return false;
        }

        ParseExpr();

        if (!ParseChar(';'))
        {
            throw new ApplicationException("Error");
        }
    }

    public static string ParseName()
    {
        Skip();

        var prevInd = curIndex;


        if (!IsNotEnd() || !(char.IsAsciiLetter(GetCurrentChar()) || GetCurrentChar() == '_'))
        {
            return "";
        }

        while (IsNotEnd() && (char.IsAsciiLetterOrDigit(GetCurrentChar()) || GetCurrentChar() == '_'))
        {
            curIndex++;
        }

        if (curIndex > prevInd)
        {
            return expression[prevInd..(curIndex - 1)];
        }

        return "";
    }


    public static bool ParseExpr()
    {
        ParseUnary();
        if (!ParseOperand())
        {
            return false;
        }

        while (ParseBinary())
        {
            if (!ParseOperand())
            {
                return false;
            }
        }

        return true;
    }

    public static bool ParseOperand()
    {
        if (ParseNum(out decimal? a))
        {
            if (a != null)
            {
                Compiler.PushNumber((decimal)a);
            }
            return true;
        }
        else
        {
            if (ParseVar())
            {
                return true;
            }
        }

        if (ParseChar((char)Operation.LeftBracket))
        {
            Compiler.PushOperation(Operation.LeftBracket);
            ParseExpr();
            if (ParseChar((char)Operation.RightBracket))
            {
                Compiler.ExecuteParanthesis();
                return true;
            }

            return false;
        }

        return false;
    }

    public static bool ParseVar()
    {
        Skip();

        var prevInd = curIndex;


        if (!IsNotEnd() || !(char.IsAsciiLetter(GetCurrentChar()) || GetCurrentChar() == '_'))
        {
            return false;
        }

        while (IsNotEnd() && (char.IsAsciiLetterOrDigit(GetCurrentChar()) || GetCurrentChar() == '_'))
        {
            curIndex++;
        }

        if (curIndex > prevInd)
        {
            var name = expression[prevInd..(curIndex - 1)];
            if (Variable.GetVariable(name) is null)
            {
                throw new ApplicationException($"Variable not found: {name}");
            }

            return true;
        }

        return false;
    }




    public static bool ParseNum(out decimal? a)
    {
        Skip();

        var prevInd = curIndex;

        while (IsNotEnd() && char.IsDigit(GetCurrentChar()))
        {
            curIndex++;
        }

        a = curIndex > prevInd ? int.Parse(expression[prevInd..curIndex]) : null;

        return curIndex > prevInd;
    }


    public static bool ParseChar(char symbol)
    {
        Skip();

        if (IsNotEnd() && GetCurrentChar().Equals(symbol))
        {
            curIndex++;
            return true;
        }

        return false;
    }


    public static bool ParseBinary()
    {
        Skip();

        if (IsNotEnd() && IsBinaryOperation(GetCurrentChar()))
        {
            Compiler.ExecuteMany(GetCurrentChar());
            Compiler.PushOperation(GetCurrentChar());
            curIndex++;
            return true;
        }

        return false;
    }


    private static void Skip()
    {
        while (IsNotEnd() && IsSymbol(GetCurrentChar()))
        {
            curIndex++;
        }

        if (curIndex < expression.Length - 1
            && GetCurrentChar().Equals((char)Operation.Divide)
            && expression[curIndex + 1].Equals((char)Operation.Divide))
        {
            curIndex += 2;
            while (IsNotEnd() && !GetCurrentChar().Equals((char)Symbol.NewLine))
            {
                curIndex++;
            }

            if (IsNotEnd())
            {
                curIndex++;
                Skip();
            }
        }
    }


    public static void ParseUnary()
    {
        Skip();

        if (IsNotEnd() && IsUnaryOperation(GetCurrentChar()))
        {
            if (GetCurrentChar() == (char)Operation.Subtract)
            {
                Compiler.PushOperation(Operation.UnaryMinus);
            }
            curIndex++;
        }
    }

    private static bool IsNotEnd()
    {
        return curIndex < expression.Length;
    }

    public static char GetCurrentChar()
    {
        return expression[curIndex];
    }
}