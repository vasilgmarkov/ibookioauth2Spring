'use strict';

angular.module('ibookioauth2App').controller('ResultadosDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Resultados',
        function($scope, $stateParams, $uibModalInstance, entity, Resultados) {

        $scope.resultados = entity;
        $scope.load = function(id) {
            Resultados.get({id : id}, function(result) {
                $scope.resultados = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ibookioauth2App:resultadosUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.resultados.id != null) {
                Resultados.update($scope.resultados, onSaveSuccess, onSaveError);
            } else {
                Resultados.save($scope.resultados, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
