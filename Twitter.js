/**
 * @providesModule Twitter
 *
 * Modifications:
 * Copyright (C) 2016 Sony Interactive Entertainment Inc.
 * Licensed under the MIT License. See the LICENSE file in the project root for license information.
 */
'use strict';

var { NativeModules } = require('react-native');
var SMXTwitter = NativeModules.SMXTwitter;

module.exports = {
  login: function (cb) {
    SMXTwitter.login(cb);
  },
  fetchProfile: function (cb) {
    SMXTwitter.fetchProfile(cb);
  },
  fetchTweet: function (options, cb) {
    SMXTwitter.fetchTweet(options, cb);
  },
  logOut: function () {
    SMXTwitter.logOut();
  },
  composeTweet: function (options, cb) {
    SMXTwitter.composeTweet(options, cb);
  }
};
