(function () {
  const executor = (executeOn, decorator) => decorator(executeOn);

  const breakOnC = toBreakOnC => toBreakOnC.split(/(?=c)/g);

  const upperAllAs = toUpperAllAs => {
    return {
      "originalString": toUpperAllAs,
      "modifiedString": toUpperAllAs.replaceAll('a', 'A'),
      "numberReplaced": (toUpperAllAs.match(/a/g) || []).length,
      "length": toUpperAllAs.length,
    };
  };

  console.log(`Executor one: ${executor("supercalifragilisticexpialidocious", breakOnC)}`);
  console.log(`Executor two:`);
  console.table(executor("supercalifragilisticexpialidocious", upperAllAs));
})()