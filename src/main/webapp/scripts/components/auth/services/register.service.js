'use strict';

angular.module('ibookioauth2App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


