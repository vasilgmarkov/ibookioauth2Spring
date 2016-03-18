'use strict';

angular.module('ibookioauth2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('apuestaRealizadas', {
                parent: 'entity',
                url: '/apuestaRealizadass',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ibookioauth2App.apuestaRealizadas.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/apuestaRealizadas/apuestaRealizadass.html',
                        controller: 'ApuestaRealizadasController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('apuestaRealizadas');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('apuestaRealizadas.detail', {
                parent: 'entity',
                url: '/apuestaRealizadas/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ibookioauth2App.apuestaRealizadas.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/apuestaRealizadas/apuestaRealizadas-detail.html',
                        controller: 'ApuestaRealizadasDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('apuestaRealizadas');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ApuestaRealizadas', function($stateParams, ApuestaRealizadas) {
                        return ApuestaRealizadas.get({id : $stateParams.id});
                    }]
                }
            })
            .state('apuestaRealizadas.new', {
                parent: 'apuestaRealizadas',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/apuestaRealizadas/apuestaRealizadas-dialog.html',
                        controller: 'ApuestaRealizadasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    cantidadApostada: null,
                                    cuota: null,
                                    eventoApostado: null,
                                    ganadorApuesta: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('apuestaRealizadas', null, { reload: true });
                    }, function() {
                        $state.go('apuestaRealizadas');
                    })
                }]
            })
            .state('apuestaRealizadas.edit', {
                parent: 'apuestaRealizadas',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/apuestaRealizadas/apuestaRealizadas-dialog.html',
                        controller: 'ApuestaRealizadasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ApuestaRealizadas', function(ApuestaRealizadas) {
                                return ApuestaRealizadas.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('apuestaRealizadas', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('apuestaRealizadas.delete', {
                parent: 'apuestaRealizadas',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/apuestaRealizadas/apuestaRealizadas-delete-dialog.html',
                        controller: 'ApuestaRealizadasDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ApuestaRealizadas', function(ApuestaRealizadas) {
                                return ApuestaRealizadas.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('apuestaRealizadas', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
