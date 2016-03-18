'use strict';

angular.module('ibookioauth2App')
	.controller('ApuestasDeleteController', function($scope, $uibModalInstance, entity, Apuestas) {

        $scope.apuestas = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Apuestas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
