'use strict';

angular.module('ibookioauth2App')
	.controller('ApuestaRealizadasDeleteController', function($scope, $uibModalInstance, entity, ApuestaRealizadas) {

        $scope.apuestaRealizadas = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ApuestaRealizadas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
