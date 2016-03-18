'use strict';

angular.module('ibookioauth2App')
    .factory('Apuestas', function ($resource, DateUtils) {
        return $resource('api/apuestass/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.partidoStartDate = DateUtils.convertLocaleDateFromServer(data.partidoStartDate);
                    data.partidoTime = DateUtils.convertLocaleDateFromServer(data.partidoTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.partidoStartDate = DateUtils.convertLocaleDateToServer(data.partidoStartDate);
                    data.partidoTime = DateUtils.convertLocaleDateToServer(data.partidoTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.partidoStartDate = DateUtils.convertLocaleDateToServer(data.partidoStartDate);
                    data.partidoTime = DateUtils.convertLocaleDateToServer(data.partidoTime);
                    return angular.toJson(data);
                }
            }
        });
    });
