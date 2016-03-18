'use strict';

angular.module('ibookioauth2App')
    .factory('Resultados', function ($resource, DateUtils) {
        return $resource('api/resultadoss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
