'use strict';

angular.module('ibookioauth2App')
    .factory('ApuestaRealizadas', function ($resource, DateUtils) {
        return $resource('api/apuestaRealizadass/:id', {}, {
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
