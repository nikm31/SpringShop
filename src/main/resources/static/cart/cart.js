angular.module('store').controller('cartController', function ($scope, $http, $location, $localStorage) {


    $scope.loadCart = function () {
        var id = $localStorage.contextPath + '/api/v1/cart/' + $localStorage.webMarketGuestCartId
        console.log(id)
        $http({
            url: $localStorage.contextPath + '/api/v1/cart/' + $localStorage.webMarketGuestCartId,
            method: 'GET'
        }).then(function (response) {
            console.log(response)
            $scope.cart = response.data;
        });
    };

    $scope.incrementItem = function (productId) {
        $http({
            url: $localStorage.contextPath + '/api/v1/cart/' + $localStorage.webMarketGuestCartId + '/add/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.decrementItem = function (productId) {
        $http({
            url: $localStorage.contextPath + '/api/v1/cart/' + $localStorage.webMarketGuestCartId + '/decrement/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.removeItem = function (productId) {
        $http({
            url: $localStorage.contextPath + '/api/v1/cart/' + $localStorage.webMarketGuestCartId + '/remove/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.checkOut = function () {
        $location.path("/order_confirmation");
    }

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.loadCart();
});