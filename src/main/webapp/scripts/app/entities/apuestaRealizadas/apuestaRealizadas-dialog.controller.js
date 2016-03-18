'use strict';

angular.module('ibookioauth2App').controller('ApuestaRealizadasDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ApuestaRealizadas', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, ApuestaRealizadas, User) {

        $scope.apuestaRealizadas = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            ApuestaRealizadas.get({id : id}, function(result) {
                $scope.apuestaRealizadas = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ibookioauth2App:apuestaRealizadasUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.apuestaRealizadas.id != null) {
                ApuestaRealizadas.update($scope.apuestaRealizadas, onSaveSuccess, onSaveError);
            } else {
                ApuestaRealizadas.save($scope.apuestaRealizadas, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
