const fetch = require('node-fetch');
const express = require('express');
const request = require('request');
const router = express.Router();

/* GET Form #1. */
router.post('/getCurrencyOne', function(req, res, next) {
  const promiseToGet = (base, requested) => new Promise((resolve, reject) => {
    const options = {
      method: 'GET',
      url: 'https://v6.exchangerate-api.com/v6/c4d71bb1a06bc56e4c40d209/pair/' + base + '/' + requested,
    };

    request (options, (error, response, body) => {
      console.log('statusCode:', response && response.statusCode);
      if (error) {
        reject(error);
      } else {
        resolve(body);
      }
    });

  });

  promiseToGet(req.body.base, req.body.requested).then(
    (body) => {
      const convertedBody = JSON.parse(body)
      console.log('Conversion Rate:', convertedBody.conversion_rate);
      res.render('converted', {requestedCurrency: req.body.base, wantedCurrency: req.body.requested, conversionRate: convertedBody.conversion_rate});
    }, (error) => {
      console.error('error:', error);
      res.render('index');
    });
});

/* GET Form #2. */
router.post('/getCurrencyTwo', async function(req, res, next) {
  const asyncToGet = async (base, requested) => {
    let returnVal = await fetch('https://v6.exchangerate-api.com/v6/c4d71bb1a06bc56e4c40d209/pair/' + base + '/' + requested);
    console.log('statusCode:', returnVal && returnVal.statusCode);
    return returnVal;
  }

  asyncToGet(req.body.base, req.body.requested).then(async (body) => {
    const convertedBody = await body.json()
    res.render('converted', {requestedCurrency: req.body.base, wantedCurrency: req.body.requested, conversionRate: convertedBody.conversion_rate});
  }).catch((error) => {
    console.error('error:', error);
    res.render('index');
  })
});

/* GET Form #3. */
router.post('/getCurrencyThree', function(req, res, next) {
  const callbackToGet = (resolve, reject, base, requested) => {
    const options = {
      method: 'GET',
      url: 'https://v6.exchangerate-api.com/v6/c4d71bb1a06bc56e4c40d209/pair/' + base + '/' + requested,
    };

    request (options, (error, response, body) => {
      console.log('statusCode:', response && response.statusCode);
      if (error) {
        reject(error);
      } else {
        resolve(body);
      }
    });

  };

  callbackToGet(
    (body) => {
      const convertedBody = JSON.parse(body)
      res.render('converted', {requestedCurrency: req.body.base, wantedCurrency: req.body.requested, conversionRate: convertedBody.conversion_rate});
    }, (error) => {
      console.error('error:', error);
      res.render('index');
    },
    req.body.base,
    req.body.requested
  );
});

module.exports = router;
