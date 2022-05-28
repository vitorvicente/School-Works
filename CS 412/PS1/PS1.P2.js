(function () {
  const getOperatorFunction = expression => {
    let returnFunc = (left, right) => 0;
    switch (getValues(expression)[1]) {
      case "+":
        returnFunc = (left, right) => left + right;
        break;
      case "-":
        returnFunc = (left, right) => left - right;
        break;
      case "*":
        returnFunc = (left, right) => left * right;
        break;
      case "/":
        returnFunc = (left, right) => left / right;
        break;
      case "^":
        returnFunc = (left, right) => left ** right;
        break;
    }

    return (toParse) => returnFunc(getValues(toParse)[0], getValues(toParse)[2])
  };

  const getValues = expression => [parseInt(expression.charAt(0)),
                                   expression.charAt(1),
                                   parseInt(expression.charAt(2))];

  const addition = "4+2";
  const subtraction = "6-1";
  const multiplication = "5*7";
  const division = "9/2";
  const exponent = "2^8";

  const additionOp = getOperatorFunction(addition);
  const subtractionOp = getOperatorFunction(subtraction);
  const multiplicationOp = getOperatorFunction(multiplication);
  const divisionOp = getOperatorFunction(division);
  const exponentOp = getOperatorFunction(exponent);

  console.log(`${addition} = ${additionOp(addition)}`);
  console.log(`${subtraction} = ${subtractionOp(subtraction)}`);
  console.log(`${multiplication} = ${multiplicationOp(multiplication)}`);
  console.log(`${division} = ${divisionOp(division)}`);
  console.log(`${exponent} = ${exponentOp(exponent)}`);
})()