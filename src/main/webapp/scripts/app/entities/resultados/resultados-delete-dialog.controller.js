'use strict';

angular.module('ibookioauth2App')
	.controller('ResultadosDeleteController', function($scope, $uibModalInstance, entity, Resultados) {

        $scope.resultados = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Resultados.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
