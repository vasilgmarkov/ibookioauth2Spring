'use strict';

angular.module('ibookioauth2App')
    .controller('ApuestasController', function ($scope, $state, Apuestas, ParseLinks) {

        $scope.apuestass = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Apuestas.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.apuestass = result;
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
            $scope.apuestas = {
                ligaName: null,
                partidoStartDate: null,
                partidoTime: null,
                apuestaName: null,
                aApostarOdd: null,
                aApostarName: null,
                tipoDeporte: null,
                id: null
            };
        };
    });
