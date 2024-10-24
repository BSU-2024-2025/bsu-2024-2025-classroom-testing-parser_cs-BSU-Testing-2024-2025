using NUnit.Framework;

namespace Calculator.Test
{
    public class CompileExpressionTests
    {
        [TestCase("1+2", ExpectedResult = 3d)]
        [TestCase("1-2", ExpectedResult = -1d)]
        [TestCase("1*2", ExpectedResult = 2d)]
        [TestCase("1/2", ExpectedResult = 0.5d)]
        [TestCase("1+2*3", ExpectedResult = 7d)]
        [TestCase("1-2*3", ExpectedResult = -5d)]
        [TestCase("1*2+3", ExpectedResult = 5d)]
        [TestCase("1*2-3", ExpectedResult = -1d)]
        public decimal TestNoParentheses(string expression)
        {
            // TODO - 1. add more test cases 2. add also test with unary operation
            return Parser.Compile(expression);
        }

        [TestCase("(1+2)", ExpectedResult = 3d)]
        [TestCase("(1-2)", ExpectedResult = -1d)]
        [TestCase("(1*2)", ExpectedResult = 2d)]
        [TestCase("(1/2)", ExpectedResult = 0.5d)]
        [TestCase("(1+2)*3", ExpectedResult = 9d)]
        [TestCase("(1-2)*3", ExpectedResult = -3d)]
        [TestCase("3+(1*2)", ExpectedResult = 5d)]
        [TestCase("3*(2+3)", ExpectedResult = 15d)]
        [TestCase("3-(1*2+4-3*4)", ExpectedResult = 9d)]
        public decimal TestWithParentheses(string expression)
        {
            return Parser.Compile(expression);
        }
    }
}