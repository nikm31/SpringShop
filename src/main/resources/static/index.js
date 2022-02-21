(function () {
    angular
        .module('store', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: '/main_page/main_page.html',
                controller: 'mainPageController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/add_product', {
                templateUrl: 'add_product/add_product.html',
                controller: 'addProductController'
            })
            .when('/edit_product/:productId', {
                templateUrl: 'edit_product/edit_product.html',
                controller: 'editProductController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/favorite_products', {
                templateUrl: 'favorite_products/favorite_products.html',
                controller: 'favoriteProductsController'
            })
            .when('/order_confirmation', {
                templateUrl: 'checkout/checkout.html',
                controller: 'checkoutController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .when('/register_user', {
                templateUrl: 'register_user/register_user.html',
                controller: 'registerUserController'
            })
            .when('/account', {
                templateUrl: 'account/account.html',
                controller: 'accountController'
            })
            .when('/account/edit_user_info', {
                templateUrl: 'account/edit_user_info/edit_user_info.html',
                controller: 'editUserController'
            })
            .when('/lost_password', {
                templateUrl: 'lost_password/lost_password.html',
                controller: 'lostPasswordController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        $localStorage.contextPath = 'http://127.0.0.1:8080';
        if ($localStorage.webMarketUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webMarketUser.token;
        }
        console.log($localStorage.contextPath + '/api/v1/cart/generate');
        if (!$localStorage.webMarketGuestCartId) {
            $http.get($localStorage.contextPath + '/api/v1/cart/generate')
                .then(function successCallback(response) {
                    $localStorage.webMarketGuestCartId = response.data.value;
                });
        }
    }
})();

    angular.module('store').controller('indexController', function ($rootScope, $scope, $http, $localStorage) {

        $scope.tryToAuth = function () {
            $http.post($localStorage.contextPath + '/api/v1/auth', $scope.user)
                .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $localStorage.webMarketUser = {username: $scope.user.username, token: response.data.token, roles: response.data.roles};

                        $scope.user.username = null;
                        $scope.user.password = null;

                    }
                }, function errorCallback(response) {
                    alert("не правильный логин / пароль")
                });
        };

        $scope.tryToLogout = function () {
            $scope.clearUser();
            if ($scope.user.username) {
                $scope.user.username = null;
            }
            if ($scope.user.password) {
                $scope.user.password = null;
            }
        };

        $scope.clearUser = function () {
            delete $localStorage.webMarketUser;
            $http.defaults.headers.common.Authorization = '';
        };

        $rootScope.isUserLoggedIn = function () {
            if ($localStorage.webMarketUser) {
                return true;
            } else {
                return false;
            }
        };


    });