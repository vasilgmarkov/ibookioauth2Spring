'use strict';

angular.module('ibookioauth2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('apuestas', {
                parent: 'entity',
                url: '/apuestass',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ibookioauth2App.apuestas.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/apuestas/apuestass.html',
                        controller: 'ApuestasController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('apuestas');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('apuestas.detail', {
                parent: 'entity',
                url: '/apuestas/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ibookioauth2App.apuestas.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/apuestas/apuestas-detail.html',
                        controller: 'ApuestasDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('apuestas');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Apuestas', function($stateParams, Apuestas) {
                        return Apuestas.get({id : $stateParams.id});
                    }]
                }
            })
            .state('apuestas.new', {
                parent: 'apuestas',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/apuestas/apuestas-dialog.html',
                        controller: 'ApuestasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    ligaName: null,
                                    partidoStartDate: null,
                                    partidoTime: null,
                                    apuestaName: null,
                                    aApostarOdd: null,
                                    aApostarName: null,
                                    tipoDeporte: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('apuestas', null, { reload: true });
                    }, function() {
                        $state.go('apuestas');
                    })
                }]
            })
            .state('apuestas.edit', {
                parent: 'apuestas',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/apuestas/apuestas-dialog.html',
                        controller: 'ApuestasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Apuestas', function(Apuestas) {
                                return Apuestas.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('apuestas', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('apuestas.delete', {
                parent: 'apuestas',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/apuestas/apuestas-delete-dialog.html',
                        controller: 'ApuestasDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Apuestas', function(Apuestas) {
                                return Apuestas.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('apuestas', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
