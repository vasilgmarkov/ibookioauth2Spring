'use strict';

angular.module('ibookioauth2App')
    .controller('ApuestaRealizadasDetailController', function ($scope, $rootScope, $stateParams, entity, ApuestaRealizadas, User) {
        $scope.apuestaRealizadas = entity;
        $scope.load = function (id) {
            ApuestaRealizadas.get({id: id}, function(result) {
                $scope.apuestaRealizadas = result;
            });
        };
        var unsubscribe = $rootScope.$on('ibookioauth2App:apuestaRealizadasUpdate', function(event, result) {
            $scope.apuestaRealizadas = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
