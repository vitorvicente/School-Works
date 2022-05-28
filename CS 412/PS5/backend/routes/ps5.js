const express = require('express');
const request = require('request');
const router = express.Router();

/* GET Form */
router.get('/exchangeRequest/:baseCurrency/:requestedCurrency', function(req, res, next) {
  const promiseToGet = (base, requested) => new Promise((resolve, reject) => {
    const options = {
      method: 'GET',
      url: 'https://v6.exchangerate-api.com/v6/c4d71bb1a06bc56e4c40d209/pair/' + base + '/' + requested,
    };

    request (options, (error, response, body) => {
      if (error) {
        reject(error);
      } else {
        resolve(body);
      }
    });

  });

  promiseToGet(req.params.baseCurrency, req.params.requestedCurrency).then(
    (body) => {
      const convertedBody = JSON.parse(body)
      res.header('Access-Control-Allow-Origin', '*');
      res.json(convertedBody);
    }, (error) => {
      console.error('error:', error);
      res.header('Access-Control-Allow-Origin', '*');
      res.json({"error": error});
    });
});


module.exports = router;
