'use strict';

describe('Controller Tests', function() {

    describe('Apuestas Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockApuestas;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockApuestas = jasmine.createSpy('MockApuestas');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Apuestas': MockApuestas
            };
            createController = function() {
                $injector.get('$controller')("ApuestasDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ibookioauth2App:apuestasUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
