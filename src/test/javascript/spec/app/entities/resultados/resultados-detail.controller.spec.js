'use strict';

describe('Controller Tests', function() {

    describe('Resultados Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResultados;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResultados = jasmine.createSpy('MockResultados');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Resultados': MockResultados
            };
            createController = function() {
                $injector.get('$controller')("ResultadosDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ibookioauth2App:resultadosUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
