var express = require('express');
var router = express.Router();

/* GET Form #1. */
router.get('/', function(req, res, next) {
  res.render('index');
});

/* GET Form #1. */
router.get('/one', function(req, res, next) {
  res.render('form');
});

/* GET Form #1. */
router.get('/two', function(req, res, next) {
  res.render('formOne');
});

/* GET Form #1. */
router.get('/three', function(req, res, next) {
  res.render('formTwo');
});

module.exports = router;
