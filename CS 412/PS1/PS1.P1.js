(function () {
  const reverseAlphaSort = toReverseAlphaSort => [...toReverseAlphaSort].sort().reverse().join('');

  console.log(`Reverse Alphabetic Sorting for String "supercalifragilisticexpialidocious": ${reverseAlphaSort(
    "supercalifragilisticexpialidocious")}`);
})()