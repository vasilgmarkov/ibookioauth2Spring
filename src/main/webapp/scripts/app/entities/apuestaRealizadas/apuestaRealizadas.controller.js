'use strict';

angular.module('ibookioauth2App')
    .controller('ApuestaRealizadasController', function ($scope, $state, ApuestaRealizadas, ParseLinks) {

        $scope.apuestaRealizadass = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ApuestaRealizadas.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.apuestaRealizadass = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.apuestaRealizadas = {
                cantidadApostada: null,
                cuota: null,
                eventoApostado: null,
                ganadorApuesta: null,
                id: null
            };
        };
    });
