'use strict';

angular.module('ibookioauth2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('resultados', {
                parent: 'entity',
                url: '/resultadoss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ibookioauth2App.resultados.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/resultados/resultadoss.html',
                        controller: 'ResultadosController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('resultados');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('resultados.detail', {
                parent: 'entity',
                url: '/resultados/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ibookioauth2App.resultados.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/resultados/resultados-detail.html',
                        controller: 'ResultadosDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('resultados');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Resultados', function($stateParams, Resultados) {
                        return Resultados.get({id : $stateParams.id});
                    }]
                }
            })
            .state('resultados.new', {
                parent: 'resultados',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/resultados/resultados-dialog.html',
                        controller: 'ResultadosDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    evento: null,
                                    ganador: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('resultados', null, { reload: true });
                    }, function() {
                        $state.go('resultados');
                    })
                }]
            })
            .state('resultados.edit', {
                parent: 'resultados',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/resultados/resultados-dialog.html',
                        controller: 'ResultadosDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Resultados', function(Resultados) {
                                return Resultados.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('resultados', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('resultados.delete', {
                parent: 'resultados',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/resultados/resultados-delete-dialog.html',
                        controller: 'ResultadosDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Resultados', function(Resultados) {
                                return Resultados.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('resultados', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
