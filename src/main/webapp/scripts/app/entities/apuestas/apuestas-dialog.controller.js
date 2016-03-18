'use strict';

angular.module('ibookioauth2App').controller('ApuestasDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Apuestas',
        function($scope, $stateParams, $uibModalInstance, entity, Apuestas) {

        $scope.apuestas = entity;
        $scope.load = function(id) {
            Apuestas.get({id : id}, function(result) {
                $scope.apuestas = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ibookioauth2App:apuestasUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.apuestas.id != null) {
                Apuestas.update($scope.apuestas, onSaveSuccess, onSaveError);
            } else {
                Apuestas.save($scope.apuestas, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForPartidoStartDate = {};

        $scope.datePickerForPartidoStartDate.status = {
            opened: false
        };

        $scope.datePickerForPartidoStartDateOpen = function($event) {
            $scope.datePickerForPartidoStartDate.status.opened = true;
        };
        $scope.datePickerForPartidoTime = {};

        $scope.datePickerForPartidoTime.status = {
            opened: false
        };

        $scope.datePickerForPartidoTimeOpen = function($event) {
            $scope.datePickerForPartidoTime.status.opened = true;
        };
}]);
