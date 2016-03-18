'use strict';

angular.module('ibookioauth2App')
    .controller('ApuestasDetailController', function ($scope, $rootScope, $stateParams, entity, Apuestas) {
        $scope.apuestas = entity;
        $scope.load = function (id) {
            Apuestas.get({id: id}, function(result) {
                $scope.apuestas = result;
            });
        };
        var unsubscribe = $rootScope.$on('ibookioauth2App:apuestasUpdate', function(event, result) {
            $scope.apuestas = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
