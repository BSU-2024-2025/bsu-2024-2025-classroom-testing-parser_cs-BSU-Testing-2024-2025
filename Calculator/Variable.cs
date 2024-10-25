using System;
using System.Collections;

namespace Calculator;

public class Variable
{

    public static Dictionary<string, object> variables = new();

    public static void AddVariable(string name, object value)
    {
        if (variables.ContainsKey(name))
        {
            variables[name] = value;
        }
        else
        {
            variables.Add(name, value);
        }
    }

    public static object? GetVariable(string name)
    {
        if (!variables.ContainsKey(name))
        {
            return null;
        }
        return variables[name];
    }
}
