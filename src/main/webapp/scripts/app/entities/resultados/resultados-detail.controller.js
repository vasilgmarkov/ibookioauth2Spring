'use strict';

angular.module('ibookioauth2App')
    .controller('ResultadosDetailController', function ($scope, $rootScope, $stateParams, entity, Resultados) {
        $scope.resultados = entity;
        $scope.load = function (id) {
            Resultados.get({id: id}, function(result) {
                $scope.resultados = result;
            });
        };
        var unsubscribe = $rootScope.$on('ibookioauth2App:resultadosUpdate', function(event, result) {
            $scope.resultados = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
