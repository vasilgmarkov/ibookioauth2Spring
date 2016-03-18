'use strict';

describe('Controller Tests', function() {

    describe('ApuestaRealizadas Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockApuestaRealizadas, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockApuestaRealizadas = jasmine.createSpy('MockApuestaRealizadas');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ApuestaRealizadas': MockApuestaRealizadas,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ApuestaRealizadasDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ibookioauth2App:apuestaRealizadasUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
