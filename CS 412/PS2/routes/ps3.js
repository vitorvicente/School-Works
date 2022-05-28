var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'PS3 Home Route', string: "Welcome!" });
});

/* GET Simple String. */
router.get('/stringOnly', function(req, res, next) {
  res.render('index', { string: 'Hey now' });
});

/* POST String on Request. */
router.post('/stringFromRequest', function(req, res, next) {
  const response = req.body.hasOwnProperty("string") ? req.body.string : "Default String";
  res.render('index', { string: response, length: response.length });
});

/* GET String on Request. (DEFAULT TO CATCH BAD GET REQUESTS) */
router.get('/stringFromRequest/', function(req, res, next) {
  const response = "Default String";
  res.render('index', { string: response, length: response.length });
});

/* GET String on Request. */
router.get('/stringFromRequest/:string', function(req, res, next) {
  res.render('index', { string: req.params.string, length: req.params.string.length });
});

module.exports = router;
