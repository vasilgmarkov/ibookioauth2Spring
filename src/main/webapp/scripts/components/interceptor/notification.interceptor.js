 'use strict';

angular.module('ibookioauth2App')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-ibookioauth2App-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-ibookioauth2App-params')});
                }
                return response;
            }
        };
    });
